package com.ldlood.listener;

import com.ldlood.MyWebSocketUtils;
import com.ldlood.config.RabbitConf;
import com.ldlood.service.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
@Slf4j
public class MessageListener {

    @Autowired
    private WebSocketService webSocketService;

    @RabbitListener(queues = {RabbitConf.QUEUE_NAME}, containerFactory="rabbitListenerContainerFactory")
    public void messageHandler(String msg){
        log.info("msg:{}",msg);
        WebSocketSession session = MyWebSocketUtils.getSession(msg);
        if(session!=null){
            webSocketService.sendMessage(session,"支付成功");
        }
    }
}
