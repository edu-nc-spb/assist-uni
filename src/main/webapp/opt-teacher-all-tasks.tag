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
    <script>
        var parent = this.parent;
        var id_task = this.parent.id_task;
        var token = this.parent.token;
        this.on('update', (e) => {
            id_task = this.parent.id_task;
            parent = this.parent;
        })
        change() {
            console.log(id_task + " change");
            var changeTaskForm = jQuery('<form/>', {
                id: "changeTask",
                submit: function (event) {
                    event.preventDefault();
                    var $form = jQuery(this),
                        term = $form.find("textarea[name='newProblem']").val();
                    $.ajax({
                        type: "POST",
                        url: 'user/teacher/change-task',
                        data: {id_task: id_task, newProblem: term},
                        dataType: 'json',
                        headers: {AUTHORIZATION : token}
                    }).done(function (data) {
                        alert(data);
                        jQuery('#context').empty();
                        this.parent.update({events : "changeTask", header:id_task})
                    }.bind(this)).fail(function (request) {
                        alert(request.responseText);
                    })
                    jQuery('#context').empty();
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
            console.log(id_task);
            $.ajax({
                type: "POST",
                url: 'user/teacher/delete-task',
                data: {id_task: id_task},
                dataType: 'json',
                headers: {AUTHORIZATION : token}
            }).done(function (data) {
                alert(data);
                jQuery('#context').empty();
                this.parent.update({events : "deleteTask"});
            }.bind(this)).fail(function (request) {
                alert(request.responseText);
            })
            jQuery('#context').empty();
            //this.parent.update({events : "deleteTask"});
        }
        addStudent() {
            var $select = $('<select/>', {
                name:'name'
            });

            $.ajax({
                url: '/user/teacher/get-students',
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

            var addStudentButton = jQuery('<form/>', {
                style: "padding-top: 10px;",
                submit: function (event) {
                    event.preventDefault();
                    var term = $select.val();
                    var $form = jQuery(this);
                    var deadline = $form.find("textarea[name='deadline']").val();
                    $.ajax({
                        type: "POST",
                        url: '/user/teacher/add-student',
                        data: {id_task: id_task, id_student: term, deadline: deadline},
                        dataType: 'json',
                        headers: {AUTHORIZATION : token}
                    }).done(function (data) {
                        alert(data);
                    }.bind(this)).fail(function (request) {
                        alert(request.responseText);
                    }.bind(this))
                    jQuery('#context').empty();
                }
            }).append(jQuery('<textarea/>', {
                name: 'deadline',
                style: 'width: 100%; margin-top: 10px;',
                placeholder: "через сколько минут закончится прием ответов"

            })).append($select);
            addStudentButton.append(jQuery('<input/>', {
                style:"background-color: #00bed6;",
                type: 'submit',
                value: 'назначить студенту'
            }));
            jQuery('#context').empty().append(addStudentButton);
        }
    </script>
</opt-teacher-all-tasks>