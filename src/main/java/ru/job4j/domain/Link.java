package ru.job4j.domain;

import javax.persistence.*;

@Entity
@Table(name = "link")
public class Link {

    @Id
    private String url;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private int count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "website", foreignKey = @ForeignKey(name = "domain"))
    private WebSite webSite;

    public Link() {
    }

    public Link(String url, WebSite webSite) {
        this.url = url;
        this.webSite = webSite;
        this.count = 0;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public WebSite getWebSite() {
        return webSite;
    }

    public void setWebSite(WebSite webSite) {
        this.webSite = webSite;
    }
}
