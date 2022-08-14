package sideproject.junior.gamego.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sideproject.junior.gamego.model.entity.Game;
import sideproject.junior.gamego.repository.GameRepository;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GameService {
    private final GameRepository gameRepository;

    public boolean gameNameCheckApi(String name){
        Optional<Game> findGame = gameRepository.findByName(name);
        if (findGame.isPresent()){
            return true;
        }else {
            return false;
        }
    }
}
