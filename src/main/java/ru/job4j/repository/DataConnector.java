package ru.job4j.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.job4j.domain.Link;
import ru.job4j.domain.WebSite;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataConnector {

    private final WebSiteRepository webSiteRepository;

    private final LinkRepository linkRepository;

    @Autowired
    public DataConnector(final WebSiteRepository webSiteRepository,
                         final LinkRepository linkRepository) {
        this.webSiteRepository = webSiteRepository;
        this.linkRepository = linkRepository;
    }

    @Transactional
    public boolean addDomain(WebSite webSite) {
        boolean result = false;
        WebSite dbSite = this.webSiteRepository.findByDomain(webSite.getDomain());
        if (dbSite == null) {
            this.webSiteRepository.save(webSite);
            result = true;
        }
        return result;
    }

    /**
     * It saves the new link and returns the code.
     * If link already exists, the method returns the code of already saved link.
     * @param link - the new link
     * @return - the unique code;
     */
    @Transactional
    public String addLink(Link link) {
        String code;
        Link dbLink = this.linkRepository.findByUrl(link.getUrl());

        if (dbLink == null) {
            Link savedLink = this.linkRepository.save(link);
            code = savedLink.getCode();
        } else {
            code = dbLink.getCode();
        }
        return code;
    }

    @Transactional
    public Link findLinkByCode(String code) {
        Link link = this.linkRepository.findByCode(code);
        if (link != null) {
            link.setCount(link.getCount() + 1);
        }
        return link;
    }

    public List<Link> getLinks(WebSite site) {
        List<Link> links = this.linkRepository.findAllByWebSite(site);
        return links;
    }
}
