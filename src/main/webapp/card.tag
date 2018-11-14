<card>
    <div id = "card">
        <h4>{header}</h4>
        <p5> Условие:</p5>
        <br/>
        <p5>{problem}</p5>
    </div>
    <script>
    this.header = this.parent.header
    this.problem = this.parent.problem
    this.on('update', (e) => {
        this.header = this.parent.header
        this.problem = this.parent.problem
    })
    </script>
</card>