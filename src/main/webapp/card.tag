<card>
    <style>
        .colortext {
            border-bottom: 2px solid  #EAA83A;
            padding:10px;
        }
        .borderdiv {
            border-width: 4px;
            border-style: double;
            border-color: greenyellow;
        }
    </style>

    <div style="background-color: #fdd89e;" id = "card">
        <!--<h3 >{header}</h3>-->
        <h3 class = "colortext">{header}</h3>
        <p style="padding-left:10px; padding-right:10px;">{date}</p>
        <p style="padding-left:10px; padding-right:10px;">Условие:</p>
        <p style="padding-left:10px; padding-right:10px;">{problem}</p>
    </div>
    <script>
    this.header = this.parent.header
    this.problem = this.parent.problem
    this.date = "";
    if(this.parent.date != null) {
        this.date = "Дедлайн: " + this.parent.date;
    }
    this.on('update', (e) => {
        this.header = this.parent.header
        this.problem = this.parent.problem
    this.date = "";
        if(this.parent.date != null) {
            this.date = "Дедлайн: " + this.parent.date;
        }
    })
    </script>
</card>