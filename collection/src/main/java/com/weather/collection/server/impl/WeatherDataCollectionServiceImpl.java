package com.weather.collection.server.impl;

import com.weather.collection.server.IWeatherDataCollectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service("weatherDataCollectionService")
public class WeatherDataCollectionServiceImpl implements IWeatherDataCollectionService {

    @Resource(name = "restTemplate")
    private RestTemplate restTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private final Long TIME_OUT = 1800L;

    private final String WEATHER_API = "http://wthrcdn.etouch.cn/weather_mini";

    private final Logger logger = LoggerFactory.getLogger(WeatherDataCollectionServiceImpl.class);

    @Override
    public void syncDataByCityId(String cityId) {

        logger.info("开始同步天气数据. city id = " + cityId);
        saveWeatherData(WEATHER_API);
    }

    private void saveWeatherData(String uri) {

        ValueOperations<String, String> vos = stringRedisTemplate.opsForValue();
        String body = "unknown";

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);
        if (responseEntity.getStatusCode().value() == 200){
            body = responseEntity.getBody() == null ? "" : responseEntity.getBody();
        }

        vos.set(uri, body, TIME_OUT, TimeUnit.SECONDS);
    }
}
