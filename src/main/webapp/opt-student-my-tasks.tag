<opt-student-my-tasks>
    <div class="container" style="margin-top: 20px;">
    <div class="row">
    <button style="background-color: #00bed6" onclick="{addAnswer}"> Ответить </button>
    </div>
    <div class="row">
    <div id = "context"></div>
    </div>
    </div>
    <script>
        //var header = this.parent.header
        var id_task = this.parent.id_task
        this.on('update', (e) => {
            id_task = this.parent.id_task
        })
        addAnswer() {
            var changeTaskForm = jQuery('<form/>', {
                id: "changeTask",
                style:"padding-top: 10px;",
                submit: function (event) {
                    event.preventDefault();
                    var $form = jQuery(this),
                        term = $form.find("input[name='answer']").val();
                    var posting = $.post('user/student/2/add-answer', {
                        id_task: id_task, answer: term
                    });
                    posting.done(function (data) {
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