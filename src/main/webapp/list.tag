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

    <div>
        <h2>Список заданий</h2>
        <ul if="{tasks}">
            <li each="{header, i in tasks}">
                <button id = "choose" class="btn btn-success btn-sm btn-block" data-message="{header}" onclick="{choose}" value = "{header}">{header}</button>
            </li>
        </ul>
    </div>

    <script>
        this.update();
        this.on('update', (e) => {
            if(e == null) {
            this.tasks = []
            this.flag = this.parent.flag
            jQuery.get(this.flag).done(function (data) {
                this.tasks = []
                $.each(
                    data.tasks,
                    function (intIndex, objValue) {
                        this.tasks.push(objValue);
                    }.bind(this))
                this.update({first: false})
            }.bind(this))
        }
        })

        choose(e) {
            this.parent.update({events : "get", header: e.target.dataset.message, flag: this.flag})
        }
    </script>
</list>