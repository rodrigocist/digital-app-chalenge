package com.digital.challenge.task;

import com.digital.challenge.services.ConsumeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ScheduledNews {

    @Autowired
    private ConsumeService consumeService;

  /*  @EventListener(ApplicationReadyEvent.class)
    public void newsServiceProcessAfterStartup() {
        consumeService.consumeApi();
    }
   */

    @EventListener(ApplicationReadyEvent.class)
    @Scheduled(cron = "${news.check.scheduler}")
    public void newsServiceProcess() {
        consumeService.consumeApi();
    }




}
