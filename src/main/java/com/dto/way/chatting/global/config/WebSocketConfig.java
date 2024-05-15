package com.dto.way.chatting.global.config;

import com.dto.way.chatting.global.handler.StompHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Slf4j
@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${spring.rabbitmq.username}")
    private String rabbitUser;

    @Value("${spring.rabbitmq.password}")
    private String rabbitPwd;

    @Value("${spring.rabbitmq.host}")
    private String rabbitHost;

    @Value("${spring.rabbitmq.port}")
    private Integer rabbitPort;

    @Value("${spring.rabbitmq.virtual-host}")
    private String rabbitVHost;

    private final StompHandler stompHandler;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        //메시지 구독 url
        config.enableStompBrokerRelay("/exchange")
                .setClientLogin(rabbitUser)
                .setClientPasscode(rabbitPwd)
                .setSystemLogin(rabbitUser)
                .setSystemPasscode(rabbitPwd)
                .setRelayHost(rabbitHost)
                .setRelayPort(61613)    //stomp port
                .setVirtualHost(rabbitVHost);

        //메시지 발행 url
        config.setPathMatcher(new AntPathMatcher("."));
        config.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*");
    }

//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.interceptors(stompHandler);
//    }
}
