package com.dto.way.chatting.web.controller;

import com.dto.way.chatting.converter.ChatRoomConverter;
import com.dto.way.chatting.domain.Chat;
import com.dto.way.chatting.global.jwt.JwtTokenProvider;
import com.dto.way.chatting.repository.ChatRepository;
import com.dto.way.chatting.repository.ChatRoomRepository;
import com.dto.way.chatting.service.ChatService;
import com.dto.way.chatting.web.dto.ChatDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatController {

    private final SimpMessageSendingOperations template;
    private final ChatRoomRepository repository;
    private final ChatRepository chatRepository;
    private final RabbitTemplate rabbitTemplate;
    private final ChatService chatService;
    private final JwtTokenProvider jwtTokenProvider;
    private final static String CHAT_EXCHANGE_NAME = "chat.exchange";
    private final static String CHAT_QUEUE_NAME = "chat.queue";

    // /pub/chat.message.{roomId} 로 요청하면 브로커를 통해 처리
    // /exchange/chat.exchange/room.{roomId} 를 구독한 클라이언트에 메시지가 전송된다.
    @MessageMapping("chat.enter.{chatRoomId}")
    public void enterUser(@Payload ChatDto chat, @DestinationVariable Long chatRoomId, @Payload Map<String, Object> headers) {
        String token = chat.getToken();
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        Authentication auth = jwtTokenProvider.getAuthentication(token);
        chatService.enterRoom(chatRoomId,auth);

        chat.setTime(String.valueOf(LocalDateTime.now()));
        chat.setMessage(chat.getSender() + " 님 입장!!");
        rabbitTemplate.convertAndSend(CHAT_EXCHANGE_NAME, "room." + chatRoomId, chat);
    }

    @MessageMapping("chat.message.{chatRoomId}")
    public void sendMessage(@Payload ChatDto chat, @DestinationVariable String chatRoomId) {
        chat.setTime(String.valueOf(LocalDateTime.now()));
        chat.setMessage(chat.getMessage());
        rabbitTemplate.convertAndSend(CHAT_EXCHANGE_NAME, "room." + chatRoomId, chat);

    }

    //기본적으로 chat.queue가 exchange에 바인딩 되어있기 때문에 모든 메시지 처리
    @RabbitListener(queues = CHAT_QUEUE_NAME)
    public void receive(ChatDto chatDTO) {

        log.info("received : " + chatDTO.getMessage());
        Chat chat = ChatRoomConverter.toChat(chatDTO);
        chatRepository.save(chat);
        System.out.println("received : " + chatDTO.getMessage());
    }


}