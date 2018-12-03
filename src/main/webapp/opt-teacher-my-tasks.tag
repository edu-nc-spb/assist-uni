<opt-teacher-my-tasks>
    <div style="margin-top: 20px;">
    <button style="background-color: #00bed6" onclick="{showAnswer}"> Посмотреть ответ </button>
    <div id = "context"></div>
    </div>
    <script>
        var id_task = this.parent.id_task;
        var parent = this.parent;
        var token = this.parent.token;
        this.on('update', (e) => {
            id_task = this.parent.id_task;
            parent = this.parent;
        })
        showAnswer () {
            var $select = $('<select/>', {
                name:'name',
                style: 'width: 100%; margin-top: 10px'
            });
            $.ajax({
                url: '/user/teacher/1/get-students',
                type: "GET",
                headers: {AUTHORIZATION : token},
            }).done(function (data) {
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
                    $.ajax({
                        type: "POST",
                        url: '/user/teacher/1/show-answer',
                        data: {id_task: id_task, id: term},
                        dataType: 'json',
                        headers: {AUTHORIZATION : token}
                    }).done(function (data) {
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
            this.update();
        }
    </script>
</opt-teacher-my-tasks>