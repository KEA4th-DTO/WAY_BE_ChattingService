package com.dto.way.chatting.domain;


import com.dto.way.chatting.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId; // 채팅방 아이디
    private String roomName; // 채팅방 이름
    private Long memberCount; // 채팅방 인원수
    private Long postId;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<ChatRoomMember> chatRoomMemberList = new ArrayList<>();

    public void upMemberCount() {
        this.memberCount++;
    }

    public void downMemberCount() {
        this.memberCount--;
    }

//    public void addMember(String memberEmail) {
//        this.memberList.add(memberEmail);
//    }
//
//    public void removeMember(String memberEmail) {
//        this.memberList.remove(memberEmail);
//    }

}