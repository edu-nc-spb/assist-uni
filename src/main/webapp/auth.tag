<auth>
    <!--button style="width: 300px" onclick="{signInTeacher}">Войти как учитель</button>
    <button style="width: 300px" onclick="{signInStudent}">Войти как студент</button-->

    <form class="form-signin">
        <h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>
        <label for="login" class="sr-only">Login</label>
        <input type="text" id="login" class="form-control" placeholder="Login"></input>
        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" id="inputPassword" class="form-control" placeholder="Password" required=""></input>
        <button class="btn btn-lg btn-primary btn-block" onclick="{signInTeacher}">Sign in teacher</button>
        <button class="btn btn-lg btn-primary btn-block" onclick="{signInStudent}">Sign in student</button>
    </form>

    <script>
        var parent = this.parent
        /*signInTeacher() {
            parent.update({events : "signInTeacher"})
        }*/
        signInStudent() {
            parent.update({events : "signInStudent"})
        }
        signInTeacher() {
            console.log("sign in")
            var auth = false
            var posting = $.post('auth/teacher/', {login : jQuery("#login").val(),
                password : jQuery("#inputPassword").val()});
            posting.done(function (data) {
                console.log("OK, auth " + data);
                //riot.update({events: "signInTeacher"});
                //this.update();
            }.bind(this)).fail(function (data) {
                console.log("fail " + data.responseText);
            }.bind(this))
            //this.update();
            parent.update({events: "signInTeacher"})


        }
    </script>
</auth>