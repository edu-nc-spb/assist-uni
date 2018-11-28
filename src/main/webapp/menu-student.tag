<menu-student>

    <style>
        li {
            list-style-type: none;
        }
    </style>

    <div id="menu">
        <ul>
            <li>
                <h3 style="text-align: center;">Меню</h3>
            </li>
            <li>
                <button style="background-color: #80D4DF; border-color: #0ec3db"
                        class="btn btn-sm btn-block" onclick="{ myTasks }">
                    Назначенные мне задания
                </button>
            </li>
        </ul>
    </div>
    <script>
        myTasks()
        {
            this.parent.update({events: "myTasksStudent"})
        }
    </script>
</menu-student>
