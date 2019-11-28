package com.rlws.rms.websocket.impl;

import com.rlws.rms.pool.HardwareResourceSocketPool;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * 对Linux的硬件资源信息进行传输
 *
 * @author rlws
 * @date 2019/11/28  9:45
 */

@Slf4j
@ServerEndpoint("/hardwareResource")
public class HardwareResourceSocket {

    /**
     * 连接会话,需要通过它给客户端发送数据
     */
    private Session session;

    /**
     * 连接建立成功调用的方法
     *
     * @param session [可选的参数]连接会话.
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        HardwareResourceSocketPool.getPool().add(this);
    }

    /**
     * 收到客户端信息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @param session 连接会话
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("客户端" + session.getId() + "发送信息:" + message);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        log.info("连接关闭");
        HardwareResourceSocketPool.getPool().remove(this);
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) throws IOException {
        log.error("发生错误", error);
        session.close();
    }

    /**
     * 发送信息到客户端
     *
     * @param message 需要发送的信息
     * @throws IOException IO异常
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }
}