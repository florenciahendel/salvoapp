
fetch("/api/games")
.then(res => res.json())
.then(json => {
    app.games = json
})

var app = new Vue({
    el: "#app",
    data: {
        games: [],
        players: []
});

function changeDateFormat (){
    for (i in app.games){
        var newDate = new Date(app.games[i].created).toLocaleString();
        app.games[i].created = newDate
    }


}


