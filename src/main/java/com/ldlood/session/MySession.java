package com.ldlood.session;

import org.springframework.http.HttpHeaders;
import org.springframework.web.socket.adapter.standard.StandardWebSocketSession;

import java.net.InetSocketAddress;
import java.security.Principal;
import java.util.Map;

public class MySession extends StandardWebSocketSession {
    public MySession(HttpHeaders headers, Map<String, Object> attributes, InetSocketAddress localAddress, InetSocketAddress remoteAddress) {
        super(headers, attributes, localAddress, remoteAddress);
    }

    public MySession(HttpHeaders headers, Map<String, Object> attributes, InetSocketAddress localAddress, InetSocketAddress remoteAddress, Principal user) {
        super(headers, attributes, localAddress, remoteAddress, user);
    }
}
