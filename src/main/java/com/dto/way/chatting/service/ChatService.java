package com.dto.way.chatting.service;

import com.dto.way.chatting.web.dto.ChatDto;
import com.dto.way.chatting.web.dto.ChatRoomResponseDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ChatService {
    void enterRoom(Long roomId, Authentication auth);

    ChatRoomResponseDto.LeaveChatRoomResultDto leaveRoom(Long roomId, Authentication auth);

    List<ChatDto> getChatList(Long roomId, Authentication auth);

}
