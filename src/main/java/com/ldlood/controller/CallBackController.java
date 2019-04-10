package com.ldlood.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ldlood.MyWebSocketUtils;
import com.ldlood.config.RabbitConf;
import com.ldlood.service.MessageService;
import com.ldlood.service.UserService;
import com.ldlood.service.WebSocketService;
import com.ldlood.VO.MessageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.WebSocketSession;

import javax.websocket.Session;

@Slf4j
@RestController
public class CallBackController {

    @Autowired
    private UserService userService;

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private RabbitTemplate template;

    @RequestMapping("/callBack")
    public String callBack(String orderId){
        try {
            String key = redisTemplate.opsForValue().get(orderId);
            WebSocketSession session = MyWebSocketUtils.getSession(key);
            if(session==null){
                template.convertAndSend(RabbitConf.EXCHANGE,RabbitConf.QUEUE_NAME,key);
                return "success";
            }
            MessageVO vo = new MessageVO();
            vo.setMessage("支付成功");
            webSocketService.sendMessage(session, JSON.toJSONString(vo));
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
