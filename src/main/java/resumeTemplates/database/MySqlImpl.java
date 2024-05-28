package resumeTemplates.database;

import resumeTemplates.User;

import java.sql.*;


public class MySqlImpl implements Database {

    private Statement getStatement() throws SQLException {
        Statement statement = getNewConnection().createStatement();
        return statement;
    }

    public static Connection getNewConnection() {
        String url = "jdbc:mysql://localhost:3306/users?useUnicode=true&serverTimezone=UTC";
        String user = "root";
        String password = "Veselova777";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public boolean addUser(User user) throws SQLException {
        String formatString = "INSERT INTO USER VALUES ('%s', '%s')";
        String userEntryQuery = String.format(formatString, user.getLogin(), user.getPassword());
        try {
            getStatement().executeUpdate(userEntryQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    @Override
    public User getUser(String login) throws SQLException {
        String formatString = "SELECT * FROM USER WHERE login =\"%s\" ";
        String query = String.format(formatString, login);
        ResultSet rs = getStatement().executeQuery(query);
        rs.next();
        String password = rs.getString("password");
        return new User(login, password);
    }


    @Override
    public void updatePassword(String login, String newPassword) throws SQLException {
        String formatString = "UPDATE USER SET password= \"%s\" WHERE login =\"%s\" ";
        String query = String.format(formatString, newPassword, login);
        getStatement().executeUpdate(query);
    }


    @Override
    public void init() throws SQLException {
        String userTableQuery = "CREATE TABLE IF NOT EXISTS USER " + "(email TEXT, password TEXT)";
        getStatement().executeUpdate(userTableQuery);
    }
}
