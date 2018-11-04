<card>

    <style>
        li {
            list-style-type: none; /* Убираем маркеры */
        }
        ul {
            margin-left: 0; /* Отступ слева в браузере IE и Opera */
            padding-left: 0; /* Отступ слева в браузере Firefox, Safari, Chrome */
        }
    </style>

    <style type="text/css">
        body { margin: 0; }
        #card { position: absolute; }
        #card {
            width: 700px;
            left: 10px;
            right: 400px;
            top:75px;
            border: 4px double black;
        }
    </style>

    <div id = "card">
    <h5>{header}</h5>
    <h5>{problem}</h5>
    </div>
    <script>
    this.header = opts.header
    this.problem = opts.problem
    console.log("card")
    console.log(this.header)
    console.log(this.problem)
    </script>
</card>