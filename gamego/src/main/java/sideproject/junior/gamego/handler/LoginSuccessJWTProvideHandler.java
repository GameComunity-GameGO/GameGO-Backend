package sideproject.junior.gamego.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import sideproject.junior.gamego.repository.MemberRepository;
import sideproject.junior.gamego.service.jwt.JwtService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
public class LoginSuccessJWTProvideHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String username = extractUsername(authentication);
        String accessToken = jwtService.createAccessToken(username);
        String refreshToken = jwtService.createRefreshToken();

        jwtService.sendAccessAndRefreshToken(response,accessToken,refreshToken);
//        memberRepository.findByUsername(username).ifPresent(
//                m->m.changeRefreshToken(refreshToken)
//        );
        /*Member member = memberRepository.findByUsername(username).get();
        member.changeRefreshToken(refreshToken);*/
        jwtService.updateRefreshToken(username,refreshToken);


        log.info("로그인에 성공하고 JWT 토큰을 발급합니다 현재 로그인 User ={} ",username);
        log.info("로그인에 성공하고 JWT 토큰을 발급합니다 현재 AccessToken ={} ",accessToken);
        log.info("로그인에 성공하고 JWT 토큰을 발급합니다 현재 RefreshToken ={} ",refreshToken);
    }


    private String extractUsername(Authentication authentication){
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        return principal.getUsername();
    }
}
