package sideproject.junior.gamego.handler;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import sideproject.junior.gamego.principal.SecurityUtil;
import sideproject.junior.gamego.service.jwt.JwtServiceImpl;

import java.util.Date;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Log4j2
public class StompHandler implements ChannelInterceptor {

    private final JwtServiceImpl jwtService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        log.info("StompHandler.presend 호출");

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        // 헤더 토큰 얻기
        String jwt = String.valueOf(accessor.getNativeHeader("Authorization"));
        StompCommand command = accessor.getCommand();

        jwt = jwt.substring(8, jwt.length()-1);

        log.info("preSend.sessionID = " + accessor.getSessionId());
        log.info("JWT 토큰 : " + jwt);

        if(command.equals(StompCommand.CONNECT)){
            log.info("================CONNECT================");
            jwtService.isTokenValid(jwt);
            String username = jwtService.extractUsername(jwt).get();
            log.info("connect.username = " + username);
            accessor.addNativeHeader("Username", username);
        }
        return message;
    }

    @Override
    public void postSend(Message message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String sessionId = accessor.getSessionId();
        switch (accessor.getCommand()) {
            case CONNECT:
                log.info("CONNECT");
                log.info("postSend.sessionId: {}",sessionId);
                log.info("postSend.channel:{}",channel);
                // 유저가 Websocket으로 connect()를 한 뒤 호출됨

                break;
            case DISCONNECT:
                log.info("DISCONNECT");
                /*log.info("sessionId: {}",sessionId);
                log.info("channel:{}",channel);*/
                // 유저가 Websocket으로 disconnect() 를 한 뒤 호출됨 or 세션이 끊어졌을 때 발생함(페이지 이동~ 브라우저 닫기 등)
                break;
            default:
                break;
        }

    }

    /*@Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        System.out.println("message:" + message);
        System.out.println("헤더 : " + message.getHeaders());
        System.out.println("토큰" + accessor.getNativeHeader("Authorization"));
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            validateToken(Objects.requireNonNull(accessor.getFirstNativeHeader("Authorization")).substring(7));
        }
        return message;
    }

    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }*/

    /*public Message<?> verifySend(String token*//*, Long roomId*//*, Message message){

        //토큰 검증
        *//*User user = *//* verifyJwt(token);

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
        headerAccessor.setHeader("userId", user.getId());

        //참가한 경우 성공 응답
        return MessageBuilder.createMessage(message.getPayload(), headerAccessor.getMessageHeaders());
    }

    public void verifyJwt(String accessToken){
        if(accessToken == null || accessToken.equals("null")){
            throw new MalformedJwtException("JWT");
        }
        //Bearer 문자 자르기
        String token = accessToken.substring(BEARER_PREFIX.length());

        // 토큰 인증
        Claims claims;
        try{
            claims = jwtService.verifyToken(token, jwtProperties.getAccessTokenSigningKey());
        }catch (JwtExpiredTokenException e){ // 만료 예외
            throw new MessageDeliveryException("JWT");
        }catch (MalformedJwtException e){ //변조 예외
            throw new MalformedJwtException("JWT");
        }catch (JwtModulatedTokenException e){ //변조 예외
            throw new JwtModulatedTokenException("JWT");
        }

        *//*return jwtUserConvertor.apply(claims);*//*

    }*/
}
