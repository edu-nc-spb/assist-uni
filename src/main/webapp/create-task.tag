<create-task>
    <form>
        Task header: <input id = "header"  value="{header}" />
        Task problem: <input id = "problem" type="text" placeholder="Problem..." />
        <button onclick='{createm}'>Создать</button>
    </form>
    <script>
        createm(e) {
            e.preventDefault();
            var posting = $.post('/teacher/1/create-task', {header: jQuery("#header").val(),
            problem: jQuery("#problem").val()});
            posting.done(function (data) {
                this.update()
            }.bind(this))
        }
    </script>
</create-task>