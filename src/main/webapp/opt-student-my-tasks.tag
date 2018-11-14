<opt-student-my-tasks>
    <button class="btn btn-primary" onclick="{addAnswer}"> Ответить </button>
    <div id = "context"></div>
    <script>
        var header = this.parent.header
        this.on('update', (e) => {
            header = this.parent.header
        })
        addAnswer() {
            var changeTaskForm = jQuery('<form/>', {
                id: "changeTask",
                submit: function (event) {
                    event.preventDefault();
                    var $form = jQuery(this),
                        term = $form.find("input[name='answer']").val();
                    var posting = $.post('/student/2/add-answer', {
                        header: header, answer: term
                    });
                    posting.done(function (data) {
                        alert(data.data);
                    }).fail(function (request) {
                        alert(request.responseText);
                    })
                    jQuery('#context').empty();
                }
            }).append(jQuery('<input/>', {
                name: 'answer',
                type: 'text',
                placeholder: "ваш ответ..."

            })).append(jQuery('<input/>', {
                type: 'submit',
                value: 'Записать ответ'
            }));
            jQuery('#context').empty().append(changeTaskForm);
            this.update()
        }
    </script>
</opt-student-my-tasks>