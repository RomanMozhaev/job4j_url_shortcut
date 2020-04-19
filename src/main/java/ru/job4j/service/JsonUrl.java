package ru.job4j.service;

/**
 * the class for creating instances for creating json object for response,
 * when the application returns the list of urls and counts of rediractions.
 */
public class JsonUrl {

    private String url;

    private int total;

    public JsonUrl() {
    }

    public JsonUrl(String url, int total) {
        this.url = url;
        this.total = total;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
