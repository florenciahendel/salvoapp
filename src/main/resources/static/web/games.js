
fetch("/api/games")
.then(res => res.json())
.then(json => {
    app.games = json
})

var app = new Vue({
    el: "#app",
    data: {
        games: []
    }
});

function changeDateFormat (){
    for (i in app.games){
        var newDate = new Date(app.games[i].created).toLocaleString();
        app.games[i].created = newDate
    }
    //Falta el código del "for" que muestre los username de los players
}


