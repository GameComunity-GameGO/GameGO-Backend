 package sideproject.junior.gamego.config;

 import lombok.extern.log4j.Log4j2;
 import org.springframework.context.annotation.Configuration;
 import org.springframework.core.Ordered;
 import org.springframework.core.annotation.Order;
 import org.springframework.messaging.simp.SimpMessageType;
 import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
 import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

 @Configuration
 @Order(Ordered.HIGHEST_PRECEDENCE + 99)
 @Log4j2
 public class SocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

     @Override
     protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {

         log.info("SocketSecurityConfig.configureInbound 호출");

         messages
                 .simpDestMatchers("/ws/**/**", "/app/**/**").permitAll();
      
         messages
          .simpTypeMatchers(SimpMessageType.CONNECT,
                        SimpMessageType.DISCONNECT, SimpMessageType.OTHER).permitAll()
                .anyMessage().authenticated();
     }

     @Override
     protected boolean sameOriginDisabled() {
         return true;
     }
 }
