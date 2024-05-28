import org.junit.Test;
import resumeTemplates.Validation;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ValidationTest {
    
    @Test
    public void isValidPasswordShouldReturnFalseIfSEvenLength() {
        assertEquals(false, Validation.isValidPassword("5468732"), "Password must be more seven symbols");
    }

    @Test
    public void isValidPasswordShouldReturnTrueIfValid() {
        assertEquals(true, Validation.isValidPassword("54687397"), "Password must be more seven symbols");
    }

    @Test
    public void isValidPasswordShouldReturnFalseIfLessSevenLength() {
        assertEquals(false, Validation.isValidPassword("546873"), "Password must be more seven symbols");
    }

    @Test
    public void validationSendEmailShouldReturnFalseIfInValid() {
        assertEquals(false, Validation.validationSendEmail("ivanova512@gmail"), "Your email should contain .");
    }

    @Test
    public void validationSendEmailShouldReturnFalseIfWithoutDogs() {
        assertEquals(false, Validation.validationSendEmail("ivanova512.com"), "Your email should contain @");
    }

    @Test
    public void validationSendEmailShouldReturnTrueIfValid() {
        assertEquals(true, Validation.validationSendEmail("ivanova512@gmail.com"), "Your email don't confirm");
    }
}

