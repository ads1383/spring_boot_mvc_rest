$(function () {
    const requestURL = 'http://localhost:8090/api/users';
    const requestRolesURL = 'http://localhost:8090/api/roles';
    const buttonAdd = $('#addUserBtn');
    const pRole = $('#roleList');
    let jData = {};
    let val = {};
    sendRequestGet('GET', requestRolesURL).then(data => {
        data.forEach(function (el) {
            val[el.id] = el.role;
            $('<input type="checkbox" value="'+ el.id +'" name="roles"/><label class="m-1">' +
                el.role.split("_").pop() + '</label>').appendTo(pRole)
        });
        buttonAdd.on('click',function () {
            let i = 0;
            jData['roles'] = [];
            $('#formAddUser').find('input').each(function () {
                if(this.id != 'addUserBtn') {
                    if (this.name === 'roles') {
                        if ($(this).is(':checked')) {
                            let mas = {};
                            mas['id'] = $(this).val();
                            mas['role'] = val[$(this).val()];
                            jData['roles'][i] = mas
                            i++;
                        }
                    } else if (this.name === 'enabled') {
                        if ($(this).is(':checked')) {
                            jData[this.name] = true;
                        } else {
                            jData[this.name] = false;
                        }
                    } else {
                        jData[this.name] = $(this).val();
                    }
                }
            });
            sendRequestPost('POST', requestURL, jData).then(data => {
                $('#blockLink a[href="#user_table"]').tab('show');
                $('#firstName').val("");
                $('#lastName').val("");
                $('#age').val("");
                $('#email').val("");
                $('#password').val("");
                $('#roleList').find('input').each(function () {
                    $(this).prop('checked', false);
                })
                tableUsers();
            });
        });

    });
})