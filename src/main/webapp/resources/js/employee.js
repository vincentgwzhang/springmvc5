$(function () {

    $(".deleteEmployee").click(function (event) {
        $(".submitForm").attr("action", this.href);
        $(".submitForm").submit();
        return false;
    });

});
