<list>

    <style>
        li {
            list-style-type: none;
        }
    </style>
    <div>
        <ul>
            <h3 style="text-align: center;">Список заданий</h3>
            <li if="{tasks}" each="{header, i in tasks}">
                <button id = "choose" style="background-color: #80D4DF; border-color: #0ec3db"
                        class="btn btn-sm btn-block" data-message="{header.id}"
                        onclick="{choose}" value = "{header.id}">{header.data}</button>
                <button id="calendar" style="background-color: #80D4DF; border-color: #0ec3db"
                        class="btn btn-sm btn-block" data-message="{header.data}"
                        onclick="{addEvent}" value = "{header.data}">Добавить в Google календарь</button>
            </li>
        </ul>
    </div>

    <script>
        this.update();
        this.on('update', (e) => {
            if(e == null) {
                this.tasks = []
                this.flag = this.parent.flag
                this.token = this.parent.token
                $.ajax({
                    url: this.flag,
                    type: "GET",
                    headers: {AUTHORIZATION : this.token},
                }).done(function (data) {
                    this.tasks = []
                    $.each(
                        data.data,
                        function (intIndex, objValue) {
                            this.tasks.push(objValue);
                        }.bind(this))
                    this.update({first: false})
                }.bind(this))
            }
        })

        choose(e) {
            console.log(e.target.dataset.message + " choose")
            this.parent.update({events : "get", header: e.target.dataset.message, flag: this.flag})
        }

        addEvent(e) {
            console.log(e.target.dataset.message + " addEvent")
            $.ajax({
                type: "POST",
                url: 'user/calendar/event',
                data: {header: e.target.dataset.message},
                dataType: 'json',
                headers: {AUTHORIZATION : this.token}
            }).done(function (data) {
                alert(data);
            }.bind(this)).fail(function (request) {
                alert(request.responseText);
            })
        }

    </script>
</list>