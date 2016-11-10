package rs.exproit.problem_reports.resource;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name = "AboutPage")
public final class AboutPage extends ResourceSupport {
    @JsonProperty @XmlAttribute
    private final String name = "About the Problem Reports service";

    @JsonProperty @XmlAttribute
    private final String about = "Stores problem reports registered in a system";

    @JsonProperty @XmlAttribute
    private final String text = "The example micro-service for chapter 9.";

    @JsonCreator
    public AboutPage() { }

    public final String getName() {
        return name;
    }

    public final String getAbout() {
        return about;
    }

    public final String getText() {
        return text;
    }
}