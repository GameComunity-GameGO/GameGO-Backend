package sideproject.junior.gamego.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sideproject.junior.gamego.model.dto.MemberDTO;
import sideproject.junior.gamego.service.SignUpService;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/v1/SignUp")
public class SignUpAPIController {

    private final SignUpService signUpService;

    @PostMapping("/existUsername")
    public ResponseEntity<Boolean> existUsernameCheck(@RequestBody MemberDTO.OnlyUsernameDTO onlyUsernameDTO){
        boolean checkExist = signUpService.checkExistUsername(onlyUsernameDTO.getUsername());
        return new ResponseEntity<>(checkExist, HttpStatus.OK);
    }

    @PostMapping("/existNickname")
    public ResponseEntity<Boolean> existNicknameCheck(@RequestBody MemberDTO.OnlyNicknameDTO onlyNicknameDTO){
        boolean checkExist = signUpService.checkExistNickname(onlyNicknameDTO.getNickname());
        return new ResponseEntity<>(checkExist, HttpStatus.OK);
    }

    @PostMapping("/samePassword")
    public ResponseEntity<?> samePasswordCheck(@RequestBody MemberDTO.SamePasswordCheckDTO samePasswordCheckDTO){
        boolean samePassword = signUpService.checkSamePassword(samePasswordCheckDTO.getPassword(), samePasswordCheckDTO.getRepassword());
        return new ResponseEntity<>(samePassword,HttpStatus.OK);
    }

}
