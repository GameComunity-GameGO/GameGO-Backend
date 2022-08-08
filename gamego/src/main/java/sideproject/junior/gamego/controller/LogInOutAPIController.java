package sideproject.junior.gamego.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sideproject.junior.gamego.model.dto.MemberDTO;
import sideproject.junior.gamego.model.entity.Member;
import sideproject.junior.gamego.principal.SecurityUtil;
import sideproject.junior.gamego.service.MemberService;
import sideproject.junior.gamego.service.jwt.JwtService;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class LogInOutAPIController {

    private final SecurityUtil securityUtil;

    private final MemberService memberService;

    private final JwtService jwtService;

    @PostMapping("/memberState")
    public MemberDTO.MemberResponseDTO.MemberStateResponseDTO memberState(){
        String username = securityUtil.returnLoginMemberInfo();
        Member ReturnMember = memberService.MemberStateApi(username);
        MemberDTO.MemberResponseDTO.MemberStateResponseDTO memberStateResponseDTO=new MemberDTO.MemberResponseDTO.MemberStateResponseDTO();
        MemberDTO.MemberResponseDTO.MemberStateResponseDTO returnDto = setMemberStateResponse(ReturnMember, memberStateResponseDTO);
        return  returnDto;
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logoutForm(MemberDTO.OnlyUsernameDTO onlyUsernameDTO){
//        Member findMember = memberService.MemberStateApi(onlyUsernameDTO.getUsername());
        if (onlyUsernameDTO.getUsername()!=null){
            jwtService.destroyRefreshToken(onlyUsernameDTO.getUsername());
            jwtService.createAccessToken(onlyUsernameDTO.getUsername());
            return new ResponseEntity<>(true, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(false,HttpStatus.OK);
        }

    }

    public MemberDTO.MemberResponseDTO.MemberStateResponseDTO setMemberStateResponse(Member member,MemberDTO.MemberResponseDTO.MemberStateResponseDTO memberStateResponseDTO){
        memberStateResponseDTO.setId(member.getId());
        memberStateResponseDTO.setAuthority(member.getRole().toString());
        memberStateResponseDTO.setNickname(member.getNickname());
        memberStateResponseDTO.setRefreshToken(member.getRefreshToken());
        memberStateResponseDTO.setUsername(member.getUsername());

        return memberStateResponseDTO;
    }
}

