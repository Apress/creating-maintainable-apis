package rs.exproit.problem_reports.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import rs.exproit.problem_reports.resource.ProblemReport;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public final class SearchProblemReports {
    private static final String USERNAME = "scott";
    private static final String PASSWORD = "tiger";
    private static HttpHeaders httpHeaders = new HttpHeaders();
    
    @LocalServerPort
    private int port;
    
    @Autowired
    private ServletContext servletContext;
    
    private Traverson traverson;
    ParameterizedTypeReference<Resources<ProblemReport>> collectionReference = 
            new ParameterizedTypeReference<Resources<ProblemReport>>() {};    

    @BeforeClass
    public static void setupHTTPHeaders() throws UnsupportedEncodingException {
        String authHash = Base64.getEncoder().encodeToString((USERNAME + ":" + PASSWORD).getBytes());
        httpHeaders.add("Authorization", "Basic " + authHash);
    }
            
    @Before
    public void setupTraversonClient() throws URISyntaxException {
        traverson = new Traverson(
                new URI("http://localhost:" + port + servletContext.getContextPath()), 
                MediaTypes.HAL_JSON);
    }

    private Resources<ProblemReport> followTheSearchLink(String correlationId) {
        Map<String, Object> params = new HashMap<>();
        params.put("correlationId", correlationId);
        
        return traverson.follow("contents").withHeaders(httpHeaders)
                        .follow("search").withTemplateParameters(params).toObject(collectionReference);
    }
    
    @Test
    public void searchForTheReportWithTheGivenCorrelationIdAndVerifyIt() throws Exception {
        Resources<ProblemReport> resources = followTheSearchLink("T1");
        assertThat(resources).isNotNull();
        
        Collection<ProblemReport> reports = resources.getContent();
        assertThat(reports).isNotNull();
        assertThat(reports).hasSize(1);
        
        ProblemReport report = reports.iterator().next();
        assertThat(report).isNotNull();
        assertThat(report.getReportNumber()).isEqualTo("1");
        assertThat(report.getType()).isEqualTo("http://example.org/errors/out-of-memory");
        assertThat(report.getStatus()).isEqualTo(500);
        assertThat(report.getTitle()).isEqualTo("Out of memory error");
        
        Link selfLink = report.getId();
        assertThat(selfLink).isNotNull();
        assertThat(selfLink.getHref()).endsWith("/" + report.getReportNumber());
    }
}