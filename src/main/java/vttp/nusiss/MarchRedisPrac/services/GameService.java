package vttp.nusiss.MarchRedisPrac.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.nusiss.MarchRedisPrac.repo.GameRepository;

@Service
public class GameService {
    
    @Autowired
    private GameRepository gameRepo;

    public Set<String> searchKeys(String index){
        String pattern="*%s*".formatted(index);
        return gameRepo.getKeys(pattern);
    }

}
