package com.ldlood.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ldlood.Service.WebSocket;
import com.ldlood.Service.WebSocketServer;
import com.ldlood.VO.MessageVO;
import com.ldlood.WebSocketUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.websocket.Session;
import java.io.IOException;

@Controller
public class CallBackController {
    @RequestMapping("/callBack")
    public String callBack(String sessionKey){
        try {
            Session session = WebSocketUtils.getSession(sessionKey);
            MessageVO messageVO = new MessageVO();
            System.out.println("生成二维码");
            messageVO.setType(3);
            messageVO.setMessage("支付成功");

            ObjectMapper mapper = new ObjectMapper();
            String Json = "";
            try {
                Json = mapper.writeValueAsString(messageVO);
            } catch (Exception ex) {
            }
            WebSocketServer.sendMessage(session,Json);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }
}
