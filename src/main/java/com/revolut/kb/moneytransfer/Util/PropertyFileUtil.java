/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revolut.kb.moneytransfer.Util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Kiran
 */
public class PropertyFileUtil {

    
    private static final String PROPERTIES_PATH ="application.properties";
    private static final Logger LOGGER = LogManager.getLogger(PropertyFileUtil.class);

//    public static void main(String args[]) {
//        String value = readValueFromPropertiesFile("moneytransfer.database");
//        LOGGER.info("Property value -- {} ",value );
//    }

    //  Read the properties file from Classpath.    
    //TODO : Think of better way of handling when no value is found
    //       Rather than sending null value.
    public static String readValueFromPropertiesFile(String key) {
        PropertyFileUtil util = new PropertyFileUtil();
        Properties properties = util.loadProperties();
        String value = properties.getProperty(key);
        LOGGER.info("Value of {} is {} ", key, properties.getProperty(key));
        if(value == null || value.isEmpty()){
            LOGGER.error("No value found for key {} ", key);
            return null;
        }
        return value;
    }

    public Properties loadProperties() {
        Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            // Loading properties file from the classpath
            inputStream = this.getClass().getClassLoader().getResourceAsStream(PROPERTIES_PATH);
            if (inputStream == null) {
                throw new IOException("File not found");
            }
            properties.load(inputStream);
        } catch (IOException ex) {
            LOGGER.error("Logging IOException -- \n", ex);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                LOGGER.error("Logging IOException -- \n", ex);
            }
        }
        return properties;
    }
    
    
    public static void main(String args[]){
        LOGGER.info("Key - {}" , readValueFromPropertiesFile("moneytransfer.database"));
    }

}
