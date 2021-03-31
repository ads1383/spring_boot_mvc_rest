function updateUser(id, upVal) {
  const requestURL = 'http://localhost:8090/api/user/'+id;
  const requestPutURL = 'http://localhost:8090/api/users/';
  const buttonUpt = $('#uptUserBtn').unbind("click");
  let jData = {};
  sendRequestGet('GET', requestURL).then(user => {
    $('#edId').val(user.id);
    $('#edFirstName').val(user.firstName);
    $('#edLastName').val(user.lastName);
    $('#edAge').val(user.age);
    $('#edEmail').val(user.username);
    $('#edPassword').val(user.password);
    $('#edAuthProvider').val(user.authProvider);
    // $('#edAuthProvider').val("LOCAL");
    buttonUpt.on('click', function () {
      let i = 0;
      jData['roles'] = [];
      $('#formEdUser').find('input').each(function () {
          if (this.name === 'roles') {
            if ($(this).is(':checked')) {
              let mas = {};
              mas['id'] = $(this).val();
              mas['role'] = upVal[$(this).val()];
              jData['roles'][i] = mas
              i++;
            }
          } else if (this.name === 'enabled') {
            if ($(this).is(':checked')) {
              jData[this.name] = true;
            } else {
              jData[this.name] = false;
            }
          } else if (this.name === 'authProvider') {
            if ($(this).val() ==='') {
              jData[this.name] = "LOCAL";
            } else {
              jData[this.name] = $(this).val();
            }
          } else {
            jData[this.name] = $(this).val();
          }
      });
      sendRequestPost('PUT', requestPutURL, jData)
          .then(data => {
            tableUsers();
          });
    });
  });
}


