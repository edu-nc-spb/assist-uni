<list>

    <style>
        li {
            list-style-type: none;
        }
        ul {
            margin-left: 0;
            padding-left: 0;
        }
    </style>

    <div style="float: right">
        <h1>Список заданий</h1>
        <ul if="{tasks}">
            <li each="{p, i in tasks}">
                <listObj all = "{opts.all}"  my = "{opts.my}" st = "{opts.st}" name = "listObj" header = '{p}' />
            </li>
        </ul>
        <button onclick="{updateList}">Update</button>
    </div>
    <script>
        this.flag = opts.flag
        this.aaa = "";
        console.log(this.flag);
        this.tasks = [];
        updateList(e) {
            if(this.tasks.length > 0) {
                this.tasks = []
            }
            jQuery.get(this.flag).done(function(data) {
                $.each(
                    data.tasks,
                    function (intIndex, objValue) {
                        this.tasks.push(objValue);
                    }.bind(this))
                this.update();
            }.bind(this));
        }

    </script>
</list>