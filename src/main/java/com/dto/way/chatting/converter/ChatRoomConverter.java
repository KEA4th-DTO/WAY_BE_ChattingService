package com.dto.way.chatting.converter;

import com.dto.way.chatting.domain.ChatRoom;
import com.dto.way.chatting.web.controller.ChatRoomController;
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

}
