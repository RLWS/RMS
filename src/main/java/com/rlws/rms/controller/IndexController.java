package com.rlws.rms.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rlws.rms.entity.LinuxResources;
import com.rlws.rms.pool.HardwareResourceSocketPool;
import com.rlws.rms.utils.LinuxUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

/**
 * @author rlws
 * @date 2019/11/28  11:38
 */

@Controller
public class IndexController {

    @RequestMapping(value = "index")
    public String index(ModelAndView modelAndView) {
        modelAndView.addObject("name", "test");
        return "index";
    }

    /**
     * 每秒钟取一次资源值
     *
     * @throws IOException I/O异常抛出
     */
    @Scheduled(cron = "0/1 * * * * ?")
    public void timeScheduled() throws IOException {
        sendMessageFormPool(LinuxResources.builder().cpu(LinuxUtils.getCpuInfo()).memory(LinuxUtils.getMemoryInfo()).build());
    }

    /**
     * 向连接池中的所有连接推送信息
     *
     * @param linuxResources Linux资源对象
     * @throws IOException I/O异常
     */
    private void sendMessageFormPool(LinuxResources linuxResources) throws IOException {
        for (int i = 0; i < HardwareResourceSocketPool.getPool().size(); i++) {
            HardwareResourceSocketPool.getPool().get(i).sendMessage(JSON.toJSONString(linuxResources));
        }
    }
}
