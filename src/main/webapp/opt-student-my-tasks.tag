<opt-student-my-tasks>
    <div class="container" style="margin-top: 20px;">
        <div class="row">
            <button style="background-color: #00bed6" onclick="{addAnswer}"> Ответить </button>
            <button style="background-color: #00bed6" onclick="{addEvent}">Добавить в Google календарь</button>
        </div>
    <div class="row">
    <div id = "context"></div>
    </div>
    </div>
    <script>
        var id_task = this.parent.id_task
        var token = this.parent.token;
        var header = this.parent.header;
        var deadline = "";
        if(this.parent.date != null) {
            deadline = this.parent.date;
        }
        this.on('update', (e) => {
            id_task = this.parent.id_task
            header = this.parent.header
            if(this.parent.date != null) {
            deadline = this.parent.date;
        }
        })

        addEvent(e) {
            $.ajax({
                type: "POST",
                url: 'user/calendar/event',
                data: {header: header, deadline: deadline},
                dataType: 'json',
                headers: {AUTHORIZATION : token}
            }).done(function (data) {
                alert(data);
            }.bind(this)).fail(function (request) {
                alert(request.responseText);
            })
        }
        addAnswer() {
            var changeTaskForm = jQuery('<form/>', {
                id: "changeTask",
                style:"padding-top: 10px;",
                submit: function (event) {
                    event.preventDefault();
                    var $form = jQuery(this),
                        term = $form.find("input[name='answer']").val();

                    $.ajax({
                        type: "POST",
                        url: 'user/student/add-answer',
                        data: {id_task: id_task, answer: term},
                        dataType: 'json',
                        headers: {AUTHORIZATION : token}
                    }).done(function (data) {
                        alert(data.data);
                    }).fail(function (request) {
                        alert(request.responseText);
                    })
                    jQuery('#context').empty();
                }
            }).append(jQuery('<label>Ответ:</label>')).append(jQuery('<input/>', {
                name: 'answer',
                type: 'text',
                placeholder: "ваш ответ..."

            })).append(jQuery('<input/>', {
                style: "background-color: #00bed6",
                type: 'submit',
                value: 'Записать ответ'
            }));
            jQuery('#context').empty().append(changeTaskForm);
            this.update()
        }
    </script>
</opt-student-my-tasks>