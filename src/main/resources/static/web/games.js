
fetch("/api/games")
.then(res => res.json())
.then(json => {
console.log(json);
    app.games = json
})

var app = new Vue({
    el: "#app",
    data: {
        games: []
    }
});



