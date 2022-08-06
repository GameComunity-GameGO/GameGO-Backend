package sideproject.junior.gamego.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sideproject.junior.gamego.model.entity.Member;
import sideproject.junior.gamego.repository.MemberRepository;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class SignUpService {
    private final MemberRepository memberRepository;

    public boolean checkExistUsername(String username) {
        System.out.println("username = " + username);
        Optional<Member> findUsername = memberRepository.findByUsername(username);
        System.out.println("findUsername = " + findUsername.get().getUsername());
        if(findUsername.isEmpty()){
            return true;
        }else {
            return false;
        }
    }

    public boolean checkSamePassword(String password, String repassword) {
        if (password.equals(repassword)) return true;
        else return false;
    }
}
