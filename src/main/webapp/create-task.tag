<create-task>
    <form>
        <div class="form-group">
            <label for="header">Заголовок</label>
            <input type="text" class="form-control" id = "header"  value="{header}" placeholder="Заголовок">
        </div>
        <div class="form-group">
            <label for="problem">Password</label>
            <input type="text" class="form-control" id = "problem" placeholder="Формулировка задания...">
        </div>
        <button onclick='{createm}' class="btn btn-primary">Создать</button>
    </form>

    <script>
        createm(e) {
            e.preventDefault();
            var posting = $.post('/teacher/1/create-task', {header: jQuery("#header").val(),
            problem: jQuery("#problem").val()});
            posting.done(function (data) {
                alert(data)
            }).fail(function (request) {
                alert(request.responseText);
            })
        }
    </script>
</create-task>