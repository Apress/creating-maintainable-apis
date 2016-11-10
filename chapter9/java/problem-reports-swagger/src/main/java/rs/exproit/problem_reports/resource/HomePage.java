package rs.exproit.problem_reports.resource;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name = "HomePage")
public final class HomePage extends ResourceSupport {
    @JsonProperty @XmlAttribute
    private final String name = "Problem Reports service's home page";

    @JsonCreator
    public HomePage() { }

    public final String getName() {
        return name;
    }
}