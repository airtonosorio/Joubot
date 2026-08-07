package com.joubot.collector;

import com.joubot.model.Job;
import com.joubot.filter.JobFilter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ProgramathorCollector implements JobCollector {
    
    private static final String BASE_URL = "https://programathor.com.br/jobs";
    private static final String SITE_NAME = "Programathor";
    
    public List<Job> collectJobs(List<String> keywords) throws Exception {
        List<Job> allJobs = collectJobs();
        JobFilter filter = new JobFilter(keywords);
        return filter.filterJobs(allJobs);
    }
    
    @Override
    public List<Job> collectJobs() throws Exception {
        List<Job> jobs = new ArrayList<>();
        
        System.out.println("Connecting to Programathor...");
        
        Document doc = Jsoup.connect(BASE_URL)
                .timeout(60000) .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36").header("Accept", "text/html,application/xhtml+xml,application/xml").header("Accept-Language", "pt-BR,pt;q=0.9,en-US;q=0.8,en;q=0.7").get();
        
        System.out.println("Connected!");
        
        Elements jobElements = doc.select("a[href^=\"/jobs/\"]");
        
        System.out.println("Found " + jobElements.size() + " job listings");
        
        for (Element element : jobElements) {
            try {
                Element titleElement = element.selectFirst("h3.text-24");
                if (titleElement == null) continue;
                String title = titleElement.text().trim();
                
                String link = element.attr("href");
                if (link.startsWith("/")) {
                    link = "https://programathor.com.br" + link;
                }
                
                Element companyElement = element.selectFirst("span:has(.fa-briefcase)");
                String company = companyElement != null ? companyElement.text().trim() : "N/A";
                
                Element locationElement = element.selectFirst("span:has(.fa-map-marker-alt)");
                String location = locationElement != null ? locationElement.text().trim() : "N/A";
                
                if (!title.isEmpty() && title.length() > 3) {
                    Job job = new Job(title, company, location, link, "N/A", "", "");
                    jobs.add(job);
                    System.out.println("Added: " + title + " - " + company);
                }
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