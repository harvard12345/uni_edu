$(document).ready(function () {
    $('.display .deleteButton1').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $('#deleteModal1 #deleteId1').attr('href', href);
        $('#deleteModal1').modal();
    });
});

$(document).ready(function () {
    $('.display .deleteButton2').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $('#deleteModal2 #deleteId2').attr('href', href);
        $('#deleteModal2').modal();
    });
});

$(document).ready(function () {
    $('.display .deleteButton3').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $('#deleteModal3 #deleteId3').attr('href', href);
        $('#deleteModal3').modal();
    });
});

