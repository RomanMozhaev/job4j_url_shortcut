package ru.job4j.web;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.job4j.domain.WebSite;
import ru.job4j.service.JsonCode;
import ru.job4j.service.JsonRegistration;
import ru.job4j.service.JsonUrl;
import ru.job4j.service.ShortCutService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/shortcut")
public class WebController {

    @Autowired
    public WebController(final ShortCutService service) {
        this.service = service;
    }

    private final ShortCutService service;

    private final ObjectMapper mapper = new ObjectMapper();

    @PostMapping(value = "/registration", consumes = "application/json", produces = "application/json")
    public @ResponseBody
    String registration(@RequestBody String jsonString) throws IOException {
        Map<String, String> fields = this.mapper.readValue(jsonString, Map.class);
        String domain = this.service.checkWebSite(fields.get("site"));
        boolean registration = false;
        String login = "";
        String password = "";
        if (domain != null) {
            login = this.service.getUniqueLogin();
            password = this.service.getUniquePassword();
            this.service.register(new WebSite(domain, login, password));
            registration = true;
        }
        String resultJSON = this.mapper.writeValueAsString(new JsonRegistration(registration, login, password));
        return resultJSON;
    }

    @PostMapping(value = "/convert", consumes = "application/json", produces = "application/json")
    public @ResponseBody
    String convert(@RequestBody String jsonString) throws IOException {
        //TODO here we need somehow to detect after authorization what kind of
        // website is authorized and send it to service layer with url
        Map<String, String> fields = this.mapper.readValue(jsonString, Map.class);
        String code = this.service.addLink(fields.get("url"));
        String resultJSON = this.mapper.writeValueAsString(new JsonCode(code));
        return resultJSON;
    }

    @GetMapping(value = "/redirect/{code}")
    public RedirectView redirect(@PathVariable String code) {
        String url = this.service.getLink(code);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(url);
        return redirectView;
    }

    @GetMapping(value = "/statistic", produces = "application/json")
    public @ResponseBody String statistic() throws IOException {
        //TODO somehow get the website which is authorized.

        List<JsonUrl> list = this.service.getStatistic();
        String resultJSON = this.mapper.writeValueAsString(list);
        return resultJSON;
    }
}
