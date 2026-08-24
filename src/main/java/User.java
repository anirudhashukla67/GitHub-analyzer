import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    public String login;
    public String name;
    public int followers;
    public int following;
    @JsonProperty("public_repos")
    public int publicRepos;
    
    
    
}
