package resumeTemplates.database;

import resumeTemplates.User;


public interface Database {

    public boolean addUser(User user) throws Exception;

    public User getUser(String login) throws Exception;

    public void updatePassword(String login, String newPassword) throws Exception;

    public void init() throws Exception;
}
