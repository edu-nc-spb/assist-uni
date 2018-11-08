<opt-student-my-tasks>
    <button onclick="{addAnswer}"> Ответить </button>
    <div id = "context"></div>
    <script>
        addAnswer() {
            console.log("addA " + opts.header )
            this.header = opts.header;
            var changeTaskForm = jQuery('<form/>', {
                id: "changeTask",
                submit: function (event) {
                    event.preventDefault();
                    var $form = jQuery(this),
                        term = $form.find("input[name='answer']").val();
                    var posting = $.post('/student/2/add-answer', {
                        header: opts.header, answer: term
                    });
                    posting.done(function (data) {
                        alert(data.data);
                    }).fail(function (request) {
                        alert(request.responseText);
                    })
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