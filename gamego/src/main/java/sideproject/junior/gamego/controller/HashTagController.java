package sideproject.junior.gamego.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sideproject.junior.gamego.model.dto.HashTagDTO;
import sideproject.junior.gamego.model.entity.HashTag;
import sideproject.junior.gamego.service.HashTagService;

import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class HashTagController {

    private final HashTagService hashTagService;

    @GetMapping("/hashTags")
    public ResponseEntity<List<HashTag>> getHashTageListApi(){
        List<HashTag> hashTagList = hashTagService.getHashTagListApi();
        return new ResponseEntity<>(hashTagList, HttpStatus.OK);
    }

    @PostMapping("/hashTag")
    public ResponseEntity<?> hashTagRegistrationAPi(@RequestBody HashTagDTO.RegistrationDTO registrationDTO){
        ResponseEntity<?> returnResponse = hashTagService.hashTagRegistrationApi(registrationDTO);
        return returnResponse;
    }
}
