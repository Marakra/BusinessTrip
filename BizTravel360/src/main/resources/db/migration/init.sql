INSERT INTO accommodation (id, name, type, address, check_in, check_out, price, create_by, create_at, last_modified_by, update_at, is_accepted)
VALUES
    (1, 'Przytulny Domek', 'COTTAGE', 'Wrzosowa 10, Zakopane', '2024-09-01 15:00:00', '2024-09-05 11:00:00', 150.00, 'admin', NOW(), 'admin', NOW(), false),
    (2, 'Luksusowy Hotel', 'HOTEL', 'Plażowa 5, Sopot', '2024-09-10 14:00:00', '2024-09-15 12:00:00', 300.00, 'admin', NOW(), 'admin', NOW(), false),
    (3, 'Nowoczesne Mieszkanie', 'APARTMENT', 'Warszawska 20, Warszawa', '2024-09-20 16:00:00', '2024-09-25 10:00:00', 200.00, 'admin', NOW(), 'admin', NOW(), false),
    (4, 'Willa Nadmorska', 'VILLA', 'Morska 3, Gdańsk', '2024-10-01 15:00:00', '2024-10-05 11:00:00', 450.00, 'admin', NOW(), 'admin', NOW(), false),
    (5, 'Rustykalna Chata', 'CABIN', 'Górska 15, Karpacz', '2024-10-10 14:00:00', '2024-10-15 12:00:00', 180.00, 'admin', NOW(), 'admin', NOW(), false),
    (6, 'Hotel w Centrum', 'HOTEL', 'Stare Miasto 2, Kraków', '2024-10-20 16:00:00', '2024-10-25 10:00:00', 250.00, 'admin', NOW(), 'admin', NOW(), false),
    (7, 'Elegancki Apartament', 'APARTMENT', 'Kwiatowa 7, Wrocław', '2024-11-01 15:00:00', '2024-11-05 11:00:00', 320.00, 'admin', NOW(), 'admin', NOW(), false),
    (8, 'Zajazd Jeziorny', 'LODGE', 'Jeziorna 8, Mazury', '2024-11-10 14:00:00', '2024-11-15 12:00:00', 400.00, 'admin', NOW(), 'admin', NOW(), false),
    (9, 'Dom na Plaży', 'VILLA', 'Plażowa 12, Kołobrzeg', '2024-11-20 16:00:00', '2024-11-25 10:00:00', 600.00, 'admin', NOW(), 'admin', NOW(), false),
    (10, 'Mieszkanie w Centrum', 'APARTMENT', 'Rynek 15, Poznań', '2024-12-01 15:00:00', '2024-12-05 11:00:00', 210.00, 'admin', NOW(), 'admin', NOW(), false),
    (11, 'Górska Chata', 'CABIN', 'Słoneczna 6, Zakopane', '2024-12-10 14:00:00', '2024-12-15 12:00:00', 190.00, 'admin', NOW(), 'admin', NOW(), false),
    (12, 'Urokliwy Bungalow', 'BUNGALOW', 'Malwowa 9, Białystok', '2025-01-01 15:00:00', '2025-01-05 11:00:00', 175.00, 'admin', NOW(), 'admin', NOW(), false),
    (13, 'Penthouse z Widokiem', 'APARTMENT', 'Widokowa 11, Gdynia', '2025-01-10 14:00:00', '2025-01-15 12:00:00', 800.00, 'admin', NOW(), 'admin', NOW(), false),
    (14, 'Zajazd Wiejskiej', 'GUEST_HOUSE', 'Wiejska 4, Wadowice', '2025-01-20 16:00:00', '2025-01-25 10:00:00', 220.00, 'admin', NOW(), 'admin', NOW(), false),
    (15, 'Luksusowy Spa Resort', 'RESORT', 'Spa 14, Ciechocinek', '2025-02-01 15:00:00', '2025-02-05 11:00:00', 900.00, 'admin', NOW(), 'admin', NOW(), false);

INSERT INTO employee (id, first_name, last_name, position, role, email, password, token, create_by, create_at, last_modified_by, update_at, is_accepted)
VALUES
    (1, 'Jan', 'Kowalski', 'ADMINISTRATOR', 'ADMINISTRATOR', 'jan.kowalski@example.com', '$2a$10$e0NSFj8xTldkEDzR7JDFLeI3UQ/WZnRYPhLGeu0MR3PiQZjNsWfNm', 'Fjs74ldkfj48js93kd73', 'system', NOW(), 'system', NOW(), true),
    (2, 'Anna', 'Nowak', 'MANAGER', 'MANAGER', 'anna.nowak@example.com', '$2a$10$Vpqzxj9K0WJHgMyH7fjDF.eO9/Y83tnM3tNiPtA/9H4rGf8LHeOsK', 'Ohd94jfl58kfk45kjd94', 'system', NOW(), 'system', NOW(), true),
    (3, 'Piotr', 'Zieliński', 'HR', 'HR', 'piotr.zielinski@example.com', '$2a$10$Tx5vdyK9k3xfuv7x4Xphru/NF9aXRjC1qzryeJ7Df5KJDf1Wd/j2.', 'Pqo38dlkfj48slf39k20', 'system', NOW(), 'system', NOW(), true),
    (4, 'Karolina', 'Wójcik', 'ACCOUNTANT', 'EMPLOYEE', 'karolina.wojcik@example.com', '$2a$10$h6dLkKFbnJkswlG1vq6jXeGnbvVOXM3UfK4nOEG9EoXgrksAxeJ.6', 'Rhs94jdkf03kls94kd82', 'system', NOW(), 'system', NOW(), true),
    (5, 'Marek', 'Lewandowski', 'SALES_REPRESENTATIVE', 'EMPLOYEE', 'marek.lewandowski@example.com', '$2a$10$eR7Lq4Yz5WtESjEu9Y.Dte74h94/lRltFkO2NUzD4klXz5slpPqaG', 'Xiw92slfj94kwp92kdm4', 'system', NOW(), 'system', NOW(), true),
    (6, 'Zofia', 'Kamińska', 'IT_SUPPORT', 'EMPLOYEE', 'zofia.kaminska@example.com', '$2a$10$B6dvzuPvBu3Y1ZkuSlgS7e7jRcqUttLQqgsrDpeJFO19fyj.GAxsS', 'Urf74jdl39kdf92lske4', 'system', NOW(), 'system', NOW(), true),
    (7, 'Tomasz', 'Dąbrowski', 'MARKETING_SPECIALIST', 'EMPLOYEE', 'tomasz.dabrowski@example.com', '$2a$10$eU5eSzmZPGFy0qZ9zWJrk.YjK7WT0lK5mkd9/BHcUqwjv5DxHFTyS', 'Ieu73kfjg45ks93kdf38', 'system', NOW(), 'system', NOW(), true),
    (8, 'Agnieszka', 'Szymańska', 'ENGINEER', 'EMPLOYEE', 'agnieszka.szymanska@example.com', '$2a$10$M0lHJe1Dflr95ZmpJdPaeuwq6UhVnBZ/JrYctP0VQ5l1BV46eX3kS', 'Lke85fjdo02ksl94kej3', 'system', NOW(), 'system', NOW(), true),
    (9, 'Paweł', 'Kwiatkowski', 'RECEPTIONIST', 'EMPLOYEE', 'pawel.kwiatkowski@example.com', '$2a$10$Fhf2UkKqYlZC7EyKHZQe9er5.m7zEYZPAo5YsO9AQXjgpB8Xe.Ray', 'Psd93hfkfj39sfk02kme', 'system', NOW(), 'system', NOW(), true),
    (10, 'Katarzyna', 'Mazur', 'CUSTOMER_SERVICE', 'EMPLOYEE', 'katarzyna.mazur@example.com', '$2a$10$IQBtF5lKeKYFItRMfkmPv6N4oAdA29OMrFEIv0GmhtBtAKHY0FMda', 'Asf85jfkdjs30wo9kej2', 'system', NOW(), 'system', NOW(), true);

INSERT INTO transport (id, type, identifier, departure, departure_date_time, arrival, arrival_date_time, price, create_by, create_at, last_modified_by, update_at, is_accepted)
VALUES
    (1, 'SUBWAY', 'LOT1234', 'Warszawa', '2024-10-01 08:30:00', 'Berlin', '2024-10-01 10:30:00', 350.00, 'system', NOW(), 'system', NOW(), true),
    (2, 'TRAIN', 'PKP5678', 'Kraków', '2024-10-02 12:00:00', 'Gdańsk', '2024-10-02 18:00:00', 150.00, 'system', NOW(), 'system', NOW(), true),
    (3, 'BUS', 'POLBUS9123', 'Poznań', '2024-10-03 06:30:00', 'Wrocław', '2024-10-03 09:00:00', 60.00, 'system', NOW(), 'system', NOW(), true),
    (4, 'CAR', 'RENTCAR345', 'Warszawa', '2024-10-04 09:00:00', 'Łódź', '2024-10-04 11:30:00', 200.00, 'system', NOW(), 'system', NOW(), true),
    (5, 'SCOOTER', 'SCOOT4567', 'Gdańsk', '2024-10-05 08:00:00', 'Sopot', '2024-10-05 09:00:00', 20.00, 'system', NOW(), 'system', NOW(), true),
    (6, 'SUBWAY', 'SUB123', 'Warszawa', '2024-10-06 07:15:00', 'Warszawa Centrum', '2024-10-06 07:45:00', 5.00, 'system', NOW(), 'system', NOW(), true),
    (7, 'TRAM', 'TRAM567', 'Łódź', '2024-10-07 14:30:00', 'Łódź Kaliska', '2024-10-07 15:00:00', 4.00, 'system', NOW(), 'system', NOW(), true),
    (8, 'MOTORBIKE', 'MOTO123', 'Katowice', '2024-10-08 10:00:00', 'Chorzów', '2024-10-08 10:30:00', 50.00, 'system', NOW(), 'system', NOW(), true),
    (9, 'TAXI', 'TAXI456', 'Warszawa', '2024-10-09 22:00:00', 'Warszawa Mokotów', '2024-10-09 22:30:00', 40.00, 'system', NOW(), 'system', NOW(), true),
    (10, 'PLANE', 'LOT5678', 'Warszawa', '2024-10-10 14:00:00', 'Paryż', '2024-10-10 16:00:00', 450.00, 'system', NOW(), 'system', NOW(), true),
    (11, 'TRAIN', 'PKP7890', 'Warszawa', '2024-10-11 09:30:00', 'Poznań', '2024-10-11 13:30:00', 130.00, 'system', NOW(), 'system', NOW(), true),
    (12, 'BUS', 'POLBUS6789', 'Gdańsk', '2024-10-12 07:00:00', 'Bydgoszcz', '2024-10-12 10:00:00', 70.00, 'system', NOW(), 'system', NOW(), true),
    (13, 'CAR', 'RENTCAR678', 'Warszawa', '2024-10-13 15:00:00', 'Białystok', '2024-10-13 18:00:00', 180.00, 'system', NOW(), 'system', NOW(), true),
    (14, 'SCOOTER', 'SCOOT7890', 'Wrocław', '2024-10-14 09:00:00', 'Wrocław Stare Miasto', '2024-10-14 09:30:00', 15.00, 'system', NOW(), 'system', NOW(), true),
    (15, 'TAXI', 'TAXI789', 'Kraków', '2024-10-15 18:00:00', 'Kraków Podgórze', '2024-10-15 18:30:00', 35.00, 'system', NOW(), 'system', NOW(), true);
