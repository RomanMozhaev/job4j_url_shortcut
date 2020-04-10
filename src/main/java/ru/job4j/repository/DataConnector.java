package ru.job4j.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.job4j.domain.Link;
import ru.job4j.domain.WebSite;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * the data base connection combiner.
 */
@Component
public class DataConnector {

    /**
     * the web-site repository.
     */
    private final WebSiteRepository webSiteRepository;

    /**
     * the link repository.
     */
    private final LinkRepository linkRepository;

    @Autowired
    public DataConnector(final WebSiteRepository webSiteRepository,
                         final LinkRepository linkRepository) {
        this.webSiteRepository = webSiteRepository;
        this.linkRepository = linkRepository;
    }

    /**
     * it adds the website (domain and unique login and password) to the data base.
     *
     * @param webSite the website
     * @return true, if the website was added; otherwise false.
     */
    @Transactional
    public boolean addDomain(WebSite webSite) {
        boolean result = false;
        Optional<WebSite> dbSite = this.webSiteRepository.findByDomain(webSite.getDomain());
        if (dbSite.isEmpty()) {
            this.webSiteRepository.save(webSite);
            result = true;
        }
        return result;
    }

    /**
     * It saves the new link and returns the code.
     * If link already exists, the method returns the code of already saved link.
     *
     * @param link - the new link
     * @return - the unique code;
     */
    @Transactional
    public String addLink(Link link) {
        String code;
        Optional<Link> dbLink = this.linkRepository.findByUrl(link.getUrl());
        if (dbLink.isEmpty()) {
            Link savedLink = this.linkRepository.save(link);
            code = savedLink.getCode();
        } else {
            code = dbLink.get().getCode();
        }
        return code;
    }

    /**
     * it returns the link which matches to the code.
     * if the link exists in the data base and returns, the count is incremented.
     *
     * @param code the code.
     * @return
     */
    @Transactional
    public Optional<Link> findLinkByCode(String code) {
        Optional<Link> link = this.linkRepository.findByCode(code);
        link.ifPresent(value -> this.linkRepository.countIncrement(value.getUrl()));
        return link;
    }

    /**
     * it returns the list of links which relate to the website.
     *
     * @param site- the web site.
     * @return the link list.
     */
    public List<Link> getLinks(WebSite site) {
        return this.linkRepository.findAllByWebSite(site);
    }
}
