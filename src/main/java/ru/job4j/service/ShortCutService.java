package ru.job4j.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.domain.WebSite;
import ru.job4j.domain.Link;
import ru.job4j.repository.DataConnector;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * the service layer.
 */
@Service
public class ShortCutService {

    /**
     * the pattern for checking urls.
     */
    private static final String URL_PATTERN = "^https?://[^/]+/([^/]+)/.*$";
    /**
     * the pattern for checking domains.
     */
    private static final String DOMAIN_PATTERN = "^((?!-)[A-Za-z0-9-]{1,63}(?<!-)\\.)+[A-Za-z]{2,6}$";

    /**
     * The link for redirection when the link which matches the code was not found.
     */
    @Value("${error.default.url}")
    private String defaultURL;


    /**
     * the data base connector.
     */
    private final DataConnector dataConnector;

    /**
     * the encoder.
     */
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public ShortCutService(DataConnector dataConnector,
                           BCryptPasswordEncoder encoder) {
        this.dataConnector = dataConnector;
        this.encoder = encoder;
    }

    /**
     * it checks the domain passes to the pattern or not.
     *
     * @param domain - the domain.
     * @return true, if passed; otherwise false.
     */
    private boolean checkDomainByPattern(String domain) {
        return Pattern.matches(DOMAIN_PATTERN, domain);
    }

    /**
     * it checks the url passes to the patterns or not.
     *
     * @param url    - the url.
     * @param domain - the domain.
     * @return true, if passed; otherwise false.
     */
    public boolean checkURLByPattern(String url, String domain) {
        String domainPattern = ".*" + domain + ".*";
        return (Pattern.matches(URL_PATTERN, url) && Pattern.matches(domainPattern, url));
    }

    /**
     * it registers the new site.
     *
     * @param site - the new web site.
     * @return the object for creating the json response with login and password.
     */
    public JsonRegistration register(JsonSite site) {
        JsonRegistration error = new JsonRegistration(false, "", "");
        String domain = site.getSite();
        if (!checkDomainByPattern(domain)) {
            return error;
        }
        String login = RandomStringUtils.randomAlphanumeric(10);
        String password = RandomStringUtils.randomAlphanumeric(10);
        String encodedPassword = encoder.encode(password);
        WebSite newWebSite = new WebSite(domain, login, encodedPassword);
        boolean registered = false;
        try {
            registered = this.dataConnector.addDomain(newWebSite);
        } catch (Exception ignored) {
        }
        if (!registered) {
            return error;
        }
        return new JsonRegistration(true, login, password);
    }

    /**
     * it adds the link.
     *
     * @param link the link.
     * @return the object for creating the json response with the unique code.
     */
    public JsonCode addLink(Link link) {
        JsonCode jsonCode = new JsonCode("");
        String url = link.getUrl();
        String domain = getDomain();
        if (!checkURLByPattern(url, domain)) {
            return jsonCode;
        }
        String code = RandomStringUtils.randomAlphanumeric(10);
        link.setCode(code);
        link.setWebSite(new WebSite(domain));
        try {
            String result = this.dataConnector.addLink(link);
            jsonCode.setCode(result);
        } catch (Exception ignored) {
        }
        return jsonCode;
    }

    /**
     * it returns the link which matches to the code.
     *
     * @param code the code.
     * @return the link for redirection.
     */
    public String getLink(String code) {
        Optional<Link> link = this.dataConnector.findLinkByCode(code);
        if (link.isEmpty()) {
            return this.defaultURL;
        }
        return link.get().getUrl();
    }

    /**
     * it returns the list with all urls and counts of redirections
     * for the authorized web-site.
     *
     * @return the list of objects for creating the json response.
     */
    public List<JsonUrl> getStatistic() {
        String domain = getDomain();
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

    /**
     * it returns the domain which is corresponded to the login/username.
     *
     * @return the domain.
     */
    private String getDomain() {
        WebSite userDetails = (WebSite) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return userDetails.getDomain();
    }
}
