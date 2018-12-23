<menu-teacher>

    <style>
        li {
            list-style-type: none;
        }
    </style>

    <div id = "menu" style="overflow: auto">
        <ul>
            <li>
                <p4 style="text-align: center;">Добро пожаловать, {userName}</p4>
            </li>
            <li>
                <h3 style="text-align: center;">Меню</h3>
            </li>
            <li>
                <button style="background-color: #80D4DF; border-color: #0ec3db;width: 200px;
                        overflow: auto;" class="btn btn-sm btn-block" onclick="{ allTasks }">
                    База заданий
                </button>
            </li>
            <li>
                <button style="background-color: #80D4DF; border-color: #0ec3db;width: 200px;
                        overflow: auto"
                        class="btn btn-sm btn-block" onclick="{ myTasks }">
                    Назначенные задания
                </button>
            </li>
            <li>
                <button style="background-color: #80D4DF; border-color: #0ec3db;width: 200px;
                        overflow: auto" class="btn btn-sm btn-block" onclick="{ createTask}">
                    Создать задание
                </button>
            </li>
            <li>
                <button style="background-color: #80D4DF; border-color: #0ec3db;width: 200px;
                        overflow: auto"
                        class="btn btn-sm btn-block" onclick="{ addCalendar }">
                    Добавить Google Calendar
                </button>
            </li>
        </ul>
    </div>
    <script>
        var parent = this.parent
        var token = this.parent.token
        this.userName = this.parent.userName
        allTasks() {
            parent.update({events : "allTaskTeacher"})
        }
        myTasks() {
            parent.update({events : "myTaskTeacher"})
        }
        createTask() {
            parent.update({events : "createTask"})
        }
        addCalendar() {
            console.log("auth")
            $.ajax({
                type: "POST",
                url: 'user/calendar/auth',
                dataType: 'json',
                headers: {AUTHORIZATION : token}
            }).done(function (data) {
                alert(data);
            }.bind(this)).fail(function (request) {
                alert(request.responseText);
            })
        }
    </script>
</menu-teacher>
