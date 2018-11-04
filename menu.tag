<menu>

    <style type="text/css">
        body { margin: 0; }
        #menu { position: absolute; }
        #b1, #b2, #b3{ position:relative;}
        #menu, #b1, #b2, #b3 {
            width: 300px;
            left: 10px;
        }
    </style>

    <h1>Меню</h1>
    <div id = "menu">
        <form action="teacher/1/get-all-tasks">
            <button id = "b1" type="submit">База заданий</button>
        </form>
        <form action="teacher/1/get-my-tasks">
            <button id = "b2" type="submit">Назначенные задания</button>
        </form>
        <form action="teacher/1/get-my-tasks">
            <button id = "b3" type="submit"> Создать задание</button>
        </form>
    </div>
</menu>