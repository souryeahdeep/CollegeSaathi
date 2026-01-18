package org.college.teacher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DatabaseCleanupJob {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Scheduled(cron = "0 0 0 * * ?")
    public void truncateTable() {
        jdbcTemplate.execute(
                "TRUNCATE TABLE attendance_record RESTART IDENTITY CASCADE"
        );
    }

}

