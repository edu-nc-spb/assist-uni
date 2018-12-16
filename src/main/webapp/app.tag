<app>
    <div class="container">
        <div class="row">
            <div id = "left" class = "col-sm-3 order-1" if = "{flagL}" data-is = "{left}"></div>
            <div class = "col-sm-6 order-2">
                <div class="row-offset-2" id = "center0" if = "{flagC0}" data-is = "{center0}"></div>
                <div class="row-offset-2" id = "center" if = "{flagC}" data-is = "{center}"></div>
            </div>
            <div id = "right" class = "col-sm-3 order-3" if = "{flagR}" data-is = "{right}"></div>
        </div>
    </div>


    <script>
        this.left = 'auth'
        this.token = "";
        this.flagL = true
        this.flagC = false;
        this.flagC0 = false;
        this.flagR = false;


        this.on('update', (e) => {
             if (e != null && e.events == "signIn") {
                this.token = e.token;
                if(e.role == 2)
                this.left = 'menu-student';
                if(e.role == 1)
                    this.left = 'menu-teacher';
                this.flagC = false;
                this.flagR = false;
                this.flagL = true;
                this.flagC0 = false;
                riot.update()
            } else if (e != null && e.events == "allTaskTeacher") {
                this.flagC = false;
                this.flagR = true;
                this.flagC0 = false;
                this.flag = "user/teacher/get-all-tasks";
                this.right = 'list'
            riot.update()
            } else if (e != null && e.events == "myTaskTeacher") {
                this.flagC=false;
                this.flagR = true;
                this.flagC0 = false;
                this.flag = "user/teacher/get-my-tasks";
                this.right = 'list'
            riot.update()

            } else if (e != null && e.events == "createTask") {
                this.center = 'create-task'
                this.flagC0 = false;
                this.flagC = true;
                this.flagR = false
                riot.update()
            } else if(e != null && e.events == "get") {
                this.flagC = true;
                this.flagC0 = true;
                $.ajax({
                    type: "POST",
                    url: 'user/teacher/get-task',
                    data: {id_task: e.header},
                    dataType: 'json',
                    headers: {AUTHORIZATION : this.token}
                }).done(function (data) {
                    this.header = data.header
                    this.problem =  data.problem
                    this.id_task = data.id
                    if(data.deadline != null) {
                        this.date = data.deadline;
                    }
                    this.update()
                }.bind(this))
                this.center0 = 'card'
                if(e.flag == "user/teacher/get-my-tasks") {
                    this.center = ('opt-teacher-my-tasks')
                } else if(e.flag == "user/teacher/get-all-tasks") {
                    this.center = ('opt-teacher-all-tasks')
                } else if (e.flag == "user/student/get-my-tasks") {
                    this.center = ('opt-student-my-tasks')
                }
                this.update();
                riot.update()
            } else if (e != null && e.events == "myTasksStudent") {
                this.flagC = false;
                this.flagR = true;
                this.flag = "user/student/get-my-tasks";
                this.right = 'list'
                riot.update()
            } else if (e != null && e.events == "deleteTask") {
                this.flagC = false;
                this.flagC0 = false;
                riot.update()
            } else if (e != null && e.events == "changeTask") {
                this.update({events:"get", header:e.header});
            }
        })
    </script>
</app>