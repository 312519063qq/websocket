package com.ldlood.service;

import org.springframework.web.socket.WebSocketSession;

public interface MessageService {
    void sendMsg();
    void sendSession(WebSocketSession session);
}
