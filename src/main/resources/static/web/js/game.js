var app = new Vue({
    el: '#app',
    data: {
        columns: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
        rows: ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'],
        gameView: null,
        player1: null,
        player2: null,
        shipLocations: null,
        salvoLocations: null,
        shipTypes: ["Aircraft Carrier", "Battleship", "Submarine", "Destroyer", "Patrol Boat"],
        shipLength: null,
        shipOrientation: "horizontal",
        selectedShip: "Aircraft Carrier",
        shipsToPost: [],
        shipToCompare: null,
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
                                if (app.gameView.ships[i].locations[j] == app.gameView.salvos[k].salvolocations[h]) {
                                    document.getElementById(app.gameView.salvos[k].salvolocations[h]).classList.add('sunkenship');
                                }
                            }
                        }
                    }
                }
            }
        },
        playersplaying: function () {
            if (gp == app.gameView.gamePlayers[0].id) {
                app.player1 = app.gameView.gamePlayers[0].player;
                app.player2 = app.gameView.gamePlayers[1] != null ? app.gameView.gamePlayers[1].player : null;
            } else {
                app.player1 = app.gameView.gamePlayers[1].player;
                app.player2 = app.gameView.gamePlayers[0].player;
            }
        },
        placeShips: function () {
            $.post({
                    url: "/api/games/players/" + gp + "/ships",
                    data: JSON.stringify(app.shipsToPost),
                    dataType: "text",
                    contentType: "application/json"
                })
                .done(function () {
                    alert("Ships Placed");
                    location.reload();
                })
                .fail(function () {
                    alert("Failed to add ships. You must add 5 ships");
                })
        },
        rotateShip: function () {
            if (app.shipOrientation == "horizontal") {
                app.shipOrientation = "vertical";
            } else if (app.shipOrientation == "vertical") {
                app.shipOrientation = "horizontal";
            }
        },
        sendCellId: function (letter, number) {
            var shipToPost = {
                "shipType": app.selectedShip,
                "shipLocations": [],
            };

            if ((app.shipsToPost.findIndex(ship => ship.shipType === app.selectedShip) != -1)) {
                alert("You can´t add the same ship twice");
            } else {

                console.log(letter, number);
                console.log(app.selectedShip);
                console.log(app.shipOrientation);
                app.lengthOfShip(app.selectedShip);
                console.log(app.shipLength);

                var error = false;

                if (app.shipOrientation == "horizontal") {
                    if ((app.shipLength + (number - 1)) > 10) {
                        error = true;
                    } else {
                        for (let l = 0; l < app.shipLength; l++) {
                            if (document.getElementById(letter + (number + l)).classList == 'shipsToPlace') {
                                error = true;
                                break;
                            }
                        }
                    }
                    if (!error) {
                        for (let l = 0; l < app.shipLength; l++) {
                            document.getElementById(letter + (number + l)).classList.add('shipsToPlace');
                            shipToPost.shipLocations.push(letter + (number + l));
                        }
                    } else {
                        alert("You can´t place a ship here");
                    }

                } else {
                    let letterString = letter;
                    let letterInRows = (app.rows.indexOf(letterString));
                    if ((app.shipLength + (letterInRows)) > 10) {
                        error = true;
                    } else {
                        for (let l = 0; l < app.shipLength; l++) {
                            let letterString = letter;
                            let letterInRows = (app.rows.indexOf(letterString));
                            let finalLetterInRows = app.rows[letterInRows + l];
                            console.log(finalLetterInRows);
                            if (document.getElementById((finalLetterInRows) + number).classList == 'shipsToPlace') {
                                error = true;
                                break;
                            }
                        }
                    }
                    if (!error) {
                        for (let l = 0; l < app.shipLength; l++) {
                            let letterString = letter;
                            let letterInRows = (app.rows.indexOf(letterString));
                            let finalLetterInRows = app.rows[letterInRows + l];
                            console.log(finalLetterInRows);
                            document.getElementById((finalLetterInRows) + number).classList.add('shipsToPlace');
                            shipToPost.shipLocations.push((finalLetterInRows) + number);
                        }
                    } else {
                        alert("You can´t place a ship here");
                    }
                }
                if (!error) {
                    app.shipsToPost.push(shipToPost);
                    var shipDelete = app.shipTypes.indexOf(app.selectedShip);
                    app.shipTypes.splice(shipDelete, 1);
                }
            }
        },

        hoverShips: function (letter, number) {
            app.lengthOfShip(app.selectedShip);

            var error = false;
            if (app.shipOrientation == "horizontal") {
                if ((app.shipLength + (number - 1)) > 10) {
                    error = true;
                } else {
                    for (let l = 0; l < app.shipLength; l++) {
                        if (document.getElementById(letter + (number + l)).classList == 'shipsToPlace') {
                            error = true;
                            break;
                        }
                    }
                }
                if (!error) {
                    for (let l = 0; l < app.shipLength; l++) {
                        document.getElementById(letter + (number + l)).classList.add('hoverShipsToPlace');
                    }
                }
            } else {
                let letterString = letter;
                let letterInRows = (app.rows.indexOf(letterString));
                if ((app.shipLength + (letterInRows)) > 10) {
                    error = true;
                } else {
                    for (let l = 0; l < app.shipLength; l++) {
                        let letterString = letter;
                        let letterInRows = (app.rows.indexOf(letterString));
                        let finalLetterInRows = app.rows[letterInRows + l];
                        if (document.getElementById((finalLetterInRows) + number).classList == 'shipsToPlace') {
                            error = true;
                            break;
                        }
                    }
                }
                if (!error) {
                    for (let l = 0; l < app.shipLength; l++) {
                        let letterString = letter;
                        let letterInRows = (app.rows.indexOf(letterString));
                        let finalLetterInRows = app.rows[letterInRows + l];
                        document.getElementById((finalLetterInRows) + number).classList.add('hoverShipsToPlace');
                    }
                }
            }
        },

        cleanHover: function () {
            let cells = Array.from(document.getElementsByClassName('hoverShipsToPlace'));
            cells.forEach(element => {
                element.classList.remove('hoverShipsToPlace');
            });
        },

        lengthOfShip: function (selectedShip) {
            if (selectedShip == "Aircraft Carrier") {
                app.shipLength = 5;
            } else if (selectedShip == "Battleship") {
                app.shipLength = 4;
            } else if (selectedShip == "Submarine") {
                app.shipLength = 3;
            } else if (selectedShip == "Destroyer") {
                app.shipLength = 3;
            } else if (selectedShip == "Patrol Boat") {
                app.shipLength = 2;
            }
        }
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
        app.gameView = json.gp;
        app.playersplaying();
        app.drawShips();
        app.drawSalvos();
        console.log(json);
    })