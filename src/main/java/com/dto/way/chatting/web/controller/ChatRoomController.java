package com.dto.way.chatting.web.controller;

import com.dto.way.chatting.converter.ChatRoomConverter;
import com.dto.way.chatting.domain.ChatRoom;
import com.dto.way.chatting.domain.ChatRoomMember;
import com.dto.way.chatting.global.response.ApiResponse;
import com.dto.way.chatting.global.response.code.status.SuccessStatus;
import com.dto.way.chatting.service.ChatRoomService;
import com.dto.way.chatting.service.ChatService;
import com.dto.way.chatting.web.dto.ChatDto;
import com.dto.way.chatting.web.dto.ChatRoomResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chatting-service/chatroom")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final ChatService chatService;

    @GetMapping()
    public ApiResponse<ChatRoomResponseDto.GetChatRoomListResultDto> getChatRoom() {
        List<ChatRoom> chatRoomList = chatRoomService.getChatRoomList();

        return ApiResponse.of(SuccessStatus.CHATROOM_LIST, ChatRoomConverter.toGetChatRoomListResultDto(chatRoomList));
    }

    // 채팅방 생성
    @PostMapping
    public ApiResponse<ChatRoomResponseDto.CreateChatRoomResultDto> createRoom(@RequestParam(name = "postId") Long postId, @RequestParam(name = "title") String roomName) {
        ChatRoom room = chatRoomService.createRoom(postId, roomName);
        return ApiResponse.of(SuccessStatus.CHATROOM_CREATED, ChatRoomConverter.toCreateChatRoomResultDto(room));
    }

    // 채팅방 나가기
    @DeleteMapping
    public ApiResponse<ChatRoomResponseDto.LeaveChatRoomResultDto> leaveRoom(@RequestParam(name = "roomId") Long roomId, Authentication auth) {
        return ApiResponse.of(SuccessStatus.CHATROOM_LEAVE, chatService.leaveRoom(roomId, auth));
    }

    // 채팅방 채팅내용 불러오기 (방 열기)
    @GetMapping("/chat")
    public ApiResponse<List<ChatDto>> getChatList(Long roomId, Authentication auth) {
        return ApiResponse.of(SuccessStatus.CHAT_LIST, chatService.getChatList(roomId, auth));
    }


    // 채팅에 참여한 유저 리스트 반환
    @GetMapping("/memberList")
    public ApiResponse<List<ChatRoomMember>> getMemberList(Long roomId) {

        return ApiResponse.of(SuccessStatus.CHATROOM_MEMBER_LIST, chatRoomService.getChatRoomMemberList(roomId));
    }
}
