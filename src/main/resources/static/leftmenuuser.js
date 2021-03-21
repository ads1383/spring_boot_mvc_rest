$(function () {
    const requestURL = 'http://localhost:8090/api/user/principal'
    sendRequestGet('GET', requestURL).then(data => {
        let div = $('#leftMenu');
        data.roles.forEach(function (el) {
            const roleTxt = el.role.split('_').pop()[0] +
                el.role.split('_').pop().toLowerCase().slice(1);
            if(el.role === 'ROLE_USER') {
                $('<a class= "nav-link active" href="/user"></a>')
                    .text(roleTxt)
                    .appendTo(div)
            } else {
                $('<a class= "nav-link" href="/'+ roleTxt.toLowerCase() +'"></a>')
                    .text(roleTxt)
                    .appendTo(div)
            }
        })
    })
})