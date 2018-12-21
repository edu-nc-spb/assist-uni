<auth>
    <!--button style="width: 300px" onclick="{signInTeacher}">Войти как учитель</button>
    <button style="width: 300px" onclick="{signInStudent}">Войти как студент</button-->

    <form class="form-signin">
        <h1 class="h3 mb-3 font-weight-normal">Авторизация в системе</h1>
        <label for="login" class="sr-only">Логин</label>
        <input type="text" id="login" class="form-control" placeholder="Логин"></input>
        <label for="inputPassword" class="sr-only">Пароль</label>
        <input type="password" id="inputPassword" class="form-control" placeholder="Пароль" required=""></input>
        <button style="background-color: #80D4DF; border-color: #0ec3db" class="btn btn-sm btn-block" onclick="{signIn}">Войти</button>
    </form>

    <script>
        signIn(e) {
            e.preventDefault();
            $.post('auth/', {login : jQuery("#login").val(),
                password : jQuery("#inputPassword").val()}).
            done(function (data) {
                this.parent.update({events: "signIn", token : data.token, role : data.role, userName : data.name})
            }.bind(this)).fail(function (data) {
                alert("Неверный логин или пароль");
            }.bind(this))
        }
    </script>
</auth>