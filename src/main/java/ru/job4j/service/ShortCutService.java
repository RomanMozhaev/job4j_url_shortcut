package ru.job4j.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.job4j.domain.WebSite;
import ru.job4j.domain.Link;
import ru.job4j.repository.DataConnector;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class ShortCutService {

    private static final String URL_PATTERN = "^https?://[^/]+/([^/]+)/.*$";
    private static final String DOMAIN_PATTERN = "^((?!-)[A-Za-z0-9-]{1,63}(?<!-)\\.)+[A-Za-z]{2,6}$";

    @Value("${error.default.url}")
    private String defaultURL;


    private final DataConnector dataConnector;

    @Autowired
    public ShortCutService(DataConnector dataConnector) {
        this.dataConnector = dataConnector;
    }

    private boolean checkDomainByPattern(String domain) {
        return Pattern.matches(DOMAIN_PATTERN, domain);
    }

    public boolean checkURLByPattern(String url, String domain) {
        String domainPattern = ".*" + domain + ".*";
        boolean e = Pattern.matches(URL_PATTERN, url);
        boolean r = Pattern.matches(domainPattern, url);
        return (Pattern.matches(URL_PATTERN, url) && Pattern.matches(domainPattern, url));
    }

    public JsonRegistration register(String domain) {
        JsonRegistration jsonRegistration;
        boolean registered = false;
        String login = "";
        String password = "";
        if (checkDomainByPattern(domain)) {
            login = RandomStringUtils.randomAlphanumeric(10);
            password = RandomStringUtils.randomAlphanumeric(10);
            WebSite newWebSite = new WebSite(domain, login, password);
            registered = this.dataConnector.addDomain(newWebSite);
        }
        if (registered) {
            jsonRegistration = new JsonRegistration(true, login, password);
        } else {
            jsonRegistration = new JsonRegistration(false, "", "");
        }
        return jsonRegistration;

    }

    public String addLink(Link link) {
        String result = "";
        String url = link.getUrl();
        String domain = link.getWebSite().getDomain();
        if (checkURLByPattern(url, domain)) {
            String code = RandomStringUtils.randomAlphanumeric(10);
            link.setCode(code);
            result = this.dataConnector.addLink(link);
        }
        return result;
    }

    public String getLink(String code) {
        String result;
        Link link = this.dataConnector.findLinkByCode(code);
        if (link == null) {
            result = this.defaultURL;
        } else {
            result = link.getUrl();
        }
        return result;
    }

    public List<JsonUrl> getStatistic(String domain) {
        List<JsonUrl> jsonUrlList = new ArrayList<>();
        jsonUrlList.add(new JsonUrl(domain, 0));
        int domainCount = 0;
        List<Link> links = this.dataConnector.getLinks(new WebSite(domain));
        for (Link link : links) {
            int count = link.getCount();
            domainCount += count;
            jsonUrlList.add(new JsonUrl(link.getUrl(), count));
        }
        jsonUrlList.get(0).setTotal(domainCount);
        return jsonUrlList;
    }

}
