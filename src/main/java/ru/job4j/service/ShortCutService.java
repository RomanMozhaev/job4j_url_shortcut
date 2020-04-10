package ru.job4j.service;

import org.springframework.stereotype.Service;
import ru.job4j.domain.WebSite;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShortCutService {

    public String checkWebSite(String website) {

        //TODO here we need to check the website and remove all symbols
        // if website is not correctly entered return null
        // if website exists in the database, return null
        // otherwise return website without additional symbols.
        return website;
    }

    public String getUniqueLogin() {
        //TODO here we need to generate unique login and check that this login does
        // not exist in the database
        return "login";
    }

    public String getUniquePassword() {
        //TODO here we need to generate unique password.
        return "password";
    }

    public void register(WebSite site) {
        //TODO here the website must be saved to date base
    }

    public String addLink(String link) {
        //TODO here we need to check that link is owned of
        // the current authorized website, otherwise return ""
        // if the link exists in the database return existing code
        // otherwise generate a unique code for this link and check that this code
        // does not exist in the data base.
        // add link and code to database.
        return "specialCode";
    }

    public String getLink(String code) {
        //TODO find the link by code in the db.
        // increment website calls.
        // if link is not found return error page.
        return "http://www.google.com";
    }

    public List<JsonUrl> getStatistic() {
        //TODO create the list where first must be common statistic of the website.
        // and then statistic for each link
        List<JsonUrl> list = new ArrayList<>();
        list.add(new JsonUrl("job4j.ru", 100));
        list.add(new JsonUrl("job4j.ru/rororo", 100));
        return list;
    }

}
