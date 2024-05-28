package resumeTemplates;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import resumeTemplates.database.Database;
import resumeTemplates.database.MySqlImpl;


@SpringBootApplication
public class Application {
    public static Database database;


    public static void main(String[] args) throws Exception {
        database = new MySqlImpl();
       /* database = new MongoImpl();*/
        database.init();
        SpringApplication.run(Application.class, args);
    }
}
