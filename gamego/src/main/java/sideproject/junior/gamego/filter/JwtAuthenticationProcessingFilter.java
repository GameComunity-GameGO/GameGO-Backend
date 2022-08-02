package sideproject.junior.gamego.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import sideproject.junior.gamego.model.entity.Member;
import sideproject.junior.gamego.repository.MemberRepository;
import sideproject.junior.gamego.service.jwt.JwtService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Log4j2
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    private GrantedAuthoritiesMapper authoritiesMapper =new NullAuthoritiesMapper();

    private final String NO_CHECK_URL="/api/v1/login";

    private final String WHYCSS="/lib/css/normalize.css";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().equals(NO_CHECK_URL)||request.getRequestURI().equals(WHYCSS)){
            filterChain.doFilter(request,response);
            return;
        }else {
            System.out.println("request = " + request.getRequestURI());
            System.out.println("request.header = " + request.getHeader("Authorization"));
            System.out.println("request.getHeader(\"Authorization-refresh\") = " + request.getHeader("Authorization-refresh"));
        }

        String refreshToken = jwtService
                .extractRefreshToken(request)
                .filter(jwtService::isTokenValid)
                .orElse(null);
        System.out.println("refreshToken = " + refreshToken);
        if (refreshToken!=null) {
            checkRefreshTokenAndReIssueAccessToken(response, refreshToken);
            return;
        }
        checkAccessTokenAndAuthentication(request, response, filterChain);
    }
    private void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("jwtService = " + jwtService.extractAccessToken(request).filter(jwtService::isTokenValid));
        jwtService.extractAccessToken(request).filter(jwtService::isTokenValid).ifPresent(
                accessToken -> jwtService.extractUsername(accessToken).ifPresent(

                        username -> memberRepository.findByUsername(username).ifPresent(

                                member -> saveAuthentication(member)
                        )
                )
        );
        log.info("TokenValid들어옴");
        filterChain.doFilter(request,response);
    }

    private void saveAuthentication(Member member) {
        UserDetails user = User.builder()
                .username(member.getUsername())
                .password(member.getPassword())
                .roles(member.getRole().name())
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null,authoritiesMapper.mapAuthorities(user.getAuthorities()));


        SecurityContext context = SecurityContextHolder.createEmptyContext();//5
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    private void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {
        memberRepository.findByRefreshToken(refreshToken).ifPresent(
                member -> jwtService.sendAccessToken(response, jwtService.createAccessToken(member.getUsername()))
        );


    }

}
