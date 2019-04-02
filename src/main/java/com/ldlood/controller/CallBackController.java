package com.ldlood.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ldlood.MyWebSocketUtils;
import com.ldlood.service.MessageService;
import com.ldlood.service.UserService;
import com.ldlood.service.WebSocketService;
import com.ldlood.VO.MessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.WebSocketSession;

@RestController
public class CallBackController {

    @Autowired
    private UserService userService;

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private MessageService messageService;

    @RequestMapping("/callBack")
    public String callBack(String sessionKey){
        try {
            int  aa = userService.getInt();
            System.out.println("userService.getInt() ----------------方法调用==="+aa);
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
            webSocketService.sendMessage(session,Json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }


    @RequestMapping("/sendMsg")
    public String sendMsg(){
        messageService.sendMsg();
        return "success";
    }
}
