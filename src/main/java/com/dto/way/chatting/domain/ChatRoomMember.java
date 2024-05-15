package com.dto.way.chatting.domain;


import com.dto.way.chatting.domain.Enum.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomMember {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long chatRoomMemberId; // 채팅방 멤버 아이디

    @Id
    private String memberEmail;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @ManyToOne
    private ChatRoom chatRoom;

    private LocalDateTime enteredTime;
}
