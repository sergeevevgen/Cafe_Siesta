$(document).ready(function(){
    $('.phone-mask').inputmask({
        mask: '+7 (999) 999-99-99'
    });
    $('form').on('submit', function(e) {
        var phone = $('#phone_number').val().replace(/[^0-9]/g,'');
        if (phone.length < 11) {
            e.preventDefault();
            alert('Введите корректный номер телефона');
        }
    });
});
