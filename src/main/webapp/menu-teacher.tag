<menu-teacher id="my">
    <div id = "menu">
        <h2>Меню</h2>
        <button class="btn btn-success btn-sm btn-block" onclick={ allTasks }>
            База заданий
        </button>
        <button class="btn btn-success btn-sm btn-block" onclick={ myTasks }>
            Назначенные задания
        </button>
        <button class="btn btn-success btn-sm btn-block" onclick={ createTask}>
            Создать задание
        </button>
    </div>
    <script>
        var parent = this.parent
        allTasks() {
            parent.update({events : "allTaskTeacher"})
        }
        myTasks() {
            parent.update({events : "myTaskTeacher"})
        }
        createTask() {
            parent.update({events : "createTask"})
        }
    </script>
</menu-teacher>
