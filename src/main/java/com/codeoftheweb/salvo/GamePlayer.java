package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;

import static java.util.stream.Collectors.toList;

@Entity
public class GamePlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="player_id")
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="game_id")
    private Game game;

    @OneToMany(mappedBy="gamePlayer", fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Ship> ships;

    @OneToMany(mappedBy="gamePlayer", fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Salvo> salvos;

    private LocalDateTime date;

    public GamePlayer () {}

    public GamePlayer (Player player, Game game) {
        this.date = LocalDateTime.now();
        this.player = player;
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public Game getGame() {
        return game;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public long getId() {
        return id;
    }

    public Set<Ship> getShips() {
        return ships;
    }

    public void setShips(Set<Ship> ships) {
        this.ships = ships;
    }

    public void addShip(Ship ship) {
        ship.setGamePlayer(this);
        ships.add(ship);
    }

    public Set<Salvo> getSalvos() {
        return salvos;
    }

    public Score getScoreInGP() {
        return player.getScore(game);
    }

    public Map<String, Object> toDTO() {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", id);
        dto.put("player", player.toDTO());
        dto.put("score", getScoreInGP() != null ? getScoreInGP().getScore() : null);
        return dto;
    }

    public Map<String, Object> toGameViewDTO() {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", id);
        dto.put("created", date);
        dto.put("gamePlayers", game.getGamePlayers().stream()
                .map(GamePlayer::toDTO)
                .collect(toList()));
        dto.put("ships", ships.stream()
                .map(Ship::toDTO)
                .collect(toList()));
        dto.put("salvos", this.game.getGamePlayers().stream()
                .flatMap(gp -> gp.getSalvos()
                .stream()
                        .map(Salvo::toDTO))
                        .collect(toList()));
        dto.put("hits", this.getSalvos().stream().map(Salvo::hitDTO));
        dto.put("sunk", this.getSalvos().stream().map(Salvo::sunkDTO));
        Optional<GamePlayer> opponent = this.getOpponent();
        if(opponent.isPresent()) {
            dto.put("enemyHits", opponent.get().getSalvos().stream().map(Salvo::hitDTO));
            dto.put("enemySunk", opponent.get().getSalvos().stream().map(Salvo::sunkDTO));
        } else {
            dto.put("enemyHits", new ArrayList<>());
            dto.put("enemySunk", new ArrayList<>());
        };
        dto.put("gameStatus", this.getGameStatus());
        return dto;
    }

    public void setSalvos(Set<Salvo> salvos) {
        this.salvos = salvos;
    }

    public void addSalvo(Salvo salvo) {
            salvo.setGamePlayer(this);
            salvos.add(salvo);
        }

    public Optional<GamePlayer> getOpponent(){
        return this.getGame().getGamePlayers()
                .stream()
                .filter(gpopponent -> gpopponent.getId() != this.id)
                .findFirst();
    }

    public String getGameStatus(){
        Optional<GamePlayer> opponent = this.getOpponent();
        Optional<Salvo> lastSalvo = this.salvos.stream().filter(salvo -> salvo.getTurn() == this.salvos.size()).findFirst();
        if(this.ships.isEmpty()){
            return "PLACE_SHIPS";
        }else if(opponent.isEmpty()){
            return "WAIT_OPPONENT";
        }else{
            Optional<Salvo> lastSalvoOpponent = opponent.get().salvos.stream().filter(salvo -> salvo.getTurn() == opponent.get().salvos.size()).findFirst();
            int turno = 0;
            int sunks = 0;
            int turnoOpponet = 0;
            int sunksOpponent = 0;
            if(lastSalvo.isPresent()){
                turno = lastSalvo.get().getTurn();
                sunks = lastSalvo.get().getSunkShips().size();
            };
            if(lastSalvoOpponent.isPresent()) {
                turnoOpponet = lastSalvoOpponent.get().getTurn();
                sunksOpponent = lastSalvoOpponent.get().getSunkShips().size();
            };
            if(turno < turnoOpponet){
                return "FIRE";
            }else if(turno > turnoOpponet){
                return "WAIT";
            }else {
                if(sunks < 5 && sunksOpponent == 5){
                    return "LOST";
                }else if (sunks == 5  && sunksOpponent < 5){
                    return "WIN";
                }else if(sunks == 5 && sunksOpponent == 5){
                    return "TIE";
                }else{
                    if(this.id < opponent.get().getId()){
                        return "FIRE";
                    }else{
                        return "WAIT";
                    }
                }
            }
        }
    }
}