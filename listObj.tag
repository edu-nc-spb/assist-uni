<listObj>
    <style type="text/css">
        body { margin: 0; }
        #choose  { position: relative; }
        #choose {
            width: 300px;
            left: 800px;
        }

    </style>
        <button id = "choose" onclick="{choose}">{header}</button>
        <ul if="{tasks}">
            <li each="{p, i in tasks}">
                    <card header = '{p.header}', problem = '{p.problem}'></card>
            </li>
        </ul>
    <script>
        this.header = opts.header
        this.tasks = []
        choose(e) {
            var posting = $.post('/teacher/1/get-task', {header: this.header});
            posting.done(function (data) {
                //alert(data.header + " " + data.problem);
                this.tasks.push({header:data.header, problem:data.problem})
                this.update()
            }.bind(this))
        }
    </script>
</listObj>