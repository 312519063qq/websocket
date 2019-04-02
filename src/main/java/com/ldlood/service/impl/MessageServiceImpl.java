package com.ldlood.service.impl;

import com.ldlood.config.RabbitConf;
import com.ldlood.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.Date;

@Slf4j
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendMsg() {
        String msg ="hello"+new Date();
        rabbitTemplate.convertAndSend(RabbitConf.QUEUE_NAME,msg);
    }

    @Override
    public void sendSession(WebSocketSession session) {
        rabbitTemplate.convertAndSend(RabbitConf.QUEUE_NAME,session);
    }
}
