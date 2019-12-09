package com.rlws.rms.controller;

import com.rlws.rms.pool.HardwareResourceSocketPool;
import com.rlws.rms.utils.LinuxUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 一个测试webSocket的控制器
 *
 * @author rlws
 * @date 2019/12/3  9:53
 */
@Slf4j
@RestController
@EnableScheduling
public class TestController {

    /**
     * 标识:开始计时
     */
    private final String START_TIME = "A0";

    /**
     * 标识:停止计时
     */
    private final String STOP_TIME = "A1";

    /**
     * 启动状态标识
     */
    private String status;

    /**
     * 全局时间
     */
    private Integer time = 0;

    /**
     * cpu测试
     */
    @RequestMapping(value = "cpu")
    public void cpu() throws IOException {
        log.info(LinuxUtils.getCpuInfo().toString());
    }
    /**
     * cpu测试
     */
    @RequestMapping(value = "memory")
    public void memory() throws IOException {
        log.info(LinuxUtils.getMemoryInfo().toString());
    }

    /**
     * 开始计时
     */
    @RequestMapping(value = "start")
    public void start() {
        log.info("开始计时");
        status = START_TIME;
    }

    /**
     * 停止计时
     */
    @RequestMapping(value = "stop")
    public void stop() {
        log.info("停止计时");
        status = STOP_TIME;
    }

    /**
     * 暂停计时
     */
    @RequestMapping(value = "pause")
    public void pause() {
        log.info("暂停计时");
        status = "";
    }

    /**
     * 向连接池中的所有连接推送信息
     *
     * @throws IOException IO异常
     */
    private void sendMessageFormPool() throws IOException {
        for (int i = 0; i < HardwareResourceSocketPool.getPool().size(); i++) {
            HardwareResourceSocketPool.getPool().get(i).sendMessage(time.toString());
        }
    }
}
