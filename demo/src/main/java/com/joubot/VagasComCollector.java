package com.joubot;

import com.joubot.model.Job;
import com.joubot.collector.JobCollector;
import com.joubot.filter.JobFilter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class VagasComCollector implements JobCollector {
    
    private static final String BASE_URL = "https://www.vagas.com.br/vagas-de-tecnologia";
    private static final String SITE_NAME = "Vagas.com";
    
    public List<Job> collectJobs(List<String> keywords) throws Exception {
        List<Job> allJobs = collectJobs();
        JobFilter filter = new JobFilter(keywords);
        return filter.filterJobs(allJobs);
    }
    
    @Override
    public List<Job> collectJobs() throws Exception {
        List<Job> jobs = new ArrayList<>();
        
        System.out.println("Connecting to Vagas.com...");
        Document doc = Jsoup.connect(BASE_URL)
                .timeout(15000)
                .userAgent("Mozilla/5.0")
                .get();
        System.out.println("Connected!");
        
        String[] selectors = {
            "div.vaga",
            "div.vaga-item",
            "div.vaga-result",
            "li.vaga-item",
            "div[class*='vaga']",
            "a[href*='/vaga/']"
        };
        
        Elements jobElements = new Elements();
        for (String selector : selectors) {
            Elements temp = doc.select(selector);
            if (!temp.isEmpty()) {
                System.out.println("Found with selector: " + selector + " (" + temp.size() + " items)");
                jobElements = temp;
                break;
            }
        }
        
        if (jobElements.isEmpty()) {
            System.out.println("Trying fallback selector...");
            jobElements = doc.select("div:has(a[href*='/vaga/'])");
            System.out.println("Found " + jobElements.size() + " items with fallback");
        }
        
        System.out.println("Total elements to process: " + jobElements.size());
        
        for (Element element : jobElements) {
            try {
                Element titleElement = element.selectFirst("a[href*='/vaga/']");
                if (titleElement == null) {
                    titleElement = element.selectFirst("a");
                }
                if (titleElement == null) continue;
                
                String title = titleElement.text().trim();
                String link = titleElement.attr("href");
                if (link.startsWith("/")) link = "https://www.vagas.com.br" + link;
                
                if (title.isEmpty() || title.length() < 3) continue;
                
                Element companyElement = element.selectFirst("span.empresa, span.nome-empresa, .empresa, [class*='empresa']");
                String company = companyElement != null ? companyElement.text().trim() : "N/A";
                
                Element locationElement = element.selectFirst("span.local, span.cidade, .local, [class*='local']");
                String location = locationElement != null ? locationElement.text().trim() : "N/A";
                
                Element dateElement = element.selectFirst("span.data, time, [class*='data']");
                String publishedDate = dateElement != null ? dateElement.text().trim() : "N/A";
                
                Job job = new Job(title, company, location, link, publishedDate, "", "");
                jobs.add(job);
                System.out.println("Added: " + title + " - " + company);
                
            } catch (Exception e) {
                System.err.println("Error parsing: " + e.getMessage());
            }
        }
        
        System.out.println("Collected " + jobs.size() + " jobs from " + SITE_NAME);
        return jobs;
    }
    
    @Override
    public String getSiteName() {
        return SITE_NAME;
    }
}