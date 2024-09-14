package com.travel.BizTravel360.transport.domain;


import com.travel.BizTravel360.accommodation.exeptions.AccommodationNotFoundException;
import com.travel.BizTravel360.accommodation.model.dto.AccommodationDTO;
import com.travel.BizTravel360.accommodation.model.entity.Accommodation;
import com.travel.BizTravel360.transport.TypeTransport;
import com.travel.BizTravel360.transport.exeptions.TransportNotFoundException;
import com.travel.BizTravel360.transport.model.dto.TransportDTO;
import com.travel.BizTravel360.transport.exeptions.TransportSaveException;
import com.travel.BizTravel360.transport.model.entity.Trasport;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TransportService  {

    private final TransportRepository transportRepository;
    private final Validator validator;
    private final TrasportMapper mapper;

    public void save(TransportDTO transportDTO) throws DataAccessException {
        try {
            trimTransport(transportDTO);
            validateTransport(transportDTO);

            Trasport trasport = mapper.fromTrasportDTO(transportDTO);
            transportRepository.save(trasport);
        }catch (DataAccessException exp) {
                log.error("Failed to save transport {}", transportDTO);
                throw new TransportSaveException(
                        String.format("Failed to save transport: %s, message: %s.", transportDTO, exp.getMessage()));
            }
    }

    public Page<TransportDTO> findAll(Pageable pageable) {
        Page<Trasport> trasportPage = transportRepository.findAll(pageable);
        List<TransportDTO> transportDTOS = mapper.toTransportList(trasportPage.getContent());
        return new PageImpl<>(transportDTOS, pageable, trasportPage.getTotalElements());
    }

    public void updateTransport(TransportDTO updatedTransportDTO) {
        Trasport existingTrasport = transportRepository.findById(updatedTransportDTO.getId())
                .orElseThrow(() -> new TransportNotFoundException(updatedTransportDTO.getId()));

        Trasport updatedTrasport = mapper.fromTrasportDTO(updatedTransportDTO);
        updatedTrasport.setId(existingTrasport.getId());
        transportRepository.save(updatedTrasport);
    }

    public void deleteById(Long transportId) {
        Trasport trasport = transportRepository.findById(transportId)
                .orElseThrow(() -> new TransportNotFoundException(transportId));
        transportRepository.delete(trasport);
    }

    public void deleteById(Long accommodationId) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> new AccommodationNotFoundException(accommodationId));
        accommodationRepository.delete(accommodation);
    }

    public Page<TransportDTO> searchTransport(String keyword, TypeTransport type, Pageable pageable) {
        return transportRepository.findByKeywordAndType(keyword, type, pageable)
                .map(mapper::toTrasport);
    }


    private void validateTransport(TransportDTO transportDTO) {
        Set<ConstraintViolation<TransportDTO>> constraintViolations = validator.validate(transportDTO);

        if (!constraintViolations.isEmpty()){
            constraintViolations.forEach(validation -> log.error(validation.getMessage()));
            throw new IllegalArgumentException("Invalid transport data");
        }
    }

    private void trimTransport(TransportDTO transportDTO) {
        transportDTO.setIdentifier(transportDTO.getIdentifier().trim());
        transportDTO.setDeparture(transportDTO.getDeparture().trim());
        transportDTO.setArrival(transportDTO.getArrival().trim());
    }

    public TransportDTO getById(Long transportId) {
        return transportRepository.findById(transportId)
                .map(mapper::toTrasport)
                .orElseThrow(() -> {
                    log.error("Transport with ID {} not found", transportId);
                    return new TransportNotFoundException(transportId);
                });
    }
}