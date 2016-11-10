package rs.exproit.problem_reports.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import rs.exproit.problem_reports.db.ProblemReportDB;
import rs.exproit.problem_reports.resource.ProblemReport;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping("/Reports/{reportNumber}")
public class ProblemReportController {
    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<ProblemReport> getReport(@PathVariable String reportNumber) {
        ProblemReport report = ProblemReportDB.findProblemReport(reportNumber);
        if (report == null) {
            return new ResponseEntity<ProblemReport>(HttpStatus.NOT_FOUND);
        } else {
            report.add(linkTo(methodOn(ProblemReportController.class)
                    .getReport(reportNumber)).withSelfRel());
            report.add(linkTo(methodOn(ProblemReportsController.class)
                    .getReports(report.getCorrelationId())).withRel("related"));
            report.add(linkTo(methodOn(ProblemReportController.class)
                    .getReport(reportNumber)).withRel("edit"));
            report.add(linkTo(ProblemReportsController.class).withRel("collection"));

            return new ResponseEntity<ProblemReport>(report, HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public HttpEntity<ProblemReport> deleteReport(@PathVariable String reportNumber) {
        ProblemReportDB.deleteProblemReport(reportNumber);
        return new ResponseEntity<ProblemReport>(HttpStatus.NO_CONTENT);
    }
}