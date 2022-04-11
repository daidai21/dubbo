package org.apache.dubbo.common.timer;

import org.apache.dubbo.common.utils.NamedThreadFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class Example {
    public static void main(String[] args) throws InterruptedException {
        demo0();
    }

    private static void demo0() throws InterruptedException {
        // 100ms间隔的时间轮
        final Timer timer = new HashedWheelTimer(new NamedThreadFactory("dubbo-future-timeout", true), 100, TimeUnit.MILLISECONDS);
        // 每隔1s向时间轮添加任务。定时任务也是1s
        for (int i = 0; i < 10; i++) {
            timer.newTimeout(new PrintTask(), 1, TimeUnit.SECONDS);
            System.out.println("task" + i + "added into the timer");
            Thread.sleep(1000);
        }
        Thread.sleep(5000);
    }

}

class PrintTask implements TimerTask {
    @Override
    public void run(Timeout timeout) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        System.out.println("task :" + LocalDateTime.now().format(formatter));
    }
}
