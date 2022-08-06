package sideproject.junior.gamego.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sideproject.junior.gamego.repository.MemberRepository;

@Service
@Log4j2
@RequiredArgsConstructor
public class SignUpService {
    private MemberRepository memberRepository;

    public boolean checkExistUsername(String username) {
        System.out.println("username = " + username);
        if(memberRepository.findByUsername(username).isPresent()) return false;
        else return true;
    }

    public boolean checkSamePassword(String password, String repassword) {
        if (password.equals(repassword)) return true;
        else return false;
    }
}
