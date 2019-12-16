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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;

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

    @RequestMapping(value = "getCurveChartNode")
    @ResponseBody
    public Object getCurveChartNode() {
        Set<String> curveChartNode = CurveChart.getCurveChartNode();
        return JSON.toJSON(curveChartNode);
    }

    /**
     * 每秒钟取一次资源值
     *
     * @throws IOException I/O异常抛出
     */
    @Scheduled(cron = "0/1 * * * * ?")
    public void timeScheduled() throws IOException {
        HashMap<String, Double> yNode = new HashMap<>(10);
        yNode.put("cpu", new Double(NumberUtils.toFix(LinuxUtils.getCpuInfo().getTotalUsedCpu(), 2)));
        yNode.put("memory", new Double(NumberUtils.toFix(LinuxUtils.getMemoryInfo().getMemTotalUsed(), 2)));
        Calendar instance = Calendar.getInstance();
        String xNode = instance.get(Calendar.HOUR) + ":" + instance.get(Calendar.MINUTE) + ":" + instance.get(Calendar.SECOND);
        log.info("---------------------------------" + xNode + "-------------------------------");
        HashMap<String, Object> map = CurveChart.addAxis(yNode, xNode);
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
