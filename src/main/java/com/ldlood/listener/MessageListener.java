package com.ldlood.listener;

import com.ldlood.config.RabbitConf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageListener {

    @RabbitListener(queues = {RabbitConf.QUEUE_NAME}, containerFactory="rabbitListenerContainerFactory")
    public void messageHandler(String msg){
        log.info("msg:{}",msg);
        System.out.println(msg);
    }
}
