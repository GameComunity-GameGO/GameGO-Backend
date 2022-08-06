package sideproject.junior.gamego.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sideproject.junior.gamego.model.dto.MemberDTO;
import sideproject.junior.gamego.model.entity.Member;
import sideproject.junior.gamego.service.MemberService;

import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberAPIController {

    private final MemberService memberService;

    @GetMapping("/members")
    public ResponseEntity<List<Member>> memberList(){
        List<Member> members = memberService.MemberListApi();
        return new ResponseEntity<>(members,HttpStatus.OK);
    }

    @PostMapping("/member")
    public ResponseEntity<?> signup(@RequestBody MemberDTO.SignUpDTO signUpDTO){
        log.info("회원가입");
        log.info("아이디 ={}",signUpDTO.getUsername());
        log.info("signUpDto ={}",signUpDTO);
        ResponseEntity<?> signUpApiState = memberService.SignUpApi(signUpDTO);
        return signUpApiState;
    }

    @PutMapping("/member")
    public ResponseEntity<String> changeMemberState(MemberDTO.ChangeStateDTO changeStateDTO){
        String changeMemberAPiState = memberService.MemberStateChangeApi(changeStateDTO);
        return new ResponseEntity<>(changeMemberAPiState,HttpStatus.OK);
    }

    @DeleteMapping("/member")
    public ResponseEntity<String> deleteMember(MemberDTO.DeleteDTO deleteDTO){
        String deleteApiState = memberService.MemberDeleteApi(deleteDTO);
        return new ResponseEntity<>(deleteApiState,HttpStatus.OK);
    }

}
