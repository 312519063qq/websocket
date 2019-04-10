package com.ldlood.Handler;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ldlood.MyWebSocketUtils;
import com.ldlood.VO.MessageType;
import com.ldlood.service.MessageService;
import com.ldlood.service.UserService;
import com.ldlood.service.WebSocketService;
import com.ldlood.VO.MessageVO;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class MyWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private UserService userService;

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    private MessageVO messageVO = new MessageVO();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("------------------------afterConnectionEstablished--------------------------------");
        super.afterConnectionEstablished(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        System.out.println("-------------------------handleMessage-------------------------------" + message.getPayload());
        super.handleMessage(session, message);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        //TODO 把session和二维码对应的uuid 存在本地   把orderId和uuid存储在redis里 发送给前端生成二维码
        List<MessageVO> messageVOS = com.alibaba.fastjson.JSONObject.parseArray(message.getPayload(), MessageVO.class);
        MessageVO messageVO = messageVOS.get(0);
        if(messageVO.getType().equals(MessageType.CreateQRcode.name())){
            log.info("生成订单 产生二维码");
            String s = UUID.randomUUID().toString();
            MyWebSocketUtils.onlinePerson(session,s );
            String orderId="123";
            redisTemplate.opsForValue().set(orderId,s);
        }
        messageVO.setMessage("www.baidu.com");
        webSocketService.sendMessage(session,JSON.toJSONString(messageVO));
    }

    @Override
    protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
        System.out.println("---------------------------handlePongMessage-----------------------------" + message.getPayload());
        super.handlePongMessage(session, message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("---------------------------handleTransportError-----------------------------");
        super.handleTransportError(session, exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("---------------------------afterConnectionClosed-----------------------------");
        MyWebSocketUtils.offlinePerson(session);
        super.afterConnectionClosed(session, status);
    }

    @Override
    public boolean supportsPartialMessages() {
        System.out.println("----------------------------supportsPartialMessages----------------------------");
        return super.supportsPartialMessages();
    }

//    public static void sendmessage(WebSocketSession socketSession, String message) {
//        try {
//            socketSession.sendMessage(new TextMessage(message));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
