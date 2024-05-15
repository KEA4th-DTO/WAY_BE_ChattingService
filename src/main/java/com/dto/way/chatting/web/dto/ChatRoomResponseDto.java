package com.dto.way.chatting.web.dto;

import com.dto.way.chatting.domain.ChatRoom;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class ChatRoomResponseDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateChatRoomResultDto {
        private Long roomId;
        private String roomName;
        private LocalDateTime createdAt;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LeaveChatRoomResultDto {
        private Long roomId;
        private String roomName;
    }


    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetChatRoomResultDto{
        private Long roomId;
        private String roomName;
        private Long memberCount;
        private LocalDateTime createdAt;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetChatRoomListResultDto{
        private List<GetChatRoomResultDto> chatRoomResultDtoList;
    }

}
