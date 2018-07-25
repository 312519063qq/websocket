package com.ldlood.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ldlood.Handler.MyWebSocketHandler;
import com.ldlood.MyWebSocketUtils;
import com.ldlood.VO.MessageVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.WebSocketSession;

@Controller
public class CallBackController {
    @RequestMapping("/callBack")
    public String callBack(String sessionKey){
        try {
            WebSocketSession session = MyWebSocketUtils.getSession(sessionKey);
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
            MyWebSocketHandler.sendmessage(session,Json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }
}
