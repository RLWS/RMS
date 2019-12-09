package com.rlws.rms.entity;

import lombok.Builder;
import lombok.Data;

/**
 * Linux资源汇总
 *
 * @author rlws
 * @date 2019/12/9  17:17
 */
@Data
@Builder
public class LinuxResources {
    /**
     * cpu相关
     */

    Cpu cpu;
    /**
     * 内存相关
     */
    Memory memory;
}
