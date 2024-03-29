// package sideproject.junior.gamego.config;

// import lombok.RequiredArgsConstructor;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.messaging.simp.config.ChannelRegistration;
// import org.springframework.messaging.simp.config.MessageBrokerRegistry;
// import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
// import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
// import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
// import sideproject.junior.gamego.handler.StompHandler;

// @Configuration
// @EnableWebSocketMessageBroker
// @RequiredArgsConstructor
// public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

//     private final StompHandler stompHandler;

//     @Override
//     public void registerStompEndpoints(StompEndpointRegistry registry) {
//         registry.addEndpoint("/ws/alarm", "/ws/chat")
//                 /*.setAllowedOrigins("http://localhost:3000")*/
//                 .setAllowedOriginPatterns("*")
//                 .withSockJS();
//     }

//     @Override
//     public void configureMessageBroker(MessageBrokerRegistry registry) {
//         registry.enableSimpleBroker("/ws/topic");
//         registry.setApplicationDestinationPrefixes("/app");
//     }

//     @Override
//     public void configureClientInboundChannel(ChannelRegistration registration) {
//         registration.interceptors(stompHandler);
//     }
// }
