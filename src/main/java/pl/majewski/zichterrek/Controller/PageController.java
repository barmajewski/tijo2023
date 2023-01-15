package pl.majewski.zichterrek.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.majewski.zichterrek.Forms.RegisterForm;
import pl.majewski.zichterrek.Model.User;
import pl.majewski.zichterrek.Service.EmailService;
import pl.majewski.zichterrek.Service.UserService;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
public class PageController {
    private final UserService userService;
    private final EmailService emailService;

    public PageController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping("/")
    public ModelAndView getLoginView(){
        ModelAndView model = new ModelAndView("index");
        Optional<User> loggedUser = userService.findLogged();
        return model;
    }

    @GetMapping("/panel")
    public String getPanelView(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            Optional<User> user = userService.findLogged();
            if (user.isPresent()) {
                for (String authority : user.get().getAuthorities()) {
                    if (authority.equals("ROLE_ADMIN")) {
                        return "redirect:/panel/admin";
                    } else if (authority.equals("ROLE_USER")) {
                        return "redirect:/panel/uzytkownik";
                    } else {
                        return "redirect:/";
                    }
                }
            } else {
                new SecurityContextLogoutHandler().logout(request, response, auth);
            }
        }
        return "redirect:/";
    }

    @PostMapping("/panel")
    public String postPanelView(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Optional<User> user = userService.findLogged();
            if (user.isPresent()) {
                for (String authority : user.get().getAuthorities()) {
                    if (authority.equals("ROLE_ADMIN")) {
                        return "redirect:/panel/admin";
                    } else if (authority.equals("ROLE_USER")) {
                        return "redirect:/panel/uzytkownik";
                    } else {
                        return "redirect:/";
                    }
                }
            } else {
                new SecurityContextLogoutHandler().logout(request, response, auth);
            }
        }
        return "redirect:/";
    }

    @GetMapping ("/dolacz")
    public ModelAndView getRegisterView(){
        ModelAndView model = new ModelAndView("register");
        model.addObject("registerForm", new RegisterForm());
        return model;
    }


    @PostMapping("/rejestracja")
    public String createNewUser(@ModelAttribute RegisterForm registerForm) throws MessagingException {
        User user = userService.createNewUser(registerForm);
        emailService.sendMessage(registerForm.getEmail(), "Utworzono nowe konto w systemie Zichtterek",
                "Witaj "+registerForm.getFirstName()+"!\n\n"+
                        "Utworzono dla Ciebie nowe konto w serwisie GunMarket. W celu zalogowania się się wykorzystaj podane przez Ciebie podczas" +
                        "rejestracji dane: \n"+
                        registerForm.getEmail()+"\n"+
                        registerForm.getPassword()+"\n\n"+
                        "Link aktywacyjny konto : http://localhost:8080/activate-account/"+user.getActivationCode()+"\n\n"+
                        "Dziękujemy za dokłaczenie do naszego serwisu!");
        return "redirect:/?success";
    }

    @GetMapping("/wyloguj")
    public String getLogoutView(@RequestParam(required = false) String emailChanged, HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        new SecurityContextLogoutHandler().logout(request, response, auth);
        if(emailChanged == null){
            return "redirect:";
        }else{
            return "redirect:/zaloguj?"+emailChanged;
        }
    }

    @GetMapping("/activate-account/{activationCode}")
    public String activateUserAccount(@PathVariable String activationCode){
        try {
            User user = userService.activeteUser(activationCode);
            return "redirect:/activate-account?success";
        } catch (Exception e){
            e.printStackTrace();
            return "redirect:/activate-account?error";
        }

    }

    @GetMapping("/activate-account")
    public ModelAndView getAcceptView(){
        ModelAndView model = new ModelAndView("user-accepted-view");
        return model;
    }
}
