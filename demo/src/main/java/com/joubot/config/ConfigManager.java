package com.joubot.config;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class ConfigManager {
    private Properties properties = new Properties();
    
    public ConfigManager() {
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("config.properties not found!");
                return;
            }
            properties.load(input);
            System.out.println("Config loaded!");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    public List<String> getKeywords() {
        String keywords = properties.getProperty("keywords", "estagio,dev,java");
        return Arrays.asList(keywords.split(","));
    }
    
    public int getIntervalHours() {
        return Integer.parseInt(properties.getProperty("interval.hours", "6"));
    }
    
    public List<String> getActiveSites() {
        String sites = properties.getProperty("active.sites", "programathor");
        return Arrays.asList(sites.split(","));
    }
    
    public String getBotToken() {
        return properties.getProperty("bot.token", "");
    }
    
    public String getLanguage() {
        return properties.getProperty("language", "pt");
    }
}