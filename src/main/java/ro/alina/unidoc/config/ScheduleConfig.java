package ro.alina.unidoc.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@ConditionalOnProperty(value = "schedule.enable", havingValue = "true")
@Configuration
@EnableScheduling
public class ScheduleConfig {
}
