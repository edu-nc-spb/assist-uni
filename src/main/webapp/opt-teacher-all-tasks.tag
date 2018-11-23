<opt-teacher-all-tasks>
        <div class="btn-group" role="group"
             style="margin-top: 10px;">
            <button style="background-color: #00bed6" onclick="{change}">
                Изменить условие</button>
            <button style="background-color: #00bed6" onclick="{deleteT}">
                Удалить задание</button>
            <button style="background-color: #00bed6" onclick="{addStudent}">
                Назначить студенту</button>
        </div>
        <div id = "context"></div>
    </div>
    <script>
        //var header = this.parent.header
        var parent = this.parent;
        var id_task = this.parent.id_task;
        this.on('update', (e) => {
            id_task = this.parent.id_task;
            parent = this.parent;
        })
        change() {
            var changeTaskForm = jQuery('<form/>', {
                id: "changeTask",
                submit: function (event) {
                    event.preventDefault();
                    var $form = jQuery(this),
                        term = $form.find("textarea[name='newProblem']").val();
                    var posting = $.post('teacher/1/change-task', {
                        id_task: id_task, newProblem: term
                    });
                    posting.done(function (data) {
                        alert(data);
                    }).fail(function (request) {
                        alert(request.responseText);
                    })
                    jQuery('#context').empty();
                    parent.update({events : "changeTask", header:id_task})
                }
            }).append(jQuery('<textarea/>', {
                name: 'newProblem',
                style: 'width: 100%; margin-top: 10px;',
                placeholder: "новая формулировка задания..."

            })).append(jQuery('<input/>', {
                style: "background-color: #00bed6",
                type: 'submit',
                value: 'Изменить'
            }));
            jQuery('#context').empty().append(changeTaskForm);
        }
        deleteT(){
            var posting = $.post('/teacher/1/delete-task', {header: id_task});
            posting.done(function (data) {
                alert(data);
            }).fail(function (request) {
                alert(request.responseText);
            })
            jQuery('#context').empty();
            this.parent.update({events : "deleteTask"});
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
                            text: objValue.data
                        }))
                    })
            });

            var addStudentButton = jQuery('<form/>', {
                style: "padding-top: 10px;",
                submit: function (event) {
                    event.preventDefault();
                    var term = $select.val();
                    var posting = $.post('/teacher/1/add-student',
                        {id_task: id_task, id_student: term});
                    posting.done(function (data) {
                        alert(data);
                    }.bind(this)).fail(function (request) {
                        alert(request.responseText);
                    }.bind(this))
                    jQuery('#context').empty();
                }
            }).append($select);
            addStudentButton.append(jQuery('<input/>', {
                style:"background-color: #00bed6;",
                type: 'submit',
                value: 'назначить студенту'
            }));
            jQuery('#context').empty().append(addStudentButton);
        }

    </script>
</opt-teacher-all-tasks>