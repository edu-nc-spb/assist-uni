<menu-teacher>

    <style>
        li {
            list-style-type: none;
        }
    </style>

    <div id = "menu">
        <ul>
            <li>
                <h3 style="text-align: center;">Меню</h3>
            </li>
            <li>
                <button style="background-color: #80D4DF; border-color: #0ec3db" class="btn btn-sm btn-block" onclick={ allTasks }>
                    База заданий
                </button>
            </li>
            <li>
                <button style="background-color: #80D4DF; border-color: #0ec3db" class="btn btn-sm btn-block" onclick={ myTasks }>
                    Назначенные задания
                </button>
            </li>
            <li>
                <button style="background-color: #80D4DF; border-color: #0ec3db" class="btn btn-sm btn-block" onclick={ createTask}>
                    Создать задание
                </button>
            </li>
        </ul>
    </div>
    <script>
        var parent = this.parent
        this.menu = [{value: "База заданий", ev: "{allTasks}"},
            {value: "Назначенные задания", ev:"myTaskTeacher"},
            {value: "Создать задание", ev:"createTask"}]
        allTasks() {
            console.log("hi")
            parent.update({events : "allTaskTeacher"})
        }
        myTasks() {
            console.log("hiii")
            parent.update({events : "myTaskTeacher"})
        }
        createTask() {
            parent.update({events : "createTask"})
        }
    </script>
</menu-teacher>
