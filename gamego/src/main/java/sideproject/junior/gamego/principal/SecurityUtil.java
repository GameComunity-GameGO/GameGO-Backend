package sideproject.junior.gamego.principal;

import com.dama.model.dto.response.QRLoginResponseDto;
import com.dama.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class SecurityUtil {

    public String returnLoginMemberInfo(){
        UserDetails loginMember = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return loginMember.getUsername();
    }


    /*public Member returnMemberInfo(String username){
        return memberService.findByUsername(username);
    }*/
}
