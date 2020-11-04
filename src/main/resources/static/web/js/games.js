var app = new Vue({
  el: '#app',
  data: {
    games: {},
    leaderboard: [],
  },
  methods: {
    getNames: function () {
      var names = [];
      for (var i = 0; i < app.games.length; i++) {
        for (var j = 0; j < app.games[i].gamePlayers.length; j++) {
          if (!names.includes(app.games[i].gamePlayers[j].player.name)) {
            names.push(app.games[i].gamePlayers[j].player.name);
          }
        }
      }
      return names;
    },
    totalScore: function (name) {
      var total = 0;
      for (var i = 0; i < app.games.length; i++) {
        for (var j = 0; j < app.games[i].gamePlayers.length; j++) {
          if (app.games[i].gamePlayers[j].player.name == name) {
            total += app.games[i].gamePlayers[j].score;
          }
        }
      }
      return total;
    },
    wins: function (name) {
      var total = 0;
      for (var i = 0; i < app.games.length; i++) {
        for (var j = 0; j < app.games[i].gamePlayers.length; j++) {
          if (app.games[i].gamePlayers[j].player.name == name) {
            if (app.games[i].gamePlayers[j].score == 1.0) {
              total++;
            }
          }
        }
      }
      return total;
    },
    lost: function (name) {
      var total = 0;
      for (var i = 0; i < app.games.length; i++) {
        for (var j = 0; j < app.games[i].gamePlayers.length; j++) {
          if (app.games[i].gamePlayers[j].player.name == name) {
            if (app.games[i].gamePlayers[j].score == 0) {
              total++;
            }
          }
        }
      }
      return total;
    },
    tie: function (name) {
      var total = 0;
      for (var i = 0; i < app.games.length; i++) {
        for (var j = 0; j < app.games[i].gamePlayers.length; j++) {
          if (app.games[i].gamePlayers[j].player.name == name) {
            if (app.games[i].gamePlayers[j].score == 0.5) {
              total++;
            }
          }
        }
      }
      return total;
    },
    getLeaderboard: function () {
      var scores = [];
      var namesList = app.getNames();
      for (var i = 0; i < namesList.length; i++) {
        var playerScore = {
          names: namesList[i],
          won: app.wins(namesList[i]),
          tie: app.tie(namesList[i]),
          lost: app.lost(namesList[i]),
          total: app.totalScore(namesList[i])
        }
        scores.push(playerScore);
      }
      return scores;
    }
  },
  filters: {
    capitalize: function (value) {
      if (!value) return ''
      return moment(value).format('DD/MM/YYYY HH:mm:ss A');
    }
  },
});

fetch("http://localhost:8080/api/games", {
    method: 'GET',
    headers: {}
  })
  .then(function (response) {
    if (response.ok) {
      return response.json();
    } else {
      throw new Error(response.statusText)
    };
  })
  .then(function (json) {
    app.games = json
    app.leaderboard = app.getLeaderboard()
    console.log(json);
  }).catch(function (error) {
    console.log("Request failed: " + error.message);
  });