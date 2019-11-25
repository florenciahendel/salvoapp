function getGames(){
fetch("/api/games")
.then(res => res.json())
.then(json => {
data = json
console.log(json);
    app.games = json.games
    app.player = json.player
})


fetch("/api/leaderboard")
.then(res => res.json())
.then(json => {
console.log(json);
    app.leaderboard = json
})
}
getGames()



var app = new Vue({
    el: "#app",
    data: {
        games: [],
        leaderboard:[],
        player: {}
    },
    methods: {
        login(evt) {
        		   evt.preventDefault();

        		   let formData = new FormData(evt.target)


        		   fetch('/api/login',{
        				method: 'POST',
        				body: formData,

        			})
        			.then((res)=> res)
        			.then(json =>{
        				getGames()
        			})
        			.catch((error)=> console.log(error))

        		},
        logout(){
    			fetch('/api/logout').then(() => getGames())
    		},
        createGame(){
    			fetch('/api/games',{
    				method: 'POST'
    			})
    			.then(res => {
    				if(res.ok){
    					return res.json()
    				}else{
    					return Promise.reject(res.json())
    				}
    			})
    			.then(json => {

    				location.href = '/web/game.html?gp=' + json.gpId
    			})
    			.catch(error => error)
    			.then(error => console.log(error))
    		},
    	signup(evt) {
                   		   evt.preventDefault();

                   		   let formData = new FormData(evt.target)


                   		   fetch('/api/players',{
                   				method: 'POST',
                   				body: formData,

                    			})
                    	    .then((res)=> res)
                    		.then(json =>{
                    	//		getGames()

                   				//$("#signup").hide()
                app.login(evt)
                    			})
                   			.catch((error)=> console.log(error))

                    		},
    	joinGame(gameId){
    		fetch('/api/games/' + gameId + '/players', {
   				method: 'POST'
    			})
   			.then(res => {
   				if(res.ok){
   					return res.json()
   				}else{
    					return Promise.reject(res.json())
    				}
    			})
    			.then(json => {
    				console.log(json)
    				location.href = "/web/game.html?gp=" + json.gpId
    			})
    			.catch(error => error)
    			.then(error => console.log(error))
    		}
        }
    });


/*    document.querySelector('#login').onsubmit = login
    document.querySelector('#signup').onsubmit = signup

    function login(evt) {
       evt.preventDefault();

       let formData = new FormData(evt.target)


       fetch('/api/login',{
    		method: 'POST',
    		body: formData,

    	})
    	.then((res)=> res)
    	.then(json =>{
    		console.log(json)
    		getGames()
    	})
    	.catch((error)=> console.log(error))

    }

function signup(evt){
       evt.preventDefault();

       let formData = new FormData(evt.target)


       fetch('/api/players',{
    		method: 'POST',
    		body: formData,

    	})
    	.then((res)=> res)
    	.then(json =>{
    		console.log(json)
    		getGames()
    	})
    	.catch((error)=> console.log(error))

}*/
