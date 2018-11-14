<opt-teacher-my-tasks>
    <button class="btn btn-primary btn-block" onclick="{showA}"> Посмотреть ответ </button>
    <div id = "context"></div>
    <script>
        var header = this.parent.header
        var parent = this.parent
        this.on('update', (e) => {
            header = this.parent.header
            parent = this.parent;
        })
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
                        {header: header, id: term});
                    posting.done(function (data) {
                        alert(data);
                    }).fail(function (request) {
                        alert(request.responseText);
                    })
                }
            }).append($select);
            showAnswerButton.append(jQuery('<input/>', {
                class: "btn btn-primary",
                type: 'submit',
                value: 'показать ответ студента'
            }));
            jQuery('#context').empty().append(showAnswerButton);
            this.update()
        }
    </script>
</opt-teacher-my-tasks>