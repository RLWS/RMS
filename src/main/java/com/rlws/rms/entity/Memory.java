package com.rlws.rms.entity;

import lombok.Data;

import javax.validation.constraints.Digits;

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
    Double memTotal;

    /**
     * 空闲内存
     */
    Double memFree;

    /**
     * 总交换空间大小
     */
    Double swapTotal;

    /**
     * 空闲交换空间
     */
    Double swapFree;

    /**
     * 高速缓冲存储器（cache memory）用的内存
     */
    Double cached;

    /**
     * 已用百分比
     */
    Double memTotalUsed;
}
