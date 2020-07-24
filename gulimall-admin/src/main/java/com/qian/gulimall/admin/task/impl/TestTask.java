package com.qian.gulimall.admin.task.impl;

import com.qian.gulimall.admin.task.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * TestTask is 测试定时任务(演示Demo，可删除)
 * testTask为spring bean的名称
 *
 * @author QIAN
 * Date 2020/07/22
 * Time 19:02
 */
@Slf4j
@Component("testTask")
public class TestTask implements TaskService {

    @Override
    public void run(String params) {
        try {
            log.info("TestTask定时任务正在执行，参数为：{}", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
