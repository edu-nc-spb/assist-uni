<opt-teacher-my-tasks>
    <div style="margin-top: 20px;">
    <button style="background-color: #00bed6" onclick="{showA}"> Посмотреть ответ </button>
    <div id = "context"></div>
    </div>
    <script>
        var id_task = this.parent.id_task;
        var parent = this.parent
        this.on('update', (e) => {
            id_task = this.parent.id_task
            parent = this.parent;
        })
        showA () {
            var $select = $('<select/>', {
                name:'name',
                style: 'width: 100%; margin-top: 10px'
            });
            var getting = $.get('/user/teacher/1/get-students');
            getting.done(function (data) {
                $.each(
                    data.data,
                    function (intIndex, objValue) {
                        $select.append($("<option/>", {
                            value: objValue.id,
                            text: objValue.data
                        }))
                    })
            });
            var showAnswerButton = jQuery('<form/>', {
                submit: function (event) {
                    event.preventDefault();
                    var term = $select.val();
                    var posting = $.post('/user/teacher/1/show-answer',
                        {id_task: id_task, id: term});
                    posting.done(function (data) {
                        alert(data);
                    }).fail(function (request) {
                        alert(request.responseText);
                    })
                }
            }).append($select);
            showAnswerButton.append(jQuery('<input/>', {
                style: "background-color: #00bed6; width: 100%; margin-top: 10px;",
                type: 'submit',
                value: 'показать ответ студента'
            }));
            jQuery('#context').empty().append(showAnswerButton);
            this.update()
        }
    </script>
</opt-teacher-my-tasks>