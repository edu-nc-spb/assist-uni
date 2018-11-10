<my-tag-student id="my">
    <style type="text/css">
        body {
            margin: 0;
        }

        #menu {
            position: absolute;
            width: 300px;
            left: 10px;
        }
        #right {
            position: absolute;
            right: 10px;
            left: 350px;
        }
    </style>

    <div id = "right" name = "right" data-is="{component}"></div>
    <div id = "menu">
        <h2>Меню</h2>
        <button style="width: 300px" onclick={ myTasks }>
            Назначенные мне задания
        </button>
    </div>
    <script>
        myTasks() {
            this.component = 'list-my-tasks-student'
        }
    </script>
</my-tag-student>
