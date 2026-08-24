import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

public class Main {
    public static List<Repository> getRepositories(String username) throws Exception{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("https://api.github.com/users/"+username+"/repos"))
                .GET()
                .build();
        HttpResponse<String> response =client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper= new ObjectMapper();
        List<Repository> repositories=mapper.readValue(response.body(), new TypeReference<List<Repository>>(){});
        return repositories; 
    }
    public static User getuserinfo(String username)throws Exception{
        HttpClient client=HttpClient.newHttpClient();
        HttpRequest request=HttpRequest.newBuilder()
        .uri(URI.create("https://api.github.com/users/"+username))
        .GET()
        .build();
        HttpResponse<String> response=client.send(request,HttpResponse.BodyHandlers.ofString()); 
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(response.body(),User.class);
        return user;     
    }
    public static void analysis(List<Repository> repositories)throws Exception{
        int maxStars=0;
        String maxRepo="";
        int totalStars=0;
        int totalRepositories=repositories.size();
        HashMap<String,Integer> hmap=new HashMap<>();
        for (Repository repo : repositories) {
            totalStars += repo.stars;
            if (repo.stars > maxStars) {
                maxStars = repo.stars;
                maxRepo = repo.name;
            }
            if (repo.language != null) {
                if (hmap.containsKey(repo.language)) {
                    hmap.put(repo.language, hmap.get(repo.language) + 1);
                } else {
                    hmap.put(repo.language, 1);
                }
            }
        }
        String maxLang="";
        int countMaxlang=0;
        for(Map.Entry<String,Integer> entry : hmap.entrySet()){
            if(entry.getValue()>countMaxlang){
                countMaxlang=entry.getValue();
                maxLang=entry.getKey();
            }
            
        }
        repositories.sort((a,b)->b.stars-a.stars);
        double avgStars=0;
        if(totalRepositories==0){
            avgStars=0;
        }else{
            avgStars=(double)totalStars/totalRepositories;
        }
        System.out.println("********************** Profile Analysis **********************");
        System.out.println(maxRepo+"is the most starred repo of the user with "+maxStars+" stars");
        System.out.println("The language most used by the user is "+maxLang+" and number of times it used is : "+countMaxlang);
        System.out.println("Numbe of Repositories are : "+totalRepositories+"\nTotal stars across all repositories : "+totalStars);
        System.out.println("Average stars : "+avgStars);
        System.out.println("\n*********************** Repository Information **********************");
        System.out.println("\nTOP 5 REPOSITORIES BY STARS\n");
        for(int i =0;i<Math.min(5, repositories.size());i++){ 
            Repository repo=repositories.get(i);
            System.out.println((i+1+"."+repo.name+"-->"+repo.stars));
        }

    }
    public static void displayuserinfo(User user){
        System.out.println("-********************** User information **********************");
        System.out.println("Usere login: "+user.login);
        System.out.println("name: "+user.name);
        System.out.println("followers: "+user.followers);
        System.out.println("no of repos"+user.publicRepos);

    }
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        String username=sc.nextLine();
        User user = getuserinfo(username);
        List<Repository> repositories = getRepositories(username);
        displayuserinfo(user);
        analysis(repositories);

   
}
    }