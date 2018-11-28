<create-task>
    <form>
        <div class="form-group">
            <label for="header">Заголовок</label>
            <input type="text" class="form-control" id = "header"  value="{header}" placeholder="Заголовок">
            </input>
        </div>
        <div class="form-group">
            <label for="problem">Условие задания</label>
            <input type="text" class="form-control" id = "problem" placeholder="Формулировка задания..."></input>
        </div>
        <button onclick='{createm}' style="background-color:  #51C5D4">Создать</button>
    </form>

    <script>
        createm(e) {
            e.preventDefault();
            var posting = $.post('user/teacher/1/create-task', {header: jQuery("#header").val(),
            problem: jQuery("#problem").val()});
            posting.done(function (data) {
                alert(data)
            }).fail(function (request) {
                alert(request.responseText);
            })
        }
    </script>
</create-task>