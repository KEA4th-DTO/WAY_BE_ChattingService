package com.dto.way.chatting.web.controller;

import com.dto.way.chatting.domain.ChatRoom;
import com.dto.way.chatting.global.response.ApiResponse;
import com.dto.way.chatting.global.response.code.status.SuccessStatus;
import com.dto.way.chatting.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chatroom")
public class ChatRoomController {
    private final ChatRepository chatRepository;

    // 채팅 리스트 화면
    @GetMapping("/")
    public ApiResponse<List<ChatRoom>> goChatRoom(){
        List<ChatRoom> chatRooms = chatRepository.findAllRoom();
        return ApiResponse.of(SuccessStatus.CHATROOM_ENTERED, chatRooms);
    }

    // 채팅방 생성
    @PostMapping("/room")
    public ApiResponse<String>createRoom(@RequestParam String name) {
        ChatRoom room = chatRepository.createChatRoom(name);
        return ApiResponse.of(SuccessStatus.CHATROOM_CREATED, room.getRoomId());
    }

    // 채팅에 참여한 유저 리스트 반환
    @GetMapping("/userlist")
    public ArrayList<String> userList(String roomId) {

        return chatRepository.getUserList(roomId);
    }
}
