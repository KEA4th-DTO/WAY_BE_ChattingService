package com.dto.way.chatting.global.handler;

import com.dto.way.chatting.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {
    private final JwtTokenProvider jwtTokenProvider;

    // websocket을 통해 들어온 요청이 처리되기 전 실행된다.
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        System.out.println("message:" + message);
        System.out.println("헤더 : " + message.getHeaders());
        System.out.println("토큰" + accessor.getNativeHeader("Authorization"));
        // websocket 연결시 헤더의 jwt token 검증
        if (StompCommand.CONNECT == accessor.getCommand()) {
            String token = accessor.getFirstNativeHeader("Authorization");
            System.out.println("+++++++++++++++++");
            System.out.println("토큰" + accessor.getNativeHeader("Authorization"));

            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
                if (!jwtTokenProvider.validateToken(token).isValid()) {
                    throw new IllegalArgumentException("Invalid JWT token");
                }
            } else {
                throw new IllegalArgumentException("Authorization header is missing or invalid");
            }
        }
        return message;
    }
}

