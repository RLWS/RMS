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
     * 向连接池中的所有连接推送信息
     *
     * @throws IOException IO异常
     * @Scheduled(cron = "0/1 * * * * ?")
     */
    private void sendMessageFormPool() throws IOException {
        String test = "{\"cpu\":{\"freeCpu\":82.4,\"kernelUsedCpu\":11.8,\"totalUsedCpu\":56.0,\"userUsedCpu\":5.9},\"memory\":{\"memFree\":\"88796\",\"memTotal\":\"1882236\",\"swapFree\":\"0\",\"swapTotal\":\"0\"}}";
        for (int i = 0; i < HardwareResourceSocketPool.getPool().size(); i++) {
            HardwareResourceSocketPool.getPool().get(i).sendMessage(test);
        }
    }
}
