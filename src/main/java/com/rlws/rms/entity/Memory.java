package com.rlws.rms.entity;

import lombok.Data;

/**
 * 存放内存使用数据
 *
 * @author rlws
 * @date 2019/12/9  13:38
 */
@Data
public class Memory {
    /**
     * 总内存
     */
    String memTotal;

    /**
     * 空闲内存
     */
    String memFree;

    /**
     * 总交换空间大小
     */
    String swapTotal;

    /**
     * 空闲交换空间
     */
    String swapFree;

    /**
     * 高速缓冲存储器（cache memory）用的内存
     */
    String cached;
}
