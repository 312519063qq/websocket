package com.ldlood.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ldlood.VO.MessageVO;
import com.ldlood.WebSocketUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@Component
@ServerEndpoint("/webSocket")
@Slf4j
public class WebSocketServer {
    private MessageVO messageVO = new MessageVO();

    @OnOpen

    public void onOpen(@PathParam("key")String key,Session session) {
        WebSocketUtils.onlinePerson(session);
        System.out.println("有新的连接" );
    }
    @OnMessage
    public void onMessage(Session session, String message) {
        String[] flag = message.split(",");
        if(flag[0].equals("QRCode")){
            WebSocketUtils.revertMap.put(flag[1],session);
            System.out.println("生成二维码");
            messageVO.setType(3);
            messageVO.setMessage("生成二维码 uuid=="+flag[1]);
        }
        ObjectMapper mapper = new ObjectMapper();
        String Json = "";
        try {
            Json = mapper.writeValueAsString(messageVO);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        this.sendMessage(session,Json);
        log.info("【websocket消息】收到客户端发来的消息:{}", message);
    }
    @OnClose
    public void onClose(Session session) {
        System.out.println("连接关闭");
    }
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("连接错误=========");
    }

    public static void sendMessage(Session session , String message) {
        log.info("【websocket消息】广播消息, message={}", message);
        try {
            session.getBasicRemote().sendText(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
