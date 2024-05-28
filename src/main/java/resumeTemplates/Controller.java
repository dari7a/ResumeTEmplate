package resumeTemplates;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Controller {
    Security security = new Security(Application.database);


    @RequestMapping(value = "/Resetpassword", method = RequestMethod.POST)
    public String resetPassword(@RequestParam(value = "login") String login) throws Exception {
        if (!Validation.validationSendEmail(login)) throw new Exception("Your email invalid");
        security.resetPassword(login);
        return login;
    }

    @RequestMapping(value = "/SignIn", method = RequestMethod.POST)
    public String signIn(@RequestParam String login, @RequestParam String password) throws Exception {
        User user = new User(login, password);
        return security.signIn(user);
    }

    @RequestMapping(value = "/SignUp", method = RequestMethod.POST)
    public String signUp(@RequestParam String login, @RequestParam String password, @RequestParam String checkPassword) throws Exception {
        User user = new User(login, password);
        return security.signUp(user, checkPassword);
    }
}


