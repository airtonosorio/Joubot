package com.joubot.filter;

import com.joubot.model.Job;
import java.util.List;
import java.util.stream.Collectors;

public class JobFilter {
    
    private List<String> keywords;
    
    public JobFilter(List<String> keywords) {
        this.keywords = keywords;
    }
    
    public List<Job> filterJobs(List<Job> jobs) {
        if (keywords == null || keywords.isEmpty()) {
            return jobs;
        }
        
        return jobs.stream()
                .filter(job -> isRelevant(job))
                .collect(Collectors.toList());
    }
    
    private boolean isRelevant(Job job) {
        String title = job.getTitle() != null ? job.getTitle() : "";
        String company = job.getCompany() != null ? job.getCompany() : "";
        String location = job.getLocation() != null ? job.getLocation() : "";
        String description = job.getDescription() != null ? job.getDescription() : "";
        String link = job.getLink() != null ? job.getLink() : "";
        
        String fullText = (title + " " + company + " " + location + " " + description + " " + link).toLowerCase();
        
        for (String keyword : keywords) {
            if (keyword == null || keyword.trim().isEmpty()) continue;
            if (fullText.contains(keyword.trim().toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}