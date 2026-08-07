package com.joubot;

import com.joubot.model.Job;
import com.joubot.collector.JobCollector;
import com.joubot.collector.ProgramathorCollector;
import com.joubot.filter.JobFilter;
import java.util.ArrayList;
import java.util.List;

public class MultiSiteCollector implements JobCollector {
    
    public List<Job> collectJobs(List<String> keywords) throws Exception {
        List<Job> allJobs = new ArrayList<>();
        
        System.out.println("Searching for jobs...");
        System.out.println("=" .repeat(40));
        
        try {
            ProgramathorCollector prog = new ProgramathorCollector();
            allJobs.addAll(prog.collectJobs(keywords));
            System.out.println("Programathor: " + allJobs.size() + " jobs found");
        } catch (Exception e) {
            System.err.println("Programathor error: " + e.getMessage());
        }
        
        JobFilter filter = new JobFilter(keywords);
        return filter.filterJobs(allJobs);
    }
    
    @Override
    public List<Job> collectJobs() throws Exception {
        return collectJobs(new ArrayList<>());
    }
    
    @Override
    public String getSiteName() {
        return "MultiSite (Programathor)";
    }
}