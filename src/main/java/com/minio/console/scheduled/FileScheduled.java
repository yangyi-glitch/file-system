package com.minio.console.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FileScheduled {

    @Scheduled(cron = "0 * * * * ?")
    public void scanEnterpriseUserAssociations() {
        log.info("定时扫描~~~~~");
    }
}
