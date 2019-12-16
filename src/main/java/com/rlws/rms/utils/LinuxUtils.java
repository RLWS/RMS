package com.rlws.rms.utils;

import com.rlws.rms.common.Constants;
import com.rlws.rms.entity.Cpu;
import com.rlws.rms.entity.Memory;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.StringTokenizer;

/**
 * 获取linux资源使用情况
 *
 * @author rlws
 * @date 2019/12/9  10:30
 */
@Slf4j
public class LinuxUtils {
    /**
     * 获取cpu使用情况
     *
     * @return 返回实体:Cpu
     */
    public static Cpu getCpuInfo() throws IOException {
        //将"top -b"获取的字节流
        InputStream inputStream = Runtime.getRuntime().exec("top -b").getInputStream();
        //将字节流转换为字符流,并放入bufferedReader
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        //跳过前2行
        bufferedReader.readLine();
        bufferedReader.readLine();
        //获取cpu(第3行)内容
        String cpuStr = bufferedReader.readLine();
        Cpu cpu = new Cpu();
        log.info(cpuStr);
        for (String temp : cpuStr.split(Constants.COLON)[1].split(Constants.COMMA)) {
            double num = Double.parseDouble(temp.trim().split(" ")[0]);
            //用户所使用的cpu资源
            if (temp.contains("us")) {
                cpu.setUserUsedCpu(num);
                log.info("用户空间占用CPU的百分比:" + num);
            }
            //系统所使用的cpu资源
            else if (temp.contains("sy")) {
                cpu.setKernelUsedCpu(num);
                log.info("内核空间占用CPU的百分比:" + num);
            }
            //空闲的cpu资源
            else if (temp.contains("id")) {
                cpu.setFreeCpu(num);
                log.info("空闲CPU百分比:" + num);
            }
        }
        //已使用资源
        cpu.setTotalUsedCpu(cpu.getUserUsedCpu() + cpu.getKernelUsedCpu());
        bufferedReader.close();
        return cpu;
    }

    /**
     * 获取Linux的内存信息
     *
     * @return 返回实体:Memory
     */
    public static Memory getMemoryInfo() throws IOException {
        //读取linux文件
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/proc/meminfo"))));
        Memory memory = new Memory();
        String line;
        StringTokenizer token;
        while ((line = bufferedReader.readLine()) != null) {
            token = new StringTokenizer(line);
            line = token.nextToken();
            //总内存
            if ("MemTotal:".equalsIgnoreCase(line)) {
                memory.setMemTotal(Double.parseDouble(token.nextToken()));
            }
            //空闲内存
            else if ("MemFree:".equalsIgnoreCase(line)) {
                memory.setMemFree(Double.parseDouble(token.nextToken()));
            }
            //总交换空间大小
            else if ("SwapTotal:".equalsIgnoreCase(line)) {
                memory.setSwapTotal(Double.parseDouble(token.nextToken()));
            }
            //空闲交换空间
            else if ("SwapFree:".equalsIgnoreCase(line)) {
                memory.setSwapFree(Double.parseDouble(token.nextToken()));
            }
            //高速缓冲存储器（cache memory）用的内存
            else if ("Cached:".equalsIgnoreCase(line)) {
                memory.setCached(Double.parseDouble(token.nextToken()));
            }
        }
        log.info(memory.toString());
        //计算已用百分比
        memory.setMemTotalUsed((memory.getMemTotal() - (memory.getMemFree() + memory.getCached())) * 100 / memory.getMemTotal());
        return memory;
    }
}
