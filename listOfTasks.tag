<listOfTasks>

    <style type="text/css">
        body { margin: 0; }
        #listObj {
            padding: 10px;
            left: 400px;
            height: 400px;
            position: relative;
        }
        #title, #updateB { position: relative; }
        #title {
            left: 800px;
            right: 0px;
            top: 10px;
            bottom: 0;
        }
        #updateB {
            left:800px;
        }
    </style>
    <style>
        li {
            list-style-type: none; /* Убираем маркеры */
        }
        ul {
            margin-left: 0; /* Отступ слева в браузере IE и Opera */
            padding-left: 0; /* Отступ слева в браузере Firefox, Safari, Chrome */
        }
    </style>

    <div id = "listObj">
        <h1 id="title">Список заданий</h1>
        <ul if="{tasks}">
            <li each="{p, i in tasks}">
                <listObj header = '{p}' />
            </li>
        </ul>
        <button id = "updateB" onclick="{updateList}">Update</button>
    </div>
    <script>
        this.tasks = [];
        updateList(e) {
            jQuery.get("teacher/1/get-all-tasks").done(function(data) {
                this.tasks = [];
                $.each(
                    data.tasks,
                    function (intIndex, objValue) {
                        this.tasks.push(objValue);
                    }.bind(this))
                this.update();
            }.bind(this));
        }

    </script>
</listOfTasks>
