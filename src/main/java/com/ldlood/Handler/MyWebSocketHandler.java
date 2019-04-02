package com.ldlood.Handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ldlood.MyWebSocketUtils;
import com.ldlood.service.MessageService;
import com.ldlood.service.UserService;
import com.ldlood.service.WebSocketService;
import com.ldlood.VO.MessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class MyWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private UserService userService;

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private MessageService messageService;


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
        int  aa = userService.getInt();
        System.out.println("userService.getInt() ----------------方法调用==="+aa);
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
//        messageService.sendSession(session);
        webSocketService.sendMessage(session,Json);
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
