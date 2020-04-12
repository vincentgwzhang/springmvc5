$(function () {

    var currentYear = new Date().getFullYear();

    $("input[name=birth]").datepicker({
        changeYear: true,
        changeMonth: true,
        dateFormat: "yy-mm-dd",
        maxDate: "0d",
        yearRange: "1900:" + currentYear
    });

});
