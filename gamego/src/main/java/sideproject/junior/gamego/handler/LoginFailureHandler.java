package sideproject.junior.gamego.handler;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("JWT 토큰발급실패!");
        log.info("로그인에 실패하였습니다");
        System.out.println("request.getReader() = " + request.getReader());
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("Login Fail");
    }
}
