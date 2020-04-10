package ru.job4j.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.job4j.domain.Link;
import ru.job4j.service.*;

import java.io.IOException;
import java.util.List;

/**
 * The controller for the application.
 */
@Controller
public class WebController {

    @Autowired
    public WebController(final ShortCutService service) {
        this.service = service;
    }

    /**
     * the service layer.
     */
    private final ShortCutService service;

    /**
     * The registration of the new web-site.
     * @param site - the new web-site.
     * @return - response entity with json in the body. The json contains
     * generated login, password and registration status(true if success; otherwise false).
     * @throws IOException
     */
    @PostMapping(value = "/registration")
    public ResponseEntity<JsonRegistration> registration(@RequestBody JsonSite site) throws IOException {
        JsonRegistration result = this.service.register(site);
        return new ResponseEntity<>(
                result,
                result.isRegistration() ? HttpStatus.OK : HttpStatus.BAD_REQUEST
        );
    }

    /**
     * the conversation of the link to the code
     * for accessing to the link form this server.
     * @param link - the new link
     * @return response entity with json in the body. the json contains the code.
     * @throws IOException
     */
    @PostMapping(value = "/convert")
    public ResponseEntity<JsonCode> convert(@RequestBody Link link) throws IOException {
        JsonCode code = this.service.addLink(link);
        return new ResponseEntity<>(
                code,
                !code.getCode().equals("") ? HttpStatus.OK : HttpStatus.BAD_REQUEST
        );
    }

    /**
     * the redirection to the link which matches to the code.
     * @param code - the unique code.
     * @return redirect view.
     */
    @GetMapping(value = "/redirect/{code}")
    public RedirectView redirect(@PathVariable String code) {
        String url = this.service.getLink(code);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(url);
        return redirectView;
    }

    /**
     * the statistic data about quantity of redirection to the site.
      * @return response entity with json in the body. the json contains
     * links and redirection quantities.
     * @throws IOException
     */
    @GetMapping(value = "/statistic")
    public ResponseEntity<List<JsonUrl>> statistic() throws IOException {
        List<JsonUrl> list = this.service.getStatistic();
        return new ResponseEntity<>(
                list,
                HttpStatus.OK);
    }
}
