$(function () {

    $("input[name=birth]").datepicker({
        changeYear: true,
        changeMonth: true,
        dateFormat: "yy-mm-dd",
        maxDate: "+10y"
    });

});
