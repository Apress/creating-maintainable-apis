package rs.exproit.problem_reports.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import rs.exproit.problem_reports.resource.AboutPage;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class AboutPageController {
    @RequestMapping(path = "/About", method = RequestMethod.GET)
    public HttpEntity<AboutPage> aboutPage() {
        AboutPage aboutPage = new AboutPage();
        aboutPage.add(linkTo(methodOn(AboutPageController.class).aboutPage()).withSelfRel());
        aboutPage.add(linkTo(HomePageController.class)
                .withRel("http://xmlns.com/foaf/spec/#term_homepage"));

        return new ResponseEntity<AboutPage>(aboutPage, HttpStatus.OK);
    }
}