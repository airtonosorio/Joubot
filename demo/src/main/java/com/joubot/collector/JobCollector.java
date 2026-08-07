package com.joubot.collector;

import com.joubot.model.Job;
import java.util.List;

public interface JobCollector {
    List<Job> collectJobs() throws Exception;
    default List<Job> collectJobs(List<String> keywords) throws Exception {
        return collectJobs();
    }
    String getSiteName();
}