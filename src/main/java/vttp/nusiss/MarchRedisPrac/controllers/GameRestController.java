package vttp.nusiss.MarchRedisPrac.controllers;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import vttp.nusiss.MarchRedisPrac.models.Game;
import vttp.nusiss.MarchRedisPrac.repo.GameRepository;
import vttp.nusiss.MarchRedisPrac.services.GameService;

@RestController
@RequestMapping(path="/game", produces = MediaType.APPLICATION_JSON_VALUE)
public class GameRestController {
    
    @Autowired
    private GameService gameSvc;
    
    @Autowired
    private GameRepository gameRepo;

    @GetMapping(path="/{gid}")
    public ResponseEntity<String> getGameById(@PathVariable String gid){
        
        Optional<Game> opt = gameRepo.getGameByGid(gid);
        if(opt.isEmpty()){
            JsonObject result = Json.createObjectBuilder()
            .add("error","gid=%s not found".formatted(gid))
            .build();
            return ResponseEntity.status(404).body(result.toString());

        }
        Game game = opt.get();
        
    return ResponseEntity.ok(game.toJson().toString());    
    }

    @GetMapping(path="/search")
    public ResponseEntity<String> getGameSearch(@RequestParam String pattern){

        Set<String> gids = gameSvc.searchKeys(pattern);
        
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        gids.stream()
            .sorted()
            .map(gid -> {
                return "/game/%s".formatted(gid);
            })
            .forEach(url -> {
                arrBuilder.add(url);
            });

            return ResponseEntity.ok(arrBuilder.build().toString());




    }


}
