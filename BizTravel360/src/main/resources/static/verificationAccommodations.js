// Create a new Date object for the current date and time
var now = new Date();
// Format the month to two-digits by adding leading zero if necessary
var month = ('0' + (now.getMonth() + 1)).slice(-2); // months are 0 based index
// Format the day to two-digits by adding leading zero if necessary
var day = ('0' + now.getDate()).slice(-2);
// Format the hour to two-digits by adding leading zero if necessary
var hour = ('0' + now.getHours()).slice(-2);
// Format the minutes to two-digits by adding leading zero if necessary
var minutes = ('0' + now.getMinutes()).slice(-2);
// Combine the parts to form a date-time string in "YYYY-MM-DDTHH:mm" format
var formattedToday = now.getFullYear() + '-' + month + '-' + day + 'T' + hour + ':' + minutes;

// Set the minimum date-time value of checkIn input element to the current date-time
document.getElementById('checkIn').min = formattedToday;

// Add an event listener to the checkIn input element to update the checkOut's min value whenever checkIn's value is changed
document.getElementById('checkIn').addEventListener('change', function() {
    document.getElementById('checkOut').min = this.value; // checkOut can't be earlier than checkIn
});