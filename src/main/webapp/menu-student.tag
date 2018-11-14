<menu-student>
    <div id = "menu">
        <h2>Меню</h2>
        <button class="btn btn-success btn-sm btn-block" onclick={ myTasks }>
            Назначенные мне задания
        </button>
    </div>
    <script>
        myTasks()
        {
            this.parent.update({events: "myTasksStudent"})
        }
    </script>
</menu-student>
