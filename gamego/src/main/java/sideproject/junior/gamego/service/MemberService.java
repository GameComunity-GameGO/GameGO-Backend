package sideproject.junior.gamego.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sideproject.junior.gamego.exception.member.MemberException;
import sideproject.junior.gamego.exception.member.MemberExceptionType;
import sideproject.junior.gamego.model.dto.MemberDTO;
import sideproject.junior.gamego.model.entity.Member;
import sideproject.junior.gamego.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public String SignUpApi(MemberDTO.SignUpDTO signUpDTO) throws MemberException{
        if (signUpDTO.getUsername() == null || signUpDTO.getPassword() == null){
            throw new MemberException(MemberExceptionType.NULL_OF_USERNAME_OR_PASSWORD);
        }else if (!signUpDTO.getPassword().matches("(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}")){
            throw new MemberException(MemberExceptionType.WRONG_PASSWORD);
        }else if(memberRepository.findByUsername(signUpDTO.getUsername()).isPresent()) {
            throw new MemberException(MemberExceptionType.ALREADY_EXIST_USERNAME);
        } else{
            Member member = signUpDTO.toEntity();
            member.addMemberAuthority();
            member.encodeToPassword(passwordEncoder);
            memberRepository.save(member);
            return "회원가입이 정상적으로 동작하였습니다.";
        }
    }

    public List<Member> MemberListApi(){
        return memberRepository.findAll();
    }


    @Transactional
    public String MemberStateChangeApi(MemberDTO.ChangeStateDTO changeStateDTO){
        Optional<Member> findMember = memberRepository.findByUsername(changeStateDTO.getUsername());
        boolean changeState = findMember.get().ChangeMemberState(changeStateDTO);
        if (changeState){
            return "회원 정보 변경이 완료되었습니다";
        }else {
            return "회원 정보 변경중 문제가 발생하였습니다";
        }
    }

    @Transactional
    public String MemberDeleteApi(MemberDTO.DeleteDTO deleteDTO){
        memberRepository.delete(memberRepository.findById(deleteDTO.getId()).get());
        return "회원이 성공적으로 삭제되었습니다";
    }


}
