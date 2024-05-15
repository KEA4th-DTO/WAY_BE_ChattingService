package com.dto.way.chatting.web.dto;


import com.dto.way.chatting.domain.Enum.MessageType;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatDto {

    private MessageType type; // 메시지 타입
    private String roomId; // 방 번호
    private String sender; // 채팅을 보낸 사람
    private String message; // 메시지
    private String time; // 채팅 발송

}