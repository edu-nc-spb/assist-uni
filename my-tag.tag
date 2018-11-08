<my-tag>
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

    <div id = "right" data-is="{component}"></div>
    <div id = "menu">
        <h2>Меню</h2>
        <button style="width: 300px" onclick={ allTasks }>
            База заданий
        </button>
        <button style="width: 300px" onclick={ myTasks }>
            Назначенные задания
        </button>
        <button style="width: 300px" onclick={ createTask}>
            Создать задание
        </button>
    </div>
    <script>
        allTasks() {
            this.component = 'list-all-tasks';
            //this.component.html("<list flag = '/teacher/1/get-all-tasks'></list>");
            //riot.mount('list', {flag = "/teacher/1/get-all-tasks"});
        }
            /*var riot = require('riot')
            var list = require('list.tag')
            var html = riot.render(list, { flag : "/teacher/1/get-all-tasks" })
            this.component = html;*/
        myTasks() {
            //riot.mount('list', {flag:"/teacher/1/get-all-tasks"});
            this.component = 'list-my-tasks'
        }
        createTask() {
            this.component = 'create-task'
        }
    </script>
</my-tag>
