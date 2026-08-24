import java.util.*;
public class Main {
    
    public static void displayuserinfo(User user){
        System.out.println("-********************** User information **********************");
        System.out.println("Usere login: "+user.login);
        System.out.println("name: "+user.name);
        System.out.println("followers: "+user.followers);
        System.out.println("no of repos"+user.publicRepos);

    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String username=sc.nextLine();
        username = username.trim();
        sc.close();
        if (username.isBlank()) {
        System.out.println("Username cannot be empty.");
        return;
        }
        try{User user = GitHubServices.getuserinfo(username);
        List<Repository> repositories = GitHubServices.getRepositories(username);
        displayuserinfo(user);
        RepositoryAnalysis.analysis(repositories);}
        catch(Exception e){
            System.out.println(e.getMessage());

        }

   
}
    }