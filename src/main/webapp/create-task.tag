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
            this.token = this.parent.token
            $.ajax({
                type: "POST",
                url: 'user/teacher/1/create-task',
                data: {header: jQuery("#header").val(),
                    problem: jQuery("#problem").val()},
                dataType: "json",
                headers: {AUTHORIZATION : this.token}
            }).done(function (data) {
            alert(data)
        }.bind(this)).fail(function (request) {
            alert(request.responseText);
        }.bind(this))
        }
    </script>
</create-task>