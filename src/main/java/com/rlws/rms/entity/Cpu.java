package com.rlws.rms.entity;

import lombok.Data;

/**
 * 存放cpu数据
 *
 * @author rlws
 * @date 2019/12/9  11:37
 */
@Data
public class Cpu {
    /**
     * 空闲的CPU
     */
    double freeCpu;

    /**
     * 总共已使用的CPU
     */
    double totalUsedCpu;

    /**
     * Linux内核占用的CPU
     */
    double kernelUsedCpu;

    /**
     * 用户占用的CPU
     */
    double userUsedCpu;

}
