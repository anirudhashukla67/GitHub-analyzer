import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.io.IOException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GitHubServices{
    static HttpClient client =HttpClient.newHttpClient();
    static ObjectMapper mapper= new ObjectMapper();

    public static HttpResponse<String> makeRequest(String URL)throws GitHubApiException,IOException,InterruptedException{
        HttpRequest request=HttpRequest.newBuilder()
        .uri(URI.create(URL))
        .GET()
        .build();
        HttpResponse<String> response =client.send(request, HttpResponse.BodyHandlers.ofString());
        Optional<String> remaining = response.headers().firstValue("X-RateLimit-Remaining");
        if(remaining.isPresent()){
            int remainingRequest=Integer.parseInt(remaining.get());
            if(remainingRequest<5){
                System.out.println("Request remaining : "+remainingRequest);
                System.out.println("API rate limit remaininng less than 5");
            }if(remainingRequest==0){
                throw new GitHubApiException("API request limit reached");
            }
        }
        int status = response.statusCode();
        if(status!=200){
            throw new GitHubApiException("GitHub API request failed. Status code : "+status);
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
    public static List<Repository> getRepositories(String username) throws GitHubApiException,IOException,InterruptedException{
        List<Repository> allRepositories=new ArrayList<>();
        int page =1;
        while(true){
            String URL="https://api.github.com/users/"+username+"/repos?page="+page+"&per_page=100";
            HttpResponse<String> response = makeRequest(URL);
            List<Repository> pageRepositories=mapper.readValue(response.body(), new TypeReference<List<Repository>>() {});
            allRepositories.addAll(pageRepositories);
            if(pageRepositories.size()<100){
                break;
            }
            page++;
        }
        return allRepositories;
    }
    public static User getuserinfo(String username)throws GitHubApiException,IOException,InterruptedException{
        String URL = "https://api.github.com/users/"+username;
        HttpResponse<String> response=makeRequest(URL);
        User user = mapper.readValue(response.body(),User.class);
        return user;     
    }

}