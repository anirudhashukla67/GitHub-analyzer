import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositoryAnalysis {
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
    
}
