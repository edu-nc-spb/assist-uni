<opt-teacher-my-tasks>
    <button onclick="{showA}"> Посмотреть ответ </button>
    <div id = "context"></div>
    <script>
        showA () {
            var $select = $('<select/>', {
                name:'name'
            });
            var getting = $.get('/teacher/1/get-students');
            getting.done(function (data) {
                $.each(
                    data.data,
                    function (intIndex, objValue) {
                        $select.append($("<option/>", {
                            value: objValue.id,
                            text: objValue.name
                        }))
                    })
            });
            var showAnswerButton = jQuery('<form/>', {
                submit: function (event) {
                    event.preventDefault();
                    var term = $select.val();
                    var posting = $.post('/teacher/1/show-answer',
                        {header: opts.header, id: term});
                    posting.done(function (data) {
                        alert(data);
                    }).fail(function (request) {
                        alert(request.responseText);
                    })
                }
            }).append($select);
            showAnswerButton.append(jQuery('<input/>', {
                type: 'submit',
                value: 'показать ответ студента'
            }));
            jQuery('#context').empty().append(showAnswerButton);
            this.update()
        }
    </script>
</opt-teacher-my-tasks>