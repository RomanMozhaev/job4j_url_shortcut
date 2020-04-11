package ru.job4j.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "domain")
public class WebSite {

    @Id
    private String domain;

    @Column(unique = true, nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "webSite", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Link> links;

    public WebSite() {
    }

    public WebSite(String domain) {
        this.domain = domain;
    }

    public WebSite(String domain, String login, String password) {
        this.domain = domain;
        this.login = login;
        this.password = password;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String name) {
        this.domain = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Link> getLinks() {
        return links;
    }

    public void setLinks(Set<Link> links) {
        this.links = links;
    }
}
