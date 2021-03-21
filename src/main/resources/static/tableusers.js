function tableUsers() {
    const requestURL = 'http://localhost:8090/api/users'
    const requestRolesURL = 'http://localhost:8090/api/roles';
    let div = $('#userTable').empty();
    let upRole = $('#edRoleList').empty();
    let upVal = {};
    sendRequestGet('GET', requestRolesURL).then(data => {
        data.forEach(function (rl) {
            upVal[rl.id] = rl.role;
            $('<input type="checkbox" value="' + rl.id + '" name="roles"/><label class="m-1">' +
                rl.role.split("_").pop() + '</label>').appendTo(upRole)
        });
        sendRequestGet('GET', requestURL).then(data => {
            data.forEach(function (el) {
                $('<tr><td>'+ el.id +'</td>' +
                    '<td>'+ el.firstName +'</td>' +
                    '<td>'+ el.lastName +'</td>' +
                    '<td>'+ el.age +'</td>' +
                    '<td>'+ el.username +'</td>' +
                    '<td>'+ el.roles.reduce(function(sum, current) {
                        return sum + current.role.split("_").pop() + " ";
                    }, "") +
                    '</td>' +
                    '<td><button id="editButton_' + el.id + '" type="button"  class="btn btn-info" data-bs-toggle="modal" ' +
                    'data-bs-target="#editModal">Edit</button></td>' +
                    '<td><button id="deleteButton_' + el.id + '" type="button"  class="btn btn-danger" data-bs-toggle="modal" ' +
                    'data-bs-target="#deleteModal">Delete</button></td>' +
                    '</tr>').appendTo(div)
                $('#editButton_'+el.id).on('click', function () {
                    updateUser(el.id, upVal);
                });
                $('#deleteButton_'+el.id).on('click', function () {
                    deleteUser(el.id);
                })

            })
        })
    });
}


$(document).ready(function () {
    tableUsers();
})
