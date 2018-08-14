package com.weather.collection.entity.job;

import com.weather.collection.entity.City;
import com.weather.collection.server.IWeatherDataCollectionService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class WeatherDataSyncJob extends QuartzJobBean {

    private Logger logger = LoggerFactory.getLogger(WeatherDataSyncJob.class);

    @Resource(name = "weatherDataCollectionService")
    private IWeatherDataCollectionService weatherDataCollectionService;


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        logger.info("同步天气任务启动");

        List<City> cityList = new ArrayList<>();
        City city = new City();
        city.setId("101280601");
        cityList.add(city);

        for (City item : cityList) {
            logger.info("天气数据同步任务执行, city id = " + item.getId());
            this.weatherDataCollectionService.syncDataByCityId(item.getId());
            logger.info("同步完成. city id = " + item.getId());
        }
    }
}
