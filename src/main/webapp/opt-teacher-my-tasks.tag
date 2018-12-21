<opt-teacher-my-tasks>
    <div style="margin-top: 20px;">
    <button style="background-color: #00bed6" onclick="{showAnswer}">Посмотреть ответ</button>
    <div id = "context"></div>
    </div>
    <script>
        var id_task = this.parent.id_task;
        var header = this.parent.header;
        var parent = this.parent;
        var token = this.parent.token;
        this.on('update', (e) => {
            id_task = this.parent.id_task;
            parent = this.parent;
            header = this.parent.header;
        })
        showAnswer () {
            $.ajax({
                type: "POST",
                url: '/user/teacher/show-answer',
                data: {id_task: id_task},
                dataType: 'json',
                headers: {AUTHORIZATION : token}
            }).done(function (data) {
                alert(data);
            }).fail(function (request) {
                alert(request.responseText);
            })
        }
    </script>
</opt-teacher-my-tasks>