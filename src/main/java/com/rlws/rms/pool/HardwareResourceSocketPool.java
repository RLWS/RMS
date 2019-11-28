package com.rlws.rms.pool;

import com.rlws.rms.websocket.impl.HardwareResourceSocket;
import java.util.LinkedList;

/**
 * HardwareResourceSocket的线程池
 *
 * @author rlws
 * @date 2019/11/28  10:04
 */

public class HardwareResourceSocketPool {
    /**
     * 线程池
     */
    private static final LinkedList<HardwareResourceSocket> LIST = new LinkedList<>();

    /**
     * 获取HardwareResourceSocket的线程池
     *
     * @return 线程池
     */
    public static LinkedList<HardwareResourceSocket> getPool(){
        return LIST;
    }
}
