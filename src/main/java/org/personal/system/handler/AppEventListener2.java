package org.personal.system.handler;

import lombok.extern.slf4j.Slf4j;
import org.personal.system.event.AppEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Component
@EnableAsync(proxyTargetClass = true)
@Slf4j
public class AppEventListener2 {

    @EventListener(condition="#event.message != 'abc'")
    @Async
    public void onApplicationEvent(AppEvent event) {
        log.info("====================AppEventListener2 receive message====================");
        log.info(event.toString());
        log.info("====================AppEventListener2 receive message====================");
    }

}