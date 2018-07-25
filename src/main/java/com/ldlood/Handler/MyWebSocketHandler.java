package com.ldlood.Handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ldlood.MyWebSocketUtils;
import com.ldlood.VO.MessageVO;
import com.ldlood.WebSocketUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Component
public class MyWebSocketHandler extends TextWebSocketHandler {

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
        MyWebSocketUtils.onlinePerson(session,message.getPayload());
        System.out.println("--------------------------handleTextMessage------------------------------" + message.getPayload());
        String[] flag = message.getPayload().split(",");
        if(flag[0].equals("QRCode")){
            System.out.println("生成二维码");
            messageVO.setType(3);
            messageVO.setMessage("生成二维码 uuid=="+flag[1]);
        }
        ObjectMapper mapper = new ObjectMapper();
        String Json = "";
        try {
            Json = mapper.writeValueAsString(messageVO);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.sendmessage(session,Json);
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

    private void sendmessage(WebSocketSession socketSession, String message) {
        try {
            socketSession.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
