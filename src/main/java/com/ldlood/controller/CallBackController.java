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

    /**
     * 支付网关回调
     * @param orderId
     * @return
     */
    @RequestMapping("/callBack")
    public String callBack(String orderId){
        try {
            //从redis里拿出 orderId 对应的 session的uuid
            String key = redisTemplate.opsForValue().get(orderId);
            //通过uuid取出用户 会话session
            WebSocketSession session = MyWebSocketUtils.getSession(key);
            if(session==null){
                //如果这个机器上拿不到session 说明用户会话的不是这台  发送通知  通知到所有的机器 让别的机器去给客户端发消息
                template.convertAndSend(RabbitConf.EXCHANGE,RabbitConf.QUEUE_NAME,key);
                return "success";
            }
            //发送消息给客户端
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
