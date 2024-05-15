package com.dto.way.chatting.service;

import com.dto.way.chatting.domain.ChatRoom;
import com.dto.way.chatting.domain.ChatRoomMember;
import com.dto.way.chatting.repository.ChatRoomRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    @Override
    public List<ChatRoom> getChatRoomList() {

        List<ChatRoom> chatRoomList = chatRoomRepository.findAll();
        return chatRoomList;
    }

    @Override
    @Transactional
    public ChatRoom createRoom(Long postId, String roomName) {

        ChatRoom chatRoom = ChatRoom.builder()
                .postId(postId)
                .roomName(roomName)
                .memberCount(1L)
                .build();

        return chatRoomRepository.save(chatRoom);
    }

    @Override
    public List<ChatRoomMember> getChatRoomMemberList(Long roomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(() -> new EntityNotFoundException("채팅방이 존재하지 않습니다."));
        List<ChatRoomMember> memberList = chatRoom.getChatRoomMemberList();
        return memberList;
    }

}
