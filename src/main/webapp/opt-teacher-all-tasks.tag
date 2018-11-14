<opt-teacher-all-tasks>
    <div class="container">
    <div class="row">
    <div class="btn-group" role="group" aria-label="Basic example">
        <button type="button" class="btn btn-primary" onclick="{change}">
            Изменить условие</button>
        <button type="button" class="btn btn-primary" onclick="{deleteT}">
            Удалить задание</button>
        <button type="button" class="btn btn-primary" onclick="{addStudent}">
            Назначить студенту</button>
    </div>
    </div>
    </div>
    <div class="row" id = "context"></div>
    <script>
        var header = this.parent.header
        var parent = this.parent;
        this.on('update', (e) => {
            header = this.parent.header
            parent = this.parent;
        })
        change() {
            var changeTaskForm = jQuery('<form/>', {
                id: "changeTask",
                submit: function (event) {
                    event.preventDefault();
                    var $form = jQuery(this),
                        term = $form.find("input[name='newProblem']").val();
                    var posting = $.post('teacher/1/change-task', {
                        header: header, newProblem: term
                    });
                    posting.done(function (data) {
                        alert(data);
                    }).fail(function (request) {
                        alert(request.responseText);
                    })
                    jQuery('#context').empty();
                    parent.update({events : "changeTask", header:header})
                }
            }).append(jQuery('<input/>', {
                name: 'newProblem',
                type: 'text',
                placeholder: "новая формулировка задания..."

            })).append(jQuery('<input/>', {
                class:"btn btn-primary",
                type: 'submit',
                value: 'Изменить'
            }));
            jQuery('#context').empty().append(changeTaskForm);
            this.update()
        }
        deleteT(){
            var posting = $.post('/teacher/1/delete-task', {header: header});
            //var parent = this.parent;
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
                            text: objValue.name
                        }))
                    })
            });

            var addStudentButton = jQuery('<form/>', {
                submit: function (event) {
                    event.preventDefault();
                    var term = $select.val();
                    var posting = $.post('/teacher/1/add-student',
                        {header: header, id: term});
                    posting.done(function (data) {
                        alert(data);
                    }.bind(this)).fail(function (request) {
                        alert(request.responseText);
                    }.bind(this))
                    jQuery('#context').empty();
                    this.update()
                }
            }).append($select);
            addStudentButton.append(jQuery('<input/>', {
                class:"btn btn-primary",
                type: 'submit',
                value: 'назначить студенту'
            }));
            jQuery('#context').empty().append(addStudentButton);
            this.update()
        }

    </script>
</opt-teacher-all-tasks>