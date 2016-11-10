package rs.exproit.problem_reports.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
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
    public Resources<Resource<ProblemReport>> getReports(
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

    @RequestMapping(path = "/favorite", method = RequestMethod.GET)
    DeferredResult<Resources<Resource<ProblemReport>>> getFavoriteReports() {
        final DeferredResult<Resources<Resource<ProblemReport>>> result = new DeferredResult<>();
        final RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();

        // Simulate that we wait for the result using a separate thread.
        new Thread(new Runnable() {
            @Override
            public void run () {
                RequestContextHolder.setRequestAttributes(requestAttributes);
                
                List<Link> links = new ArrayList<Link>();
                links.add(linkTo(methodOn(ProblemReportsController.class)
                        .getFavoriteReports()).withSelfRel());       
                links.add(linkTo(HomePageController.class)
                        .withRel("http://xmlns.com/foaf/spec/#term_homepage"));

                List<Resource<ProblemReport>> favoriteReports = 
                        toResources(ProblemReportDB.getFavoriteProblemReports());                
                result.setResult(new Resources<>(favoriteReports, links));
            }
        }).start();
            
        return result;
    }    
    
    private Link createSearchLink() {
        UriComponents uriComponents = UriComponentsBuilder.fromUri(
                linkTo(ProblemReportsController.class).toUri()).build();
        UriTemplate template = new UriTemplate(uriComponents.toUriString())
           .with("correlationId", TemplateVariable.VariableType.REQUEST_PARAM);
        
        return new Link(template, "search");
    }

    private List<Resource<ProblemReport>> toResources(List<ProblemReport> reports) {
        List<Resource<ProblemReport>> resources = new ArrayList<>(reports.size());

        for (ProblemReport report : reports) {
            Resource<ProblemReport> resource = new Resource<>(report);
            resource.add(linkTo(methodOn(ProblemReportController.class)
                    .getReport(report.getReportNumber())).withSelfRel());
            resources.add(resource);
        }

        return resources;
    }
}