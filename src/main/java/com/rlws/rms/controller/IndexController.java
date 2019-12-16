package com.rlws.rms.controller;

import com.alibaba.fastjson.JSON;
import com.rlws.rms.pool.HardwareResourceSocketPool;
import com.rlws.rms.utils.LinuxUtils;
import com.rlws.rms.utils.NumberUtils;
import com.rlws.rms.utils.chart.CurveChart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

/**
 * @author rlws
 * @date 2019/11/28  11:38
 */

@Slf4j
@Controller
@EnableScheduling
public class IndexController {

    @RequestMapping(value = "index")
    public String index(ModelAndView modelAndView) {
        modelAndView.addObject("name", "test");
        return "dashboard";
    }

    /**
     * 每秒钟取一次资源值
     *
     * @throws IOException I/O异常抛出
     */
    @Scheduled(cron = "0/1 * * * * ?")
    public void timeScheduled() throws IOException {
        log.info("----------------------------------------------------------------");
        HashMap<String, Double> yNode = new HashMap<>(10);
        yNode.put("cpu", LinuxUtils.getCpuInfo().getTotalUsedCpu());
        yNode.put("memory", new Double(NumberUtils.toFix(LinuxUtils.getMemoryInfo().getMemTotalUsed(), 2)));
        HashMap<String, Object> map = CurveChart.addAxis(yNode, Calendar.getInstance().get(Calendar.MINUTE) + ":" + Calendar.getInstance().get(Calendar.SECOND));
        sendMessageFormPool(map);
    }

    /**
     * 向连接池中的所有连接推送信息
     *
     * @param message 推送的信息
     * @throws IOException I/O异常
     */
    private void sendMessageFormPool(Object message) throws IOException {
        for (int i = 0; i < HardwareResourceSocketPool.getPool().size(); i++) {
            HardwareResourceSocketPool
                    .getPool()
                    .get(i)
                    .sendMessage(JSON.toJSONString(message));
        }
    }
}
