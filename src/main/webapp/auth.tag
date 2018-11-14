<auth>
    <button style="width: 300px" onclick="{signInTeacher}">Войти как учитель</button>
    <button style="width: 300px" onclick="{signInStudent}">Войти как студент</button>
    <script>
        var parent = this.parent
        signInTeacher() {
            parent.update({events : "signInTeacher"})
        }
        signInStudent() {
            parent.update({events : "signInStudent"})
        }
    </script>
</auth>