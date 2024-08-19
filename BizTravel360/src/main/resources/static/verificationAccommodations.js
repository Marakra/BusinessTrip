    var now = new Date();
    var month = ('0' + (now.getMonth() + 1)).slice(-2); // months are 0 based index
    var day = ('0' + now.getDate()).slice(-2);
    var hour = ('0' + now.getHours()).slice(-2);
    var minutes = ('0' + now.getMinutes()).slice(-2);
    var formattedToday = now.getFullYear() + '-' + month + '-' + day + 'T' + hour + ':' + minutes;

    document.getElementById('checkIn').min = formattedToday;

    document.getElementById('checkIn').addEventListener('change', function() {
    document.getElementById('checkOut').min = this.value;
});
