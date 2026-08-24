import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Repository{
    public String name;
    public String description;
    public String language;
    @JsonProperty("stargazers_count")
    public int stars;
    @JsonProperty("forks_count")
    public int forks;

}