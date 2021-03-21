function userInfo() {
    const requestURL = 'http://localhost:8090/api/user/principal'
    let div = $('#userInfo').empty();
    sendRequestGet('GET', requestURL).then(el => {
        $('<tr><td>'+ el.id +'</td>' +
            '<td>'+ el.firstName +'</td>' +
            '<td>'+ el.lastName +'</td>' +
            '<td>'+ el.age +'</td>' +
            '<td>'+ el.username +'</td>' +
            '<td>'+ el.roles.reduce(function(sum, current) {
                return sum + current.role.split("_").pop() + " ";
                    }, "") +
            '</td>' +
            '</tr>').appendTo(div);
    });
}


$(document).ready(function () {
    userInfo();
})
