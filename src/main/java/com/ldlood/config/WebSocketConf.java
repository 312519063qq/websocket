package com.ldlood.config;

import com.ldlood.Handler.MyWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConf implements WebSocketConfigurer{

    @Autowired
    private MyWebSocketHandler webSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(webSocketHandler,"/webSocket").setAllowedOrigins("*");
    }

//    @Bean
//    public MyWebSocketHandler webSocketHandler(){
//        return new MyWebSocketHandler();
//    }
}
