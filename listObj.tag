<listObj>

    <style type="text/css">
        body {
            margin: 0;
        }

        #choose {
            position: relative;
            right: 10px;
            width: 300px;
        }

        #card {
            position: absolute;
            width: 700px;
            left: 10px;
            top: 10px;
        }
        #optall, #optmy, #optst {
            position: absolute;
            width: 700px;
            left: 10px;
            padding: 30px;
        }
    </style>
        <button id = "choose" onclick="{choose}">{header}</button>
        <ul if="{tasks}">
            <li each="{p, i in tasks}">
                <card id = "card" name = "card" header = '{p.header}', problem = '{p.problem}'></card>
                <opt-teacher-all-tasks if="{opts.all}" id = "optall" header = '{p.header}'></opt-teacher-all-tasks>
                <opt-teacher-my-tasks if="{opts.my}" id = "optmy" header = '{p.header}'></opt-teacher-my-tasks>
                <opt-student-my-tasks if="{opts.st}" id = "optst" header = '{p.header}'></opt-student-my-tasks>
            </li>
        </ul>
    <script>
        this.header = opts.header
        tasks = []
        choose(e) {
            if(tasks.length > 0) {
                tasks = []
                riot.update()
            }
            var posting = $.post('/teacher/1/get-task', {header: this.header});
                posting.done(function (data) {
                tasks.push({header:data.header, problem:data.problem})
                this.update()
            }.bind(this))
        }
    </script>
</listObj>