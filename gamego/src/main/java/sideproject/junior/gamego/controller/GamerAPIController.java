package sideproject.junior.gamego.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sideproject.junior.gamego.model.dto.GamerDTO;
import sideproject.junior.gamego.model.entity.Gamer;
import sideproject.junior.gamego.service.GamerService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/v1")
public class GamerAPIController {

    private final GamerService gamerService;

    @GetMapping("/GamerList")
    public ResponseEntity<List<Gamer>> getGamerListApi(){
        List<Gamer> gamerList = gamerService.getGamerListApi();
        return new ResponseEntity<>(gamerList, HttpStatus.OK);
    }

    @GetMapping("/gamer/{id}")
    public ResponseEntity<Gamer> getGamerState(@PathVariable Long id){
        Gamer gamer = gamerService.getGamerApi(id);
        return new ResponseEntity<>(gamer,HttpStatus.OK);
    }

    @PostMapping("/gamer")
    public ResponseEntity<?> gamerRegistation(GamerDTO.GamerRegistationDTO gamerRegistationDTO){
        gamerService.registationGamerApi(gamerRegistationDTO);
        return new ResponseEntity<>("게이머등록 API 성공",HttpStatus.OK);
    }

    @DeleteMapping("/gamer/{id}")
    public ResponseEntity<?> gamerDelete(@PathVariable Long id){
        gamerService.deleteGamerApi(id);
    }
}
