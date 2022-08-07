package sideproject.junior.gamego.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sideproject.junior.gamego.model.dto.MemberDTO;
import sideproject.junior.gamego.model.entity.Member;
import sideproject.junior.gamego.principal.SecurityUtil;
import sideproject.junior.gamego.service.MemberService;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class LogInOutAPIController {

    private final SecurityUtil securityUtil;

    private final MemberService memberService;

    @PostMapping("/memberState")
    public MemberDTO.MemberResponseDTO.MemberStateResponseDTO memberState(){
        String username = securityUtil.returnLoginMemberInfo();
        Member ReturnMember = memberService.MemberStateApi(username);
        MemberDTO.MemberResponseDTO.MemberStateResponseDTO memberStateResponseDTO=new MemberDTO.MemberResponseDTO.MemberStateResponseDTO();
        MemberDTO.MemberResponseDTO.MemberStateResponseDTO returnDto = setMemberStateResponse(ReturnMember, memberStateResponseDTO);
        return  returnDto;
    }
    /*@PostMapping("/logout")
    public ResponseEntity<?> logoutForm(){

    }*/

    public MemberDTO.MemberResponseDTO.MemberStateResponseDTO setMemberStateResponse(Member member,MemberDTO.MemberResponseDTO.MemberStateResponseDTO memberStateResponseDTO){
        memberStateResponseDTO.setId(member.getId());
        memberStateResponseDTO.setAuthority(member.getRole().toString());
        memberStateResponseDTO.setNickname(member.getNickname());
        memberStateResponseDTO.setRefreshToken(member.getRefreshToken());
        memberStateResponseDTO.setUsername(member.getUsername());

        return memberStateResponseDTO;
    }
}

