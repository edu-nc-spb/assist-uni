<opt-teacher-all-tasks>
    <button onclick="{change}"> Изменить условие </button>
    <button onclick="{deleteT}"> Удалить задание</button>
    <button onclick="{addStudent}"> Назначить студенту</button>
    <div id = "context"></div>
    <script>
        change() {
            this.header = opts.header;
            console.log("change")
            var changeTaskForm = jQuery('<form/>', {
                id: "changeTask",
                submit: function (event) {
                    event.preventDefault();
                    var $form = jQuery(this),
                        term = $form.find("input[name='newProblem']").val();
                    var posting = $.post('teacher/1/change-task', {
                        header: opts.header, newProblem: term
                    });
                    posting.done(function (data) {
                        //alert(data);
                    }).fail(function (request) {
                        alert(request.responseText);
                    })
                }
            }).append(jQuery('<input/>', {
                name: 'newProblem',
                type: 'text',
                placeholder: "новая формулировка задания..."

            })).append(jQuery('<input/>', {
                type: 'submit',
                value: 'Изменить'
            }));
            jQuery('#context').empty().append(changeTaskForm);
            this.update()
        }
        deleteT(){
            var posting = $.post('/teacher/1/delete-task', {header: opts.header});
            posting.done(function (data) {
                alert(data);
                jQuery('#result').empty();
                jQuery('#task').empty();
            }).fail(function (request) {
                alert(request.responseText);
            })
            jQuery('#context').empty();
            this.update();
        }
        addStudent() {
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

            var addStudentButton = jQuery('<form/>', {
                submit: function (event) {
                    event.preventDefault();
                    var term = $select.val();
                    var posting = $.post('/teacher/1/add-student',
                        {header: opts.header, id: term});
                    posting.done(function (data) {
                        alert(data);
                    }).fail(function (request) {
                        alert(request.responseText);
                    })
                }
            }).append($select);
            addStudentButton.append(jQuery('<input/>', {
                type: 'submit',
                value: 'назначить студенту'
            }));
            jQuery('#context').empty().append(addStudentButton);
            this.update()
        }

    </script>
</opt-teacher-all-tasks>