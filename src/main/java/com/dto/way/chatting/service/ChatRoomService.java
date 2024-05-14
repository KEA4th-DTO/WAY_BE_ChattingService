package com.dto.way.chatting.service;


import com.dto.way.chatting.domain.ChatRoom;

import java.util.List;

public interface ChatRoomService {

    List<ChatRoom> getChatRoomList();

    ChatRoom createRoom(Long postId,String roomName);

    List<String> getMemberList(String roomId);

    void addMember(String roomId, String memberEmail);

    void deleteMember(String roomId, String memberEmail);
}
