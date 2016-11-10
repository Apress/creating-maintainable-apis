package rs.exproit.problem_reports.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.UriTemplate;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import rs.exproit.problem_reports.db.ProblemReportDB;
import rs.exproit.problem_reports.resource.ProblemReport;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(value = "/Reports")
public class ProblemReportsController {
    @RequestMapping(method = RequestMethod.GET)
    public Resources<ProblemReport> getReports(
            @RequestParam(value = "correlationId", required = false) String correlationId) {        
        List<Link> links = new ArrayList<Link>();
        links.add(linkTo(methodOn(ProblemReportsController.class)
                .getReports(correlationId)).withSelfRel());       
        links.add(linkTo(HomePageController.class)
                .withRel("http://xmlns.com/foaf/spec/#term_homepage"));
        links.add(createSearchLink());

        List<ProblemReport> reports = 
                correlationId != null ? ProblemReportDB.findAllProblemReports(correlationId) :
                                        ProblemReportDB.getAllProblemReports();
        
        return new Resources<>(toResources(reports), links);
    }
    
    private Link createSearchLink() {
        UriComponents uriComponents = UriComponentsBuilder.fromUri(
                linkTo(ProblemReportsController.class).toUri()).build();
        UriTemplate template = new UriTemplate(uriComponents.toUriString())
           .with("correlationId", TemplateVariable.VariableType.REQUEST_PARAM);
        
        return new Link(template, "search");
    }

    private List<ProblemReport> toResources(List<ProblemReport> reports) {
        for (ProblemReport report : reports) {
            if (!report.hasLink("self")) {
                report.add(linkTo(methodOn(ProblemReportController.class)
                        .getReport(report.getReportNumber())).withSelfRel());
            }
        }

        return reports;
    }
}