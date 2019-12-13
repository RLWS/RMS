package com.rlws.rms.utils.chart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;


/**
 * JS插件Chartist曲线图的数据生成工具
 *
 * @author rlws
 * @date 2019/12/12  10:38
 */
public class CurveChart {

    /**
     * 图表的横坐标长度
     */
    private static final int CHART_DATA_LENGTH = 15;

    /**
     * 横坐标
     */
    private static ArrayBlockingQueue<String> labels = new ArrayBlockingQueue<>(CHART_DATA_LENGTH);

    /**
     * 纵坐标(返回)
     */
    private static List<ArrayBlockingQueue<Double>> series = new ArrayList<>();

    /**
     * 纵坐标(记录)
     */
    private static HashMap<String, ArrayBlockingQueue<Double>> seriesMap = new HashMap<>();

    /**
     * 设置横坐标到labels队列中
     *
     * @param xNote 传入的横坐标
     * @return 是否添加成功
     */
    private static boolean addAbscissa(String xNote) {
        return addNode(labels, xNote);
    }

    /**
     * 添加纵坐标
     *
     * @param map 需要添加的纵坐标
     * @return 是否添加成功
     */
    private static boolean addOrdinate(HashMap<String, Double> map) {
        boolean status = true;
        ArrayList<String> existedNode = new ArrayList<>();
        for (String key : map.keySet()) {
            for (String seriesKey : seriesMap.keySet()) {
                if (key.equals(seriesKey)) {
                    status = false;
                    addNode(seriesMap.get(seriesKey), map.get(key));
                }
            }
            if (status) {
                //代表该节点数据中未存在
                existedNode.add(key);
            }
        }
        //添加不存在的节点
        for (String key : existedNode) {
            ArrayBlockingQueue<Double> newQueue = new ArrayBlockingQueue<>(CHART_DATA_LENGTH);
            addNode(newQueue, map.get(key));
            seriesMap.put(key, newQueue);
        }
        //seriesMap ==> series
        series.clear();
        for (String key : seriesMap.keySet()) {
            series.add(seriesMap.get(key));
        }
        return true;
    }

    /**
     * 自定义queue插入:值大于CHART_DATA_LENGTH时,移除第一个并在最后插入
     *
     * @param queue 被插入的队列
     * @param e     要插入的节点
     * @param <E>   泛型
     * @return 是否成功
     */
    private static <E> boolean addNode(ArrayBlockingQueue<E> queue, E e) {
        //判断队列是否满了
        if (queue.size() == CHART_DATA_LENGTH) {
            queue.poll();
        }
        return queue.offer(e);
    }

    /**
     * 添加一个节点
     *
     * @param map   纵坐标
     * @param xNode 横坐标
     * @return 是否成功
     */
    public static HashMap<String, Object> addAxis(HashMap<String, Double> map, String xNode) {
        if (addAbscissa(xNode) && addOrdinate(map)) {
            HashMap<String, Object> chartData = new HashMap<>(2);
            chartData.put("labels", labels);
            chartData.put("series", series);
            return chartData;
        }
        return null;
    }
}
