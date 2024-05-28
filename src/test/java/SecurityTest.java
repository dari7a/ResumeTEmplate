import org.junit.Test;
import resumeTemplates.Security;
import resumeTemplates.User;
import resumeTemplates.database.MongoImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class SecurityTest {

    Security security = new Security(new MongoImpl());

    @Test
    public void signInShouldReturnYouSignIn() throws Exception {
        User user = new User("ivanova512@gmail.com", "Ivanova512");
        assertEquals("You sign in", security.signIn(user), "you sign in");
    }

    @Test
    public void signInShouldReturnUserNotFound() throws Exception {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            throw new IllegalArgumentException("User not found");
        });
        assertEquals("User not found", exception.getMessage());
    }


    public void signInShouldReturnYouNotSignIn() throws Exception {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            throw new IllegalArgumentException("You not sign in");
        });
        assertEquals("You not sign in", exception.getMessage());
    }


    @Test
    public void signUpShouldReturnYouHaveRegister() throws Exception {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            throw new IllegalArgumentException("You have register");
        });
        assertEquals("You have register", exception.getMessage());
    }
}
