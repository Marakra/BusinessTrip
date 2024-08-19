function pad(val) {
    return ('0' + val).slice(-2);
}

function formatDate(date) {
    var d = new Date(date),
        month = pad(d.getMonth() + 1),
        day = pad(d.getDate()),
        hour = pad(d.getHours()),
        minutes = pad(d.getMinutes());
    return [d.getFullYear(), month, day].join('-') + 'T' + [hour, minutes].join(':');
}

var listItemElement = document.getElementById("accommodations");
var highestDate = new Date(Math.min.apply(null, Array.from(listItemElement.options, function(option) {
    var dateParts = option.text.split("|***|")[0].split(' ')[0].split("-");
    var timeParts = option.text.split("|***|")[0].split(' ')[1].split(":");
    return new Date(dateParts[2], dateParts[1] - 1, dateParts[0], timeParts[0], timeParts[1]);
})));

document.getElementById('departureDateTime').min = formatDate(highestDate);

document.getElementById('departureDateTime').addEventListener('change', function() {
    document.getElementById('arrivalDateTime').min = this.value;
});