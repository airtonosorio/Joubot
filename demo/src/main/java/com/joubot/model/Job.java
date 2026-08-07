package com.joubot.model;

import java.util.Objects;

public class Job {

    private String title;
    private String company;
    private String location;
    private String link;
    private String publishedDate;
    private String description;
    private String salary;

    public Job(String title, String company, String location, String link, String publishedDate, String description, String salary ) {
        this.title = title;
        this.company = company;
        this.link = link;
        this.publishedDate = publishedDate;
        this.description = description;
        this.salary = salary;
    }

    public String getTitle() {return title;}    
    public String getCompany() {return company;}
    public String getLocation() {return location;}
    public String getLink() {return link;}
    public String getPublishedDate() {return publishedDate;}
    public String getDescription() {return description;}
    public String getSalary() {return salary;}

    public void setTitle(String title) {this.title = title;}
    public void setCompany(String Company) {this.company = company;}
    public void setLocation(String location) {this.title = location;}
    public void setLink(String link) {this.link = link;}
    public void setPublishedDate(String publishedDate) {this.publishedDate = publishedDate;}
    public void setDescription(String description) {this.description = description;}
    public void setSalary(String salary) {this.salary = salary;}

    public boolean equals(Object o) {
        if (this == o) return true;
        if(o == null || getClass() != o.getClass() ) return false;
        Job job = (Job) o;
        return Objects.equals(link, job.link);
        /*In a lot of jobs sites, the link only take you to another link that is the job inscription.
          Thats why the line 44 check if the link are the same from another founded job*/
    }

    @Override
    public int hashCode() {
        return Objects.hash(link);
    }
    
    @Override
public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("TITLE: *").append(title).append("*\n");
    sb.append("COMPANY: ").append(company).append("\n");
    if (location != null && !location.isEmpty()) {
        sb.append("LOCATION: ").append(location).append("\n");
    }
    if (salary != null && !salary.isEmpty()) {
        sb.append("SALARY: ").append(salary).append("\n");
    }
    sb.append("DATE: ").append(publishedDate).append("\n");
    sb.append("LINK: ").append(link);
    return sb.toString();
}


}
