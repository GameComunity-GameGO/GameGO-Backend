package sideproject.junior.gamego.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sideproject.junior.gamego.model.dto.GameDTO;
import sideproject.junior.gamego.model.entity.Game;
import sideproject.junior.gamego.service.GameService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/v1")
public class GameApiController {

    private final GameService gameService;

    @GetMapping("/games")
    public ResponseEntity<List<Game>> getGameList(){
        List<Game> gameList = gameService.getGameListApi();
        return new ResponseEntity<>(gameList, HttpStatus.OK);
    }

    @GetMapping("/game/{id}")
    public ResponseEntity<Game> getGameState(@PathVariable Long id){
        Game gameState = gameService.getGameStateApi(id);
        return new ResponseEntity<>(gameState,HttpStatus.OK);
    }

    @PostMapping("/game")
    public ResponseEntity<?> gameRegistrationApi(GameDTO.RegistrationDTO registrationDTO){
        ResponseEntity<?> returnResponse = gameService.gameRegistrationApi(registrationDTO);
        return returnResponse;
    }

    @PutMapping("/game/{id}")
    public ResponseEntity<?> changeStateApi(@PathVariable Long id,@RequestBody GameDTO.ChangeStateDTO changeStateDTO){
        ResponseEntity<?> returnResponse = gameService.changeStateApi(id, changeStateDTO);
        return returnResponse;
    }

    @DeleteMapping("/game/{id}")
    public ResponseEntity<?> deleteGameApi(@PathVariable Long id){
        ResponseEntity<?> returnResponse = gameService.deleteGameApi(id);
        return returnResponse;
    }
}
