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
            if ((e != null) && (e.events == "signInTeacher")) {
                this.left = 'menu-teacher';
                this.flagC = false;
                this.flagR = false;
                this.flagL = true;
                this.flagC0 = false;
                this.token = e.token;
                riot.update()
            } else if (e != null && e.events == "signInStudent") {
                this.left = 'menu-student';
                this.flagC = false;
                this.flagR = false;
                this.flagL = true;
            this.flagC0 = false;
            riot.update()
            } else if (e != null && e.events == "allTaskTeacher") {
                this.flagC = false;
                this.flagR = true;
                this.flagC0 = false;
                this.flag = "user/teacher/1/get-all-tasks";
                this.right = 'list'
            riot.update()
            } else if (e != null && e.events == "myTaskTeacher") {
                this.flagC=false;
                this.flagR = true;
                this.flagC0 = false;
                this.flag = "user/teacher/1/get-my-tasks";
                console.log("myTaskTeacher")
                this.right = 'list'
            riot.update()

            } else if (e != null && e.events == "createTask") {
                this.center = 'create-task'
                this.flagC0 = false;
                this.flagC = true;
                this.flagR = false
                riot.update()
            } else if(e != null && e.events == "get") {
                console.log("get " + e.header)
                this.flagC = true;
                this.flagC0 = true;
                $.ajax({
                    type: "POST",
                    url: 'user/teacher/1/get-task',
                    data: {task_id: e.header},
                    dataType: 'json',
                    headers: {AUTHORIZATION : this.token}
                }).done(function (data) {
                    this.header = data.header
                    this.problem =  data.problem
                    this.id_task = data.id
                    this.update()
                }.bind(this))
                this.center0 = 'card'
                if(e.flag == "user/teacher/1/get-my-tasks") {
                    this.center = ('opt-teacher-my-tasks')
                } else if(e.flag == "user/teacher/1/get-all-tasks") {
                    this.center = ('opt-teacher-all-tasks')
                } else if (e.flag == "user/student/2/get-my-tasks") {
                    this.center = ('opt-student-my-tasks')
                }
                riot.update()
            } else if (e != null && e.events == "myTasksStudent") {
                this.flagC = false;
                this.flagR = true;
                this.flag = "user/student/2/get-my-tasks";
                this.right = 'list'
                riot.update()
            } else if (e != null && e.events == "deleteTask") {
                this.flagC = false;
                this.flagC0 = false;
                console.log("deleteTask")
                riot.update()
            } else if (e != null && e.events == "changeTask") {
            this.update({events:"get", header:e.header});
            console.log("changeTask " + e.header)
            riot.update()
        }
        })
    </script>
</app>