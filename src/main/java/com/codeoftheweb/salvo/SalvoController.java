package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class SalvoController {

    @Autowired
    private GameRepository repo;

    @Autowired
    private GamePlayerRepository repo2;

    @RequestMapping("/games")
    public List<Map> getGames() {
        return repo.findAll()
                .stream()
                .map(game -> game.toDTO())
                .collect(toList());
    }

    @RequestMapping("/game_view/{gamePlayerId}")
    public Map<String, Object> getGamePlayer (@PathVariable Long gamePlayerId){
        return repo2.findById(gamePlayerId)
                .get()
                .toGameViewDTO();
   }
}

