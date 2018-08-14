package com.weather.collection.server;

/**
 * 天气数据采集接口
 */
public interface IWeatherDataCollectionService {

    /**
     * 根据城市的ID同步天气数据
     * @param cityId 城市ID
     */
    void syncDataByCityId(String cityId);
}
