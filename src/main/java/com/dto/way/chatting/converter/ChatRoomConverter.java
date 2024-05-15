package com.dto.way.chatting.converter;

import com.dto.way.chatting.domain.Chat;
import com.dto.way.chatting.domain.ChatRoom;
import com.dto.way.chatting.web.controller.ChatRoomController;
import com.dto.way.chatting.web.dto.ChatDto;
import com.dto.way.chatting.web.dto.ChatRoomResponseDto;

import java.util.List;
import java.util.stream.Collectors;

public class ChatRoomConverter {

    public static ChatRoomResponseDto.CreateChatRoomResultDto toCreateChatRoomResultDto(ChatRoom chatRoom) {
        return ChatRoomResponseDto.CreateChatRoomResultDto.builder()
                .roomId(chatRoom.getRoomId())
                .roomName(chatRoom.getRoomName())
                .createdAt(chatRoom.getCreatedAt())
                .build();
    }

    public static ChatRoomResponseDto.GetChatRoomResultDto toGetChatRoomResultDto(ChatRoom chatRoom) {
        return ChatRoomResponseDto.GetChatRoomResultDto.builder()
                .roomId(chatRoom.getRoomId())
                .roomName(chatRoom.getRoomName())
                .memberCount(chatRoom.getMemberCount())
                .createdAt(chatRoom.getCreatedAt())
                .build();
    }

    public static ChatRoomResponseDto.GetChatRoomListResultDto toGetChatRoomListResultDto(List<ChatRoom> chatRoomList) {
        List<ChatRoomResponseDto.GetChatRoomResultDto> chatRoomResultDtoList = chatRoomList.stream()
                .map(ChatRoomConverter::toGetChatRoomResultDto).collect(Collectors.toList());

        return ChatRoomResponseDto.GetChatRoomListResultDto.builder()
                .chatRoomResultDtoList(chatRoomResultDtoList)
                .build();
    }


    public static ChatRoomResponseDto.LeaveChatRoomResultDto toLeaveChatRoomResultDto(ChatRoom chatRoom) {
        return ChatRoomResponseDto.LeaveChatRoomResultDto.builder()
                .roomId(chatRoom.getRoomId())
                .roomName(chatRoom.getRoomName())
                .build();
    }

    public static ChatDto toChatDto(Chat chat) {
        return ChatDto.builder()
                .roomId(chat.getRoomId())
                .message(chat.getMessage())
                .sender(chat.getSender())
                .time(chat.getTime())
                .type(chat.getType())
                .build();
    }

    public static Chat toChat(ChatDto chatDto) {
        return Chat.builder()
                .roomId(chatDto.getRoomId())
                .message(chatDto.getMessage())
                .sender(chatDto.getSender())
                .time(chatDto.getTime())
                .type(chatDto.getType())
                .build();
    }

}
