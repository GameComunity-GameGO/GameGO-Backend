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
    public ResponseEntity<?> gamerRegistation(@RequestBody  GamerDTO.GamerRegistationDTO gamerRegistationDTO){
        ResponseEntity<?> responseEntity = gamerService.registationGamerApi(gamerRegistationDTO);
        return new ResponseEntity<>(responseEntity,HttpStatus.OK);
    }

    @DeleteMapping("/gamer/{id}")
    public ResponseEntity<?> gamerDelete(@PathVariable Long id){
        gamerService.deleteGamerApi(id);
        return new ResponseEntity<>("회원 삭제 API 완료",HttpStatus.OK);
    }
}
