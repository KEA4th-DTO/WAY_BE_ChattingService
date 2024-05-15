package com.dto.way.chatting.service;

import com.dto.way.chatting.converter.ChatRoomConverter;
import com.dto.way.chatting.domain.Chat;
import com.dto.way.chatting.domain.ChatRoom;
import com.dto.way.chatting.domain.ChatRoomMember;
import com.dto.way.chatting.domain.Enum.Status;
import com.dto.way.chatting.global.exception.handler.ChatHandler;
import com.dto.way.chatting.global.response.code.status.ErrorStatus;
import com.dto.way.chatting.repository.ChatRepository;
import com.dto.way.chatting.repository.ChatRoomMemberRepository;
import com.dto.way.chatting.repository.ChatRoomRepository;
import com.dto.way.chatting.web.dto.ChatDto;
import com.dto.way.chatting.web.dto.ChatRoomResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;

    @Override
    @Transactional
    public void enterRoom(Long roomId, Authentication auth) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(() -> new EntityNotFoundException("채팅방을 찾을 수 없습니다. "));
        String memberEmail = auth.getName();
        if (chatRoomMemberRepository.existsByChatRoomAndMemberEmail(chatRoom, memberEmail)) {
            throw new ChatHandler(ErrorStatus.CHATROOM_ALREADY_ENTERED);
        } else {
            chatRoom.upMemberCount();
            ChatRoomMember chatRoomMember = new ChatRoomMember(memberEmail, Status.UNCHECKED, chatRoom, LocalDateTime.now());

            chatRoomMemberRepository.save(chatRoomMember);
        }
    }

    @Override
    @Transactional
    public ChatRoomResponseDto.LeaveChatRoomResultDto leaveRoom(Long roomId, Authentication auth) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(() -> new EntityNotFoundException("채팅방을 찾을 수 없습니다. "));
        String memberEmail = auth.getName();

        ChatRoomMember chatRoomMember = chatRoomMemberRepository.findByChatRoomAndMemberEmail(chatRoom, memberEmail);
        chatRoom.downMemberCount();

        ChatRoomResponseDto.LeaveChatRoomResultDto leaveChatRoomResultDto = ChatRoomConverter.toLeaveChatRoomResultDto(chatRoom);

        chatRoomMemberRepository.delete(chatRoomMember);

        return leaveChatRoomResultDto;
    }

    @Override
    public List<ChatDto> getChatList(Long roomId, Authentication auth) {

        String memberEmail = auth.getName();
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(() -> new EntityNotFoundException("채팅방을 찾을 수 없습니다. "));
        ChatRoomMember chatRoomMember = chatRoomMemberRepository.findByChatRoomAndMemberEmail(chatRoom, memberEmail);

        List<Chat> chatList = chatRepository.findAllByRoomIdAndTimeAfter(roomId, chatRoomMember.getEnteredTime());
        List<ChatDto> chatDtoList = chatList.stream().map(ChatRoomConverter::toChatDto).collect(Collectors.toList());
        return chatDtoList;
    }
}
