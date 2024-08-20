/*Kod JavaScript jest używany do uzupełniania i aktualizowania wartości daty w odpowiednich polach ('transports' i 'accommodations').
 Dodatkowo, ustawia minimalne możliwe wartości dla tych pól, zgodne z wybraną datą,
 zapewniając, że użytkownik nie może wybrać daty wcześniejszej niż wybrana. */

// Add click event listener for 'transports'
document.getElementById("transports").addEventListener("click", function() {
    handleSelectionChange(this, "departureDateTime", "arrivalDateTime");
});

// Add click event listener for 'accommodations'
document.getElementById("accommodations").addEventListener("click", function() {
    handleSelectionChange(this, "departureDateTime", "arrivalDateTime");
});

// Function to handle selection change for transports or accommodations
function handleSelectionChange(element, outputStartId, outputEndId) {
    // Extract start and end date from the selected option
    var selectedOptionText = element.options[element.selectedIndex].text;
    var startDateText = selectedOptionText.split(' |***| ')[0];
    var endDateText = selectedOptionText.split(' |***| ')[1];

    // Get related output elements
    var outputStartElem = document.getElementById(outputStartId);
    var outputEndElem = document.getElementById(outputEndId);

    // Update output elements' values
    outputStartElem.value = getDateTimeForInput(startDateText);
    outputEndElem.value = getDateTimeForInput(endDateText);

    // Update minimal possible values of the output elements
    outputStartElem.min = outputStartElem.value;
    outputEndElem.min = outputEndElem.value;
}

// Function to convert a date/time string into a date object
function getDateTimeForInput(dateTimeText) {
    var dateParts = dateTimeText.split(' ')[0].split("-");
    var timeParts = dateTimeText.split(' ')[1].split(":");
    var dateObject = new Date(dateParts[2], dateParts[1] - 1, dateParts[0], timeParts[0], timeParts[1]);

    return formatDate(dateObject);
}

// Function to pad single digit numbers with a leading zero
function pad(val) {
    return ('0' + val).slice(-2);
}

// Function to convert date object into a string following a specific format (yyyy-mm-ddThh:mm)
function formatDate(date) {
    // Get individual parts of the date/time
    var d = new Date(date),
        month = pad(d.getMonth() + 1),
        day = pad(d.getDate()),
        hour = pad(d.getHours()),
        minutes = pad(d.getMinutes());

    // Return the formatted date
    return [d.getFullYear(), month, day].join('-') + 'T' + [hour, minutes].join(':');
}