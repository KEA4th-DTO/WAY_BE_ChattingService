package com.dto.way.chatting.repository;

import com.dto.way.chatting.domain.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatRepository extends MongoRepository<Chat,String> {
    public List<Chat> findAllByRoomIdAndTimeAfter(Long roomId, LocalDateTime time);
}
