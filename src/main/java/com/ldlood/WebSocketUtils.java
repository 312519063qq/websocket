package com.ldlood;

import org.json.JSONObject;

import javax.websocket.Session;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WebSocketUtils {
    /**
     * 存放session的集合
     * 对应uuid
     */
    public static Map<Session, String> map = new HashMap<Session, String>();
    /**
     * map的反转集合，为了方便，多存储一个集合，MapUtils内有反转方法，可自行了解
     */
    public static Map<String, Session> revertMap = new HashMap<String, Session>();

    /**
     * 加入新链接
     * @param session
     */
    public static void onlinePerson(Session session) {
        if (map.get(session) != null) {
            return;
        }
        String uuid = UUID.randomUUID().toString().replaceAll("_","");
        map.put(session, uuid);
        revertMap.put(uuid, session);
    }
    /**
     * 链接退出
     * @param session
     */
    public static void offlinePerson(Session session) {
//        revertMap.remove(MapUtils.getString(map, session));
        map.remove(session);
    }
    /**
     * 通过uuid查询session
     */
    public static Session getSession(String key) {
        return revertMap.get(key);
    }
    /**
     * 通过session获取uuid
     */
//    public static String getUUid(Session session) {
//        return MapUtils.getString(map, session);
//    }
    /**
     * 向session发送信息
     * @param message
     * @throws IOException
     */
    public static void sendMessage(Session session, String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }
    /**
     * 清理无效的链接，可自行决定执行时机
     */
    public static void clearSession() {
        for (Map.Entry<Session, String> obj : map.entrySet()) {
            Session session = obj.getKey();
            if (!session.isOpen()) {
                revertMap.remove(obj.getValue());
                map.remove(session);
            }
        }
    }
}
