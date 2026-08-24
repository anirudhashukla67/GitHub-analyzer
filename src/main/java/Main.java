import java.util.*;

public class Main {

    public static void displayuserinfo(User user) {
        System.out.println(" ********************** User information **********************");
        System.out.println("Usere login: " + user.login);
        System.out.println("name: " + user.name);
        System.out.println("followers: " + user.followers);
        System.out.println("no of repos : " + user.publicRepos);

    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a username : ");
        String username=sc.nextLine();
        username = username.trim();
        if (username.isBlank()) {
        System.out.println("Username cannot be empty.");
        return;
        }
        try{User user = GitHubServices.getuserinfo(username);
        List<Repository> repositories = GitHubServices.getRepositories(username);
        boolean running = true;
        while(running){
        System.out.println("\n========== GitHub Analyzer ==========");
        System.out.println("1. Show User Information");
        System.out.println("2. Show Repository Analysis");
        System.out.println("3. Search Repository");
        System.out.println("4. Search Repositories by Language");
        System.out.println("5. Sort Repository : ");
        System.out.println("6. Exit\n");
        System.out.println("Enter choice : ");
        int choice = sc.nextInt();
        sc.nextLine();
            switch (choice) {
                case 1:
                    displayuserinfo(user);
                    break;
                case 2:
                    RepositoryAnalysis.analysis(repositories);
                    break;
                case 3:
                    System.out.println("Enter the repository name you want to search :");
                    String repoName =sc.nextLine();
                    repoName=repoName.trim();
                    Repository repo=RepositoryAnalysis.findRepository(repositories,repoName);
                    if(repo!=null){
                        System.out.println("Repository name : "+repo.name);
                        System.out.println("Repository description: "+repo.description);
                        System.out.println("Language used : "+repo.language);
                        System.out.println("stars : "+repo.stars);
                        System.out.println("forks: "+repo.forks);
                        
                    }else{
                        System.out.println("Repository not found !");
                    }
                    break;
                case 4:
                    System.out.println("Enter the repository language you want to search : ");
                    String repoLang=sc.nextLine();
                    repoLang=repoLang.trim();
                    List<Repository> langList=RepositoryAnalysis.findByLanguage(repositories, repoLang);
                    if(langList.isEmpty()){
                        System.out.println("None of the Repositories use the language : "+repoLang);
                    }else{
                    for(Repository repo1:langList){
                        System.out.println("Repository name : "+repo1.name);
                        System.out.println("Repository description: "+repo1.description);
                        System.out.println("stars : "+repo1.stars);
                        System.out.println("forks: "+repo1.forks);
                    }
                    }
                    break;
                case 5:
                    System.out.println("1. Sort Repositories by Stars ");
                    System.out.println("2. Sort Repositories by forks ");
                    System.out.println("3. Sort Repositories Alphabetically ");
                    System.out.print("choose a type : ");

                    int type=sc.nextInt();
                    sc.nextLine();
                    switch (type) {
                        case 1:
                            repositories.sort((a,b)->b.stars-a.stars);
                            for(int i =0;i<Math.min(5, repositories.size());i++){
                                Repository repo2 = repositories.get(i);
                                System.out.println((i+1)+". "+repo2.name+"-->"+repo2.stars);
                            }
                            break;
                        case 2:
                            repositories.sort((a,b)->b.forks-a.forks);
                            for(int i =0;i<Math.min(5, repositories.size());i++){
                                Repository repo2 = repositories.get(i);
                                System.out.println((i+1)+". "+repo2.name+"-->"+repo2.forks);
                            }
                            break;
                        case 3:
                            repositories.sort((a,b)->a.name.compareToIgnoreCase(b.name));
                            for(int i =0;i<repositories.size();i++){
                                Repository repo2 = repositories.get(i);
                                System.out.println(((i+1)+". "+repo2.name));
                            }
                            break;        
                    }
                    break;
                case 6:
                    running=false; 
                    break;   
                default:
                    break;
            }
        }
        }
        catch(Exception e){
            System.out.println(e.getMessage());

        }
    }
}
        

