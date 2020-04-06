package com.sy.blog.utils.Timer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TaskTimer {
    private static int a = 0;
    @Scheduled(cron = "0 0 0 * * ? ")//每天凌晨
    public void test(){
        System.out.println(a++);
    }
}
