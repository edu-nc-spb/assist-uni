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
    </style>
        <button id = "choose" onclick="{choose}">{header}</button>
        <ul if="{tasks}">
            <li each="{p, i in tasks}">
                    <card id = "card" flag = '{p.flag}' header = '{p.header}', problem = '{p.problem}'></card>
            </li>
        </ul>
    <script>
        this.header = opts.header
        this.tasks = []
        choose(e) {
            if(this.tasks.length > 0) {
                this.tasks[0].flag = false;
                this.update()
            }
            var posting = $.post('/teacher/1/get-task', {header: this.header});
                posting.done(function (data) {
                this.tasks.push({flag:true, header:data.header, problem:data.problem})
                this.update()
            }.bind(this))
        }
    </script>
</listObj>