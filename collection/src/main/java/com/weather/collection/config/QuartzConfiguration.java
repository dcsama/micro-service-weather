package com.weather.collection.config;

import com.weather.collection.entity.job.WeatherDataSyncJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfiguration {

    private final int TIME = 1800;

    @Bean(name = "weatherDataSyncJob")
    public JobDetail getWeatherDataSyncJobDetail(){
        return JobBuilder.newJob(WeatherDataSyncJob.class).storeDurably().build();
    }

    @Bean(value = "syncJob")
    public Trigger getSampleSyncJobTrigger(){

        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(TIME).repeatForever();
        return TriggerBuilder.newTrigger()
                .forJob(getWeatherDataSyncJobDetail())
                .withIdentity("weatherDataSyncJob")
                .withSchedule(scheduleBuilder)
                .build();
    }

}
