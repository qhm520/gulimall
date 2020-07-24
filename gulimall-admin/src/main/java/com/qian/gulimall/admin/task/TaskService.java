package com.qian.gulimall.admin.task;

/**
 * Created by IntelliJ IDEA.
 * TaskService is 定时任务接口，所有定时任务都要实现该接口
 *
 * @author QIAN
 * Date 2020/07/22
 * Time 19:01
 */
public interface TaskService {

    /**
     * 执行定时任务接口
     *
     * @param params   参数，多参数使用JSON数据
     */
    void run(String params);
}
