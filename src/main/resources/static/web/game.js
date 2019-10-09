$(function() {

   // parametros de URL
  const urlParams = new URLSearchParams(window.location.search);

    // encabezados de la grilla
    let numbers = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10"];
    let letters = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J"];

    // posiciones de todos los barcos del jugador
    let locations = [];

    // genera el HTML de los encabezados de la grilla
    function getHeadersHtml(headers) {
        return "<tr class='table-dark text-center align-middle'><th></th>" + headers.map(function(header) {
            return "<th>" + header + "</th>";
        }).join("") + "</tr>";
    }

    // dibuja los encabezados
    function renderHeaders() {
        var html = getHeadersHtml(numbers);
        document.getElementById("grid-numbers").innerHTML = html;
    }

    // genera el HTML de las columnas
    function getColumnsHtml(i) {
        let html = "";
        for (let j = 0; j < numbers.length; j++) {
            //let cellContent = "";
            let cellColor = "lightblue";
            for (let k = 0; k < locations.length; k++) {
                if (locations[k] == letters[i] + numbers[j]) {
                    //cellContent = "si";
                    cellColor = "black";
                }
            }
            html = html + "<td style='background-color: " + cellColor + "'></td>";
        }
        return html;
    }

    // genera el HTML de las filas (depende de getColumnsHtml)
    function getRowsHtml() {
        let html = "";
        for (let i = 0; i < letters.length; i ++) {
            html = html + "<tr><th class='table-dark text-center align-middle'>" + letters[i] + "</th>" + getColumnsHtml(i) + "</tr>";
        }
        return html;
    }

    // dibuja las filas de la grilla
    function renderRows() {
        var html = getRowsHtml();
        document.getElementById("grid-rows").innerHTML = html;
    }

    // dibuja la grilla
    function renderTable() {
        renderHeaders();
        renderRows();
    }

    // muestra los datos de los jugadores de la partida
    function showPlayersData(data) {
        let thisPlayer;
        let otherPlayer;
        let gamePlayer1 = data.playersInThisGame[0];
        let gamePlayer2 = data.playersInThisGame[1];
        // según el ID del gameplayer actual asigna thisPlayer al jugador correspondiente, otherPlayer al contrincante
        if (gamePlayer1.id == urlParams.get('gp')) {
            thisPlayer = gamePlayer1.player.username;
            otherPlayer = gamePlayer2.player.username;
        }
        else {
            thisPlayer = gamePlayer2.player.username;
            otherPlayer = gamePlayer1.player.username;
        }
        document.getElementById("players-data").innerHTML = thisPlayer + " (you) vs " + otherPlayer;
    }

    // recibe los datos del gameplayer y setea en el array locations las posiciones de todos los barcos del jugador
    function setLocations(data) {
        mappedLocations = data.ships.map(function(ship) { return ship.locations });
        locations = [].concat.apply([], mappedLocations);
    }

    // carga los datos del gameplayer según el parámetro 'gp' en la URL y llama a los métodos que dibujan la grilla
    function loadData() {
        $.get("/api/game_view/"+urlParams.get('gp'))
            .done(function(data) {
               console.log(data);
               setLocations(data);
               showPlayersData(data);
               renderTable();
            })
            .fail(function( jqXHR, textStatus ) {
                alert( "Failed: " + textStatus );
                renderTable();
            });
    }

  loadData();

});

