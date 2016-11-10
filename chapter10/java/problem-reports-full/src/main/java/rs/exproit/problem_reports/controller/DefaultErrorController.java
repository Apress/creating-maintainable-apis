package rs.exproit.problem_reports.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import rs.exproit.problem_reports.resource.ProblemReport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

// Based on the sample published at https://gist.github.com/jonikarppinen/662c38fb57a23de61c8b.
@RestController
public class DefaultErrorController implements ErrorController {
    private static final String ERROR_PATH = "/error";
    private static final String NOT_APPLICABLE = "N/A";
    
    @Autowired
    private ErrorAttributes errorAttributes;

    @RequestMapping(value = ERROR_PATH, produces = "application/json")
    public ProblemReport errorHandler(HttpServletRequest request, HttpServletResponse response) {
        return createReport(request, response);
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    private ProblemReport createReport(HttpServletRequest request, HttpServletResponse response) {
        assert errorAttributes != null : "Error attributes should have been set by Spring";
        
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        Map<String,Object> errorFields = errorAttributes.getErrorAttributes(requestAttributes, false);

        return new ProblemReport(
                errorFields.getOrDefault("path", NOT_APPLICABLE).toString(), 
                errorFields.getOrDefault("error", NOT_APPLICABLE).toString(),
                errorFields.getOrDefault("message", NOT_APPLICABLE).toString(), 
                response.getStatus(), 
                errorFields.getOrDefault("exception", NOT_APPLICABLE).toString());
    }
}