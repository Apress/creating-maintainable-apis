package rs.exproit.problem_reports.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import rs.exproit.problem_reports.resource.HomePage;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping("/")
public class HomePageController {
    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<HomePage> homePage() {
        HomePage homePage = new HomePage();
        homePage.add(linkTo(methodOn(HomePageController.class).homePage()).withSelfRel());
        homePage.add(linkTo(methodOn(AboutPageController.class).aboutPage()).withRel("about"));
        homePage.add(linkTo(ProblemReportsController.class).withRel("contents"));

        return new ResponseEntity<HomePage>(homePage, HttpStatus.OK);
    }
}