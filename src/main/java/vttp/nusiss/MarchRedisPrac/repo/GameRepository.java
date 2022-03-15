package vttp.nusiss.MarchRedisPrac.repo;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import vttp.nusiss.MarchRedisPrac.models.Game;

@Repository
public class GameRepository{
    
    @Autowired
    @Qualifier("games")
    private RedisTemplate<String, String> template;
    
    public Set<String> getKeys(String key){
        return template.keys(key);
    }

    public Optional<Game> getGameByGid(String gid){
        String rec = (String)template.opsForHash().get(gid,"rec");
        if(null != rec)
            return Optional.of(Game.create(rec));
        
        return Optional.empty();
        
    }

}

