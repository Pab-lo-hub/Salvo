var app = new Vue({
    el: '#app',
    data: {
        columns: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
        rows: ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'],
        gameView: null,
        player1: null,
        player2: null,
    },
    methods: {
        drawShips: function () {
            for (var i = 0; i < app.gameView.ships.length; i++) {
                for (var j = 0; j < app.gameView.ships[i].locations.length; j++) {
                    document.getElementById(app.gameView.ships[i].locations[j]).classList.add('barco');
                }
            }
        },
        drawSalvos: function () {
            for (var k = 0; k < app.gameView.salvos.length; k++) {
                for (var h = 0; h < app.gameView.salvos[k].salvolocations.length; h++) {
                    if (app.gameView.salvos[k].playerId == app.player1.id) {
                        document.getElementById(app.gameView.salvos[k].salvolocations[h] + 'S').classList.add('shot');
                    } else {
                        for (var i = 0; i < app.gameView.ships.length; i++) {
                            for (var j = 0; j < app.gameView.ships[i].locations.length; j++) {
                                if (app.gameView.ships[i].locations[j] == app.gameView.salvos[k].salvolocations[h]){
                                   document.getElementById(app.gameView.salvos[k].salvolocations[h]).classList.add('sunkenship'); 
                                }
                            }
                        }
                    }
                }
            }
        },
        playersplaying: function () {
            if (gp == app.gameView.gamePlayers[0].player.id) {
                app.player1 = app.gameView.gamePlayers[0].player;
                app.player2 = app.gameView.gamePlayers[1] != null ? app.gameView.gamePlayers[1].player : null;
            } else {
                app.player1 = app.gameView.gamePlayers[1].player;
                app.player2 = app.gameView.gamePlayers[0].player;
            }
        },
    }
})

var urlParams = new URLSearchParams(window.location.search);
var gp = urlParams.get('gp');

var api = "/api/game_view/" + gp;

fetch(api)
    .then(function (response) {
        if (response.ok) {
            return response.json();
        }
        throw new Error(response.status);
    })
    .then(function (json) {
        app.gameView = json;
        app.playersplaying();
        app.drawShips();
        app.drawSalvos();
        console.log(json);
    })