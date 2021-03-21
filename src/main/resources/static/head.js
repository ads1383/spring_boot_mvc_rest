$(function () {
    const requestURL = 'http://localhost:8090/api/user/principal'

    sendRequestGet('GET', requestURL).then(data => {
        $('#headerBlack1').replaceWith('<a class="navbar-brand text-light" id="headerBlack1">' + data.username +'</a>');
        let div =  $('#headerBlack2');
        data.roles.forEach(function (el) {
            $('<li class="nav-item text-light m-1"></li>').text(el.role.split('_').pop()).appendTo(div);
        })
    })

})




