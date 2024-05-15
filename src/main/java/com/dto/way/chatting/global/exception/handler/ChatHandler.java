package com.dto.way.chatting.global.exception.handler;

import com.dto.way.chatting.global.exception.GeneralException;
import com.dto.way.chatting.global.response.code.BaseErrorCode;

public class ChatHandler extends GeneralException {
    public ChatHandler(BaseErrorCode code) {
        super(code);
    }
}
