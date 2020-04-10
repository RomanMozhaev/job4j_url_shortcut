package ru.job4j.service;

/**
 * the class for creating instances for creating json object,
 * when the application accepts the new site for registration.
 */
public class JsonSite {

    private String site;

    public JsonSite() {
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
