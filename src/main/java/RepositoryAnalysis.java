import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositoryAnalysis {
    public static List<Repository> findByLanguage(List<Repository> repositories, String lang){
        List<Repository> langList=new ArrayList<>();
        for(Repository repo:repositories){
            if(repo.language!=null && repo.language.equalsIgnoreCase(lang)){
                langList.add(repo);
            }
        }
        return langList;
    }
    public static Repository findRepository(List<Repository> repositories, String repoName){
        for(Repository repo:repositories){
            if(repo.name.equalsIgnoreCase(repoName)){
                return repo;
            }
        }
        return null;
    }
    public static void analysis(List<Repository> repositories){
        int maxStars=0;
        int maxFork=0;
        String maxForkRepo="";
        String maxRepo="";
        int totalStars=0;
        int totalRepositories=repositories.size();
        HashMap<String,Integer> hmap=new HashMap<>();
        for (Repository repo : repositories) {
            if(repo.forks>maxFork){
                maxFork=repo.forks;
                maxForkRepo=repo.name;
            }
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
        int countMaxLang=0;
        for(Map.Entry<String,Integer> entry : hmap.entrySet()){
            if(entry.getValue()>countMaxLang){
                countMaxLang=entry.getValue();
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
        double highestForkToStar=0;
        String repoHighestForkToStarRatio="";
        
        System.out.println("********************** Profile Analysis **********************");
        System.out.println(maxRepo+"is the most starred repo of the user with "+maxStars+" stars");
        System.out.println(maxForkRepo+"is the most forked repo of the user with "+maxFork+" forks");
        System.out.println("The language most used by the user is "+maxLang+" and number of times it used is : "+countMaxLang);
        System.out.println("Numbe of Repositories are : "+totalRepositories+"\nTotal stars across all repositories : "+totalStars);
        System.out.println("Average stars : "+avgStars);
        System.out.println("\n*********************** Repository Information **********************");
        System.out.println("\nTOP 5 REPOSITORIES BY STARS\n");
        for(int i =0;i<Math.min(5, repositories.size());i++){ 
            Repository repo=repositories.get(i);
            
            if(repo.stars!=0){
                if((double)repo.forks/repo.stars>highestForkToStar){
                    highestForkToStar=(double)repo.forks/repo.stars;
                    repoHighestForkToStarRatio=repo.name;
                }
                System.out.println((i+1+"."+repo.name+"-->"+repo.stars));
                System.out.println("Fork to Stars ratio : "+(double)repo.forks/repo.stars);
            }else{
                System.out.println("Fork to Stars ratio : (N/A) ");
            }
            
        }
        System.out.println("\nRepository with the highest Fork to Star ratio : "+highestForkToStar+ " is : "+repoHighestForkToStarRatio);        

    }
    
}
