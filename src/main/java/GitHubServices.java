import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GitHubServices{
    static HttpClient client =HttpClient.newHttpClient();
    static ObjectMapper mapper= new ObjectMapper();
    public static HttpResponse<String> makeRequest(String URL)throws Exception{
        HttpRequest request=HttpRequest.newBuilder()
        .uri(URI.create(URL))
        .GET()
        .build();
        HttpResponse<String> response =client.send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        if(status!=200){
            throw new Exception("GitHub API request failed. Styatus code : "+status);
        }
        return response;
        // switch (status) {
        //     case 200:
        //         return response;
        //     case 404 :
        //         throw new Exception("user not found ");
                
        //     case 403:
        //         throw new Exception("Access forbidden or API rate limit reached ");
                
        //     default:
        //         throw new Exception("GitHub API request failed . Status code : "+status);
                
        // }
        
    }
    public static List<Repository> getRepositories(String username) throws Exception{
        String URL="https://api.github.com/users/"+username+"/repos";
        HttpResponse<String> response =makeRequest(URL);
        List<Repository> repositories=mapper.readValue(response.body(), new TypeReference<List<Repository>>(){});
        return repositories; 
    }
    public static User getuserinfo(String username)throws Exception{
        String URL = "https://api.github.com/users/"+username;
        HttpResponse<String> response=makeRequest(URL);
        User user = mapper.readValue(response.body(),User.class);
        return user;     
    }

}