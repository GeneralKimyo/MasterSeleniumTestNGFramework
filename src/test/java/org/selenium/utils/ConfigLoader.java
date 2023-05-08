package org.selenium.utils;

import org.selenium.pom.constants.EnvType;

import java.util.Properties;

public class ConfigLoader {
    private final Properties properties;
    private static ConfigLoader configLoader;
    private ConfigLoader(){

        String env =  System.getProperty("env", String.valueOf(EnvType.STAGE));
        switch(EnvType.valueOf(env)){
            case STAGE -> {
                    properties = PropertyUtils.
                            propertyLoader("src/test/resources/stg_config.properties");
                    break;
                    }
                case PRODUCTION -> {
                    properties = PropertyUtils.
                                propertyLoader("src/test/resources/prod_config.properties");
                        break;
                    }
                    default -> throw new IllegalStateException("Invalid environment type " +env);
        }
    }
    public static ConfigLoader getInstance(){
        if(configLoader==null){
            configLoader= new ConfigLoader();
        }
        return configLoader;
    }
    private String getProperty(String property){
        String prop = properties.getProperty(property);
        if(prop != null) return prop;
        else throw new RuntimeException("property" + property+ "is not specified in the "+properties+" file");
    }
    public String getBaseUrl(){
        return getProperty("baseUrl");
    }
    public String getUserName(){
        return getProperty("username");
    }
    public String getPassword(){
        return getProperty("password");
    }
}
