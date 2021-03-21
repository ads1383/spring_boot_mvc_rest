function deleteUser(id) {
    const requestURL = 'http://localhost:8090/api/user/'+id;
    const requestDelURL = 'http://localhost:8090/api/users/'+id;
    const buttonDel = $('#delUserBtn').unbind("click");
    sendRequestGet('GET', requestURL).then(user => {
        $('#delId').val(user.id);
        $('#delFirstName').val(user.firstName);
        $('#delLastName').val(user.lastName);
        $('#delAge').val(user.age);
        $('#delEmail').val(user.username);
        $('#delPassword').val(user.password);
        buttonDel.on('click', function () {
            sendRequestDelete('DELETE', requestDelURL)
                .then(data => {
                    tableUsers()
                });
        });
    });
}