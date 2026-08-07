package com.joubot;

import com.joubot.bot.JoubotBot;
import com.joubot.collector.MultiSiteCollector;
import com.joubot.model.Job;
import com.joubot.config.ConfigManager;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("JOUBOT Starting...");
        System.out.println("=" .repeat(40));
        
        ConfigManager config = new ConfigManager();
        
        System.out.println("\nCurrent Configuration:");
        System.out.println("  - Keywords: " + config.getKeywords());
        System.out.println("  - Sites: " + config.getActiveSites());
        System.out.println("  - Interval: " + config.getIntervalHours() + " hours");
        System.out.println("  - Language: " + config.getLanguage());
        System.out.println("  - Bot Token: " + (config.getBotToken().isEmpty() ? "NOT SET!" : "Set"));
        
        // Inicia o bot
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new JoubotBot());
            System.out.println("\nJOUBOT is running! Talk to your bot on Telegram.");
            System.out.println("Search for: @jjou_bot");
        } catch (TelegramApiException e) {
            System.err.println("Error starting bot: " + e.getMessage());
        }
        
        // Teste rápido do MultiSiteCollector (opcional)
        testMultiSite();
    }
    
    private static void testMultiSite() {
        try {
            System.out.println("\nTesting MultiSiteCollector...");
            MultiSiteCollector collector = new MultiSiteCollector();
            List<Job> jobs = collector.collectJobs();
            
            System.out.println("\nTotal jobs found: " + jobs.size());
            
            if (!jobs.isEmpty()) {
                System.out.println("\nFirst 3 jobs:");
                for (int i = 0; i < Math.min(3, jobs.size()); i++) {
                    Job job = jobs.get(i);
                    System.out.println("\n--- Job " + (i + 1) + " ---");
                    System.out.println("Title: " + job.getTitle());
                    System.out.println("Company: " + job.getCompany());
                    System.out.println("Location: " + job.getLocation());
                    System.out.println("Link: " + job.getLink());
                }
            }
        } catch (Exception e) {
            System.err.println("MultiSite test failed: " + e.getMessage());
        }
    }
}