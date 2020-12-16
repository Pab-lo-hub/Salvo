package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;


@Entity
public class Salvo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="gameplayer_id")
    private GamePlayer gamePlayer;

    public int turn;

    @ElementCollection
    @Column(name="salvoLocations")
    List<String> salvoLocations = new ArrayList<>();

    public Salvo () {};

    public Salvo (int turn, List<String> salvoLocations, GamePlayer gamePlayer) {
        this.turn = turn;
        this.salvoLocations = salvoLocations;
        this.gamePlayer = gamePlayer;
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public List<String> getSalvoLocations() {
        return salvoLocations;
    }

    public void setSalvoLocations(List<String> salvoLocations) {
        this.salvoLocations = salvoLocations;
    }

    private List<String> getHits() {
        Optional <GamePlayer> opponent = this.gamePlayer.getOpponent();
        if (opponent.isPresent()){
            List<String> myShots = this.salvoLocations;
            List<String> opponentLocations = new ArrayList<>();
            Set<Ship> opponentShips = opponent.get().getShips();
            opponentShips.forEach(ship -> opponentLocations.addAll(ship.getShipLocations()));
            return myShots.stream().filter(shot -> opponentLocations.contains(shot))
                    .collect(Collectors.toList());
        }else {
            return new ArrayList<>();
        }
    }

    public List<Ship> getSunkShips() {
        Optional <GamePlayer> opponent = this.gamePlayer.getOpponent();
        if (opponent.isPresent()) {
            List<String> allShots = new ArrayList<>();
            Set<Salvo> mySalvos = this.getGamePlayer().getSalvos().stream()
                    .filter(salvo -> salvo.getTurn() <= this.getTurn()).collect(Collectors.toSet());
            Set<Ship> opponentShips = opponent.get().getShips();
            mySalvos.forEach(salvo -> allShots.addAll(salvo.getSalvoLocations()));
            return opponentShips.stream().filter(ship -> allShots.containsAll(ship.getShipLocations()))
                    .collect(Collectors.toList());
        }else {
            return new ArrayList<>();
        }
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public Map<String, Object> toDTO() {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", id);
        dto.put("playerId", gamePlayer.getPlayer().getId());
        dto.put("turn", turn);
        dto.put("salvolocations", salvoLocations);
        return dto;
    }

    public Map<String, Object> hitDTO() {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("turn", this.turn);
        dto.put("hits", this.getHits());
        return dto;
    }

    public Map<String, Object> sunkDTO() {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("turn", this.turn);
        dto.put("sunk", this.getSunkShips().stream().map(Ship::toDTO));
        return dto;
    }
}
