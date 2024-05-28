package resumeTemplates;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import resumeTemplates.database.Database;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


public class Security {

    private static final Log log = LogFactory.getLog(Security.class);
    Database database;

    public Security(Database database) {
        this.database = database;
    }

    private Security() {

    }

    public enum PasswordValidation {
        INVALID,
        NOTMACH,
        VALID
    }

    public enum SignInValidation {
        NOTFOUND,
        NOTSIGIN,
        SIGNIN
    }


    private String addUserDatabase(User user) throws Exception {
        if (database.addUser(user)) {
            return "You have register ";
        } else {
            throw new Exception("this email is exist");
        }
    }

    public String signUp(User user, String checkPassword) throws Exception {
        PasswordValidation passwordValidation = PasswordValidation.VALID;
        String password = user.getPassword();
        String result = "";
        if (!user.getPassword().equals(checkPassword)) {
            passwordValidation = PasswordValidation.NOTMACH;
        }
        switch (passwordValidation) {
            case INVALID:
                result = "Your password is incorrect";
                break;
            case NOTMACH:
                result = "Password don't confirm checkPassword";
                break;
            case VALID:
                result = addUserDatabase(user);
                break;
        }
        return result;
    }

    public User getUserFromDB(String login) throws Exception {
        return database.getUser(login);
    }

    public String signIn(User user) throws Exception {
        User userDB = database.getUser(user.getLogin());
        return signInValidation(user, userDB);
    }

    public String signInValidation(User user, User userDB) throws Exception {
        SignInValidation signInValidation = SignInValidation.SIGNIN;
        String result = "";
        if (userDB == (null)) {
            signInValidation = SignInValidation.NOTFOUND;
        } else if (!user.getPassword().equals(userDB.getPassword())) {
            signInValidation = SignInValidation.NOTSIGIN;

        }
        switch (signInValidation) {
            case NOTFOUND:
                log.error("This user nor found");
                throw new Exception("This user nor found");
            case NOTSIGIN:
                log.error("you don't sign in");
                throw new Exception("You don't sign in");
            case SIGNIN:
                result = "You sign in";

        }
        return result;
    }

    public void resetPassword(String login) throws Exception {
        String newPassword = generatePassword();
        sendEmail(login, newPassword);
        database.updatePassword(login, newPassword);

    }

    public static String generatePassword() {
        StringBuilder sb = new StringBuilder(7);
        for (int i = 0; i < 7; i++) {
            int index = (int) (10 * Math.random());
            sb.append(index);
        }
        return sb.toString();
    }

    public void sendEmail(String toEmail, String newPassword) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("annaiv2588@gmail.com", "Anna2588");

            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("annaiv2588@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            String subject = "Reset password";
            String text = "You send request on reset password.Your new password is  " + newPassword;
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);
        } catch (MessagingException e) {
            log.error(e.getMessage());
            throw new RuntimeException("Your password didn't send");
        }
    }
}





