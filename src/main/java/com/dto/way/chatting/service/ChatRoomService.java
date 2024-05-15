package com.dto.way.chatting.service;


import com.dto.way.chatting.domain.ChatRoom;
import com.dto.way.chatting.domain.ChatRoomMember;

import java.util.List;

public interface ChatRoomService {

    List<ChatRoom> getChatRoomList();

    ChatRoom createRoom(Long postId,String roomName);

    List<ChatRoomMember> getChatRoomMemberList(Long roomId);
}
