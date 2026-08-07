package com.joubot.bot;

import com.joubot.config.ConfigManager;
import com.joubot.collector.MultiSiteCollector;
import com.joubot.model.Job;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class JoubotBot extends TelegramLongPollingBot {
    
    private final ConfigManager config;
    
    public JoubotBot() {
        this.config = new ConfigManager();
    }
    
    @Override
    public String getBotUsername() {
        return "jjou_bot";  
    }
    
    @Override
    public String getBotToken() {
        return config.getBotToken();
    }
    
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String chatId = update.getMessage().getChatId().toString();
            String messageText = update.getMessage().getText();
            
            if (messageText.equals("/start")) {
                sendMessage(chatId, getWelcomeMessage());
            } else if (messageText.equals("/help")) {
                sendMessage(chatId, getHelpMessage());
            } else if (messageText.equals("/jobs")) {
                handleJobsCommand(chatId);
            } else {
                sendMessage(chatId, "Unknown command. Type /help for available commands.");
            }
        }
    }
    
private void handleJobsCommand(String chatId) {
    try {
        MultiSiteCollector collector = new MultiSiteCollector();
        List<String> keywords = config.getKeywords();
        List<Job> jobs = collector.collectJobs(keywords);
        
        if (jobs.isEmpty()) {
            sendMessage(chatId, "No jobs found for keywords: " + keywords);
        } else {
            StringBuilder response = new StringBuilder("Jobs found:\n\n");
            int count = 0;
            for (Job job : jobs) {
                if (count >= 10) {
                    response.append("\n... and " + (jobs.size() - 10) + " more jobs.");
                    break;
                }
                response.append(job.toString()).append("\n");
                count++;
            }
            sendMessage(chatId, response.toString());
        }
    } catch (Exception e) {
        sendMessage(chatId, "Error fetching jobs: " + e.getMessage());
    }
}
    
    private String getWelcomeMessage() {
        return "Welcome to JOUBOT!\n\n" +
               "I'm a bot who look Jobs for you.\n\n" +
               "Commands:\n" +
               "/help - Show available commands\n" +
               "/start - Show this message\n" +
               "/jobs - List available jobs";
    }
    
    private String getHelpMessage() {
        return "Available Commands:\n\n" +
               "/start - Welcome message\n" +
               "/help - Show this help\n" +
               "/jobs - List available jobs\n\n" +
               "Configuration:\n" +
               "Keywords: " + config.getKeywords() + "\n" +
               "Sites: " + config.getActiveSites() + "\n" +
               "Interval: " + config.getIntervalHours() + " hours";
    }
    
    private void sendMessage(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        
        try {
            execute(message);
        } catch (TelegramApiException e) {
            System.err.println("Error sending message: " + e.getMessage());
        }
    }
}