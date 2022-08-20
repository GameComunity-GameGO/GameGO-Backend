package sideproject.junior.gamego.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sideproject.junior.gamego.exception.game.GameException;
import sideproject.junior.gamego.exception.game.GameExceptionType;
import sideproject.junior.gamego.model.dto.GameDTO;
import sideproject.junior.gamego.model.entity.Game;
import sideproject.junior.gamego.repository.GameRepository;

import java.util.List;
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

    public List<Game> getGameListApi(){
        return gameRepository.findAll();
    }

    public Game getGameStateApi(Long id){
        return gameRepository.findById(id).get();
    }

    @Transactional
    public ResponseEntity<?> gameRegistrationApi(GameDTO.RegistrationDTO registrationDTO){
        if (registrationDTO.getName()==null||registrationDTO.getContent()==null) return new ResponseEntity<>(new GameException(GameExceptionType.NAME_OR_CONTENT_NULL), HttpStatus.OK);
        else {
            Game gameEntity = registrationDTO.toEntity();
            gameRepository.save(gameEntity);
            return new ResponseEntity<>("게임 등록 API 성공!",HttpStatus.OK);
        }
    }

    @Transactional
    public ResponseEntity<?> changeStateApi(Long id,GameDTO.ChangeStateDTO changeStateDTO){
        if(id==null||changeStateDTO.getName()==null||changeStateDTO.getContent()==null){
            return new ResponseEntity<>(new GameException(GameExceptionType.CHANGE_API_REQUEST_NULL),HttpStatus.OK);
        }else {
            Optional<Game> findGame = gameRepository.findById(id);
            if (findGame.isPresent()){
                findGame.get().setChangeState(changeStateDTO.getName(), changeStateDTO.getContent());
                return new ResponseEntity<>("게임 변경 API 완료!",HttpStatus.OK);
            }else {
                return new ResponseEntity<>("게임 변경 API 호출 실패!",HttpStatus.BAD_REQUEST);
            }
        }
    }


    @Transactional
    public ResponseEntity<?> deleteGameApi(Long id) {
        if (id==null){
            return new ResponseEntity<>(new GameException(GameExceptionType.DELETE_API_REQUEST_NULL),HttpStatus.OK);
        }else {
            Optional<Game> findGame = gameRepository.findById(id);
            if (findGame.isPresent()){
                gameRepository.delete(findGame.get());
                return new ResponseEntity<>("게임 삭제 API 성공!",HttpStatus.OK);
            }else {
                return new ResponseEntity<>("게임 삭제 API 실패!",HttpStatus.OK);
            }
        }
    }
}
