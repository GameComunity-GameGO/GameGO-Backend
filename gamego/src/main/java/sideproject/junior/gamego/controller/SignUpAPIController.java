package sideproject.junior.gamego.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sideproject.junior.gamego.exception.member.MemberException;
import sideproject.junior.gamego.exception.member.MemberExceptionType;
import sideproject.junior.gamego.model.dto.MemberDTO;
import sideproject.junior.gamego.service.SignUpService;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SignUpAPIController {

    private final SignUpService signUpService;

    @PostMapping("/existUsername")
    public ResponseEntity<?> existUsernameCheck(@RequestBody MemberDTO.OnlyUsernameDTO onlyUsernameDTO){
        boolean checkExist = signUpService.checkExistUsername(onlyUsernameDTO.getUsername());
        if (checkExist) return new ResponseEntity<>("사용가능한 아이디입니다", HttpStatus.OK);
        else return new ResponseEntity<>(new MemberException(MemberExceptionType.ALREADY_EXIST_USERNAME),HttpStatus.OK);
    }

    @PostMapping("/samePassword")
    public ResponseEntity<?> samePasswordCheck(@RequestBody MemberDTO.SamePasswordCheckDTO samePasswordCheckDTO){
        boolean samePassword = signUpService.checkSamePassword(samePasswordCheckDTO.getPassword(), samePasswordCheckDTO.getRepassword());
        if (samePassword) return new ResponseEntity<>("비밀번호 일치",HttpStatus.OK);
        else return new ResponseEntity<>("비밀번호 불일치",HttpStatus.BAD_REQUEST);
    }

}
