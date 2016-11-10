package rs.exproit.problem_reports.resource;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name = "problem")
@Relation(collectionRelation = "items")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public final class ProblemReport extends ResourceSupport {
    @JsonProperty @XmlAttribute
    private String reportNumber;
    
    @JsonProperty @XmlAttribute
    private String type;

    @JsonProperty @XmlAttribute
    private String title;

    @JsonProperty @XmlAttribute
    private Integer status;

    @JsonProperty @XmlAttribute
    private String correlationId;

    @JsonCreator
    public ProblemReport(String id, String type, String title, Integer status, String correlationId) {
        this.reportNumber = id;
        this.type = type;
        this.title = title;
        this.status = status;
        this.correlationId = correlationId;
    }
    
    public ProblemReport() { }

    public final String getType() {
        return type;
    }

    public final String getTitle() {
        return title;
    }

    public final Integer getStatus() {
        return status;
    }

    public final String getCorrelationId() {
        return correlationId;
    }

    public final String getReportNumber() {
        return reportNumber;
    }
}