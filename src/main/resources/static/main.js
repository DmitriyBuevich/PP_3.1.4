
$(async() => {
    await getTableWithUsers();
})


const userFetchService = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json; charset=utf-8'
    },

    findAllUsers: async () => await fetch('api/users'),
    addNewUser: async (user, addRoles) => await fetch(`api/?selectedRoles=` + addRoles, {method: 'POST', headers: userFetchService.head, body: JSON.stringify(user)}),
    updateUser: async (user, editRoles) => await fetch(`api/edit/?selectedRoles=` + editRoles, {method: 'PATCH', headers: userFetchService.head, body: JSON.stringify(user)}),
    deleteUser: async (deleteId) => await fetch(`api/delete/?deleteId=` + deleteId , {method: 'DELETE', headers: userFetchService.head})
}

async function getTableWithUsers() {
    let table = $('#data tbody');
    table.empty();

    await userFetchService.findAllUsers()
        .then(res => res.json())
        .then(users => {
            users.forEach(user => {
                let tableFilling = `$(
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.firstName}</td>
                            <td>${user.lastName}</td>
                            <td>${user.age}</td>
                            <td>${user.email}</td>
                            <td>${getRoles(user)}</td>
                            <td> <a href="/api/${user.id}" class="btn btn-primary editBtn" >Edit</a> </td>
                            <td> <a href="/api/${user.id}" class="btn btn-danger deleteBtn ">Delete</a> </td> 
                            </td>
                            <td>
                            </td>
                            </tr>
                )`;
                table.append(tableFilling);
            })
        })
}
//
document.onclick = function (event){
    //вызов модалки эдита
    if ($(event.target).hasClass('editBtn')) {
        event.preventDefault();
        let href = $(event.target).attr("href");

        $.get(href, function (user) {
            $('.editForm #idEdit').val(user.id);
            $('.editForm #firstnameEdit').val(user.firstName);
            $('.editForm #lastnameEdit').val(user.lastName);
            $('.editForm #ageEdit').val(user.age);
            $('.editForm #emailEdit').val(user.email);
            $('.editForm #passwordEdit').val(user.password);
        });

        $('.editForm #editModal').modal();
    }
    //вызов модалки делита

    if ($(event.target).hasClass('deleteBtn')) {
        event.preventDefault();
        let href = $(event.target).attr("href");

        $.get(href, function (user) {
            $('.deleteForm #idDelete').val(user.id);
            $('.deleteForm #firstNameDelete').val(user.firstName);
            $('.deleteForm #lastnameDelete').val(user.lastName);
            $('.deleteForm #ageDelete').val(user.age);
            $('.deleteForm #emailDelete').val(user.email);
            $('.deleteForm #passwordDelete').val(user.password);
        });

        $('.deleteForm #deleteModal').modal();
    }
}
$( document ).ready( function () {
    // button EDIT
    $('.editSuccess').on('click', async function (e) {
        e.preventDefault();

        let user = {
            id: $('#idEdit').val(),
            firstName: $('#firstnameEdit').val(),
            lastName: $('#lastnameEdit').val(),
            age: $('#ageEdit').val(),
            email: $('#emailEdit').val(),
            password: $('#passwordEdit').val(),
        }

        var selectedRoles = $('#editRoles option:selected')
            .toArray().map(item => item.text);

        await userFetchService.updateUser(user, selectedRoles)
        $(".editForm #editClose").click()
        await getTableWithUsers()
    })
    // button DELETE

    $('.deleteSuccess').on('click', async function (e) {
        e.preventDefault();
        let deleteId = $('#idDelete').val()

        await userFetchService.deleteUser(deleteId)
        $(".deleteForm #deleteClose").click()
        await getTableWithUsers()

    })
    //Добавление + перезагрузка полей
    $('.addSuccess').on('click', async function (e) {

        let user = {
            firstName: $('#addFirstName').val(),
            lastName: $('#addLastName').val(),
            age: $('#addAge').val(),
            email: $('#addEmail').val(),
            password: $('#addPassword').val(),
        }

        var addRoles = $('#rolesAdd option:selected')
            .toArray().map(item => item.text);

        await userFetchService.addNewUser(user, addRoles)
        $('#addFirstName').val("")
        $('#addLastName').val(""),
            $('#addAge').val(""),
            $('#addEmail').val(""),
            $('#addPassword').val(""),
            await getTableWithUsers()
        $(" #usersBtn").click()
    })
})


// Роли
function getRoles(user) {
    let roleList = ""
    for (let i = 0; i < user.roles.length; i++) {
        roleList += (user.roles[i].name + " ").substring(5);
    }
    return roleList;
}

// юзер инфо
fetch("http://localhost:8080/api/thisUser")
    .then(res => res.json())
    .then(user => {

        document.getElementById('id').innerText = user.id;
        document.getElementById('firstname').innerText = user.firstName;
        document.getElementById('lastname').innerText = user.lastName;
        document.getElementById('age').innerText = user.age;
        document.getElementById('email').innerText = user.email;
        document.getElementById('current_user').innerText = user.email;

    })
fetch("http://localhost:8080/api/userInfo")
    .then(res => res.text())
    .then(res => {
        document.getElementById("roles").innerText = res;
        document.getElementById("rolesTable").innerText = res;
    })







