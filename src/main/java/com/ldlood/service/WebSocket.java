package com.ldlood.service;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by Administrator on 2017/8/19.
 */
//@Component
//@ServerEndpoint("/webSocket")
@Slf4j
public class WebSocket {

//    private Session session;
//
//    private static CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<>();
//
//    private MessageVO messageVO = new MessageVO();
//
//    @OnOpen
//    public void onOpen(Session session) {        this.session = session;
//        webSockets.add(this);
//        WebSocketUtils.onlinePerson(session);
//
//
//        messageVO.setType(1);
//        messageVO.setUserNum(webSockets.size());
//        messageVO.setMessage("有新的连接");
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        String Json = "";
//        try {
//            Json = mapper.writeValueAsString(messageVO);
//        } catch (Exception ex) {
//            log.error(ex.getMessage());
//        }
//
//        this.sendMessage(Json);
//        log.info("【websocket消息】有新的连接, 总数:{}", webSockets.size());
//    }
//
//
//    @OnClose
//    public void onClose() {
//        webSockets.remove(this);
//
//        messageVO.setType(2);
//        messageVO.setUserNum(webSockets.size());
//        messageVO.setMessage("有用户离开");
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        String Json = "";
//        try {
//            Json = mapper.writeValueAsString(messageVO);
//        } catch (Exception ex) {
//            log.error(ex.getMessage());
//        }
//
//        this.sendMessage(Json);
//
//
//        log.info("【websocket消息】连接断开, 总数:{}", webSockets.size());
//    }
//
//    @OnMessage
//    public void onMessage(String message) {
//        if(message.equals("QRCode")){
//            System.out.println("生成二维码");
//            messageVO.setType(3);
//            messageVO.setUserNum(webSockets.size());
//            messageVO.setMessage("生成二维码 QRCode");
//        }
//        ObjectMapper mapper = new ObjectMapper();
//        String Json = "";
//        try {
//            Json = mapper.writeValueAsString(messageVO);
//        } catch (Exception ex) {
//            log.error(ex.getMessage());
//        }
//        this.sendMessage(Json);
//        log.info("【websocket消息】收到客户端发来的消息:{}", message);
//    }
//
//    public void sendMessage(String message) {
//        for (WebSocket webSocket : webSockets) {
//            log.info("【websocket消息】广播消息, message={}", message);
//            try {
//                webSocket.session.getBasicRemote().sendText(message);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
}