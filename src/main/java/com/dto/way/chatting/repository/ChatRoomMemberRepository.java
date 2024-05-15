package com.dto.way.chatting.repository;

import com.dto.way.chatting.domain.ChatRoom;
import com.dto.way.chatting.domain.ChatRoomMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, String> {

    boolean existsByChatRoomAndMemberEmail(ChatRoom chatRoom, String memberEmail);

    ChatRoomMember findByChatRoomAndMemberEmail(ChatRoom chatRoom, String memberEmail);
}
