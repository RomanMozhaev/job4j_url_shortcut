package ru.job4j.service;

/**
 * the class for creating instances for creating json object for response,
 * when the application returns the code matched to the url.
 */
public class JsonCode {

    private String code;

    public JsonCode() {
    }

    public JsonCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
