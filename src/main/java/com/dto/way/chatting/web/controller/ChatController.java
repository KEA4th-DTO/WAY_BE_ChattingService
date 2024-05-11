package com.dto.way.chatting.web.controller;

import com.dto.way.chatting.repository.ChatRepository;
import com.dto.way.chatting.web.dto.ChatDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatController {

    private final SimpMessageSendingOperations template;
    private final ChatRepository repository;
    private final RabbitTemplate rabbitTemplate;
    private final static String CHAT_EXCHANGE_NAME = "chat.exchange";
    private final static String CHAT_QUEUE_NAME = "chat.queue";


//    @MessageMapping("/enterUser")
//    public void enterUser(@Payload ChatDto chat, SimpMessageHeaderAccessor headerAccessor) {
//        repository.plusUserCnt(chat.getRoomId());
//        String userUUID = repository.addUser(chat.getRoomId(), chat.getSender());
//
//        headerAccessor.getSessionAttributes().put("userUUID", userUUID);
//        headerAccessor.getSessionAttributes().put("roomId", chat.getRoomId());
//
//        chat.setMessage(chat.getSender() + " 님 입장!!");
//        template.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);
//    }
//
//    @MessageMapping("/sendMessage")
//    public void sendMessage(@Payload ChatDto chat) {
//        log.info("CHAT {}", chat);
//        chat.setMessage(chat.getMessage());
//        template.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);
//
//    }



    // /pub/chat.message.{roomId} 로 요청하면 브로커를 통해 처리
    // /exchange/chat.exchange/room.{roomId} 를 구독한 클라이언트에 메시지가 전송된다.
    @MessageMapping("chat.enter.{chatRoomId}")
    public void enterUser(@Payload ChatDto chat, @DestinationVariable String chatRoomId) {
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
    public void receive(ChatDto chatDTO){
        System.out.println("received : " + chatDTO.getMessage());
    }


}