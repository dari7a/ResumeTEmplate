package resumeTemplates.database;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClients;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import resumeTemplates.User;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;


public class MongoImpl implements Database {

    private static final Log log = LogFactory.getLog(MongoImpl.class);
    static MongoOperations mongoOps = new MongoTemplate(MongoClients.create(), "database");


    @Override
    public boolean addUser(User user) {
        log.info("try to add user" + user);
        try {
            mongoOps.insert(user);

        } catch (Exception ex) {
            log.error(ex.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public User getUser(String login) throws MongoException {
        User user = mongoOps.findOne(query(where("login").is(login)), User.class);
        log.info("try to get user" + user);
        return user;
    }

    @Override
    public void updatePassword(String login, String newPassword) throws MongoException {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(login));
        Update update = new Update();
        update.set("password", newPassword);
        mongoOps.findAndModify(
                query, update,
                new FindAndModifyOptions().returnNew(true), User.class);
        log.info("try update password" + newPassword);
    }

    @Override
    public void init() throws MongoException {

    }

}


