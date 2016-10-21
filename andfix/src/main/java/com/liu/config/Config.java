package com.liu.config;

import com.liu.andfix.App;

/**
 * Created by Administrator on 2016-10-20.
 */
public class Config {
    public static Config instance = new Config();

    private Config() {
        init();
    }

    public static Config getInstance() {
        return instance;
    }

    private String fixapatchUrl = "";

    private void init() {
        PropertiesUtils propertiesUtils = PropertiesUtils.getInstance(App.getInstance(),"voole.properties");
        setFixapatchUrl(propertiesUtils.getProperty("fixapatchurl",""));

    }

    public String getFixapatchUrl() {
        return fixapatchUrl;
    }

    public void setFixapatchUrl(String fixapatchUrl) {
        this.fixapatchUrl = fixapatchUrl;
    }
}
