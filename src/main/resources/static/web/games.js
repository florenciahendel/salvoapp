
fetch("/api/games")
.then(res => res.json())
.then(json => {
console.log(json);
    app.games = json
})

fetch("/api/leaderboard")
.then(res => res.json())
.then(json => {
console.log(json);
    app.leaderboard = json
})
var app = new Vue({
    el: "#app",
    data: {
        games: [],
        leaderboard:[]
    }
});



