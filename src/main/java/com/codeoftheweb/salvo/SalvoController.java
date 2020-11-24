package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.management.ObjectName;
import java.time.LocalDateTime;
import java.util.*;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class SalvoController {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GamePlayerRepository gamePlayerRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/games")
    public Map<String, Object> getGames(Authentication authentication) {
        Map<String, Object> DTO = new LinkedHashMap<String, Object>();
        if (authentication == null) {
            DTO.put("player", null);
        }else{
            Player player = playerRepository.findByUserName(authentication.getName());
            DTO.put("player", player.toDTO());
        }
        DTO.put("games", gameRepository.findAll()
                .stream()
                .map(game -> game.toDTO())
                .collect(toList()));
        return DTO;
    }

    @RequestMapping(path = "/games", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createGame(Authentication authentication) {
        if (authentication == null) {
            return new ResponseEntity<>(makeMap("error", "You must have a user to create a new game"), HttpStatus.FORBIDDEN);
        }else {
            Game game = new Game(LocalDateTime.now());
            gameRepository.save(game);
            Player player = playerRepository.findByUserName(authentication.getName());
            GamePlayer gamePlayer = new GamePlayer(player, game);
            gamePlayerRepository.save(gamePlayer);
            return new ResponseEntity<>(makeMap("gpid", gamePlayer.getId()), HttpStatus.CREATED);
        }
    }
    
    @RequestMapping(path = "/games/{gameId}/players", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> joinGame(Authentication authentication, @PathVariable Long gameId) {
        if (authentication == null) {
            return new ResponseEntity<>(makeMap("error", "You must have a user to create a new game"), HttpStatus.FORBIDDEN);
        }else {
            Game game = gameRepository.findById(gameId).orElse(null);
            gameRepository.save(game);
            if (game == null) {
                return new ResponseEntity<>(makeMap("error", "Not Found"), HttpStatus.NOT_FOUND);
            } else if  (game.getGamePlayers().size() > 1){
                return new ResponseEntity<>(makeMap("error", "Full Game"), HttpStatus.FORBIDDEN);
            } else {
                Player player = playerRepository.findByUserName(authentication.getName());
                GamePlayer newGamePlayer = new GamePlayer(player, game);
                gamePlayerRepository.save(newGamePlayer);
                return new ResponseEntity<>(makeMap("gpid", newGamePlayer.getId()), HttpStatus.CREATED);
            }
            }
        }


    @RequestMapping("/game_view/{gamePlayerId}")
    public ResponseEntity <Map<String, Object>> getGamePlayer (@PathVariable Long gamePlayerId, Authentication authentication) {
        Optional<GamePlayer> gamePlayer = gamePlayerRepository.findById(gamePlayerId);
        Player player = playerRepository.findByUserName(authentication.getName());
        if (gamePlayer.get().getPlayer().getId() == player.getId()) {
            return new ResponseEntity<>(makeMap("gp", gamePlayer.get().toGameViewDTO()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(makeMap("error", "unauthorized"), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(path = "/players", method = RequestMethod.POST)
    public ResponseEntity<Object> register(
            @RequestParam String username, @RequestParam String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (playerRepository.findByUserName(username) !=  null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        playerRepository.save(new Player(username, passwordEncoder.encode(password)));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }
}