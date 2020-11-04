package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;
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

    @OneToMany(mappedBy="gamePlayer", fetch=FetchType.EAGER)
    private Set<Ship> ships;

    @OneToMany(mappedBy="gamePlayer", fetch=FetchType.EAGER)
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
        return dto;
    }
}