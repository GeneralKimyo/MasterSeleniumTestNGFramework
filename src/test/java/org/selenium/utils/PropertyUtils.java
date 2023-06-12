package org.selenium.utils;

import java.io.*;
import java.util.Properties;

public class PropertyUtils {
    public static Properties propertyLoader(String filePath){
        Properties properties= new Properties();
        FileInputStream is;
        try {
            is= new FileInputStream(filePath);
            try {
                properties.load(is);
                is.close();
            }
            catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("failed to load properties file " +filePath);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("properties file not found at " + filePath);
             }
        return properties;

    }
}
