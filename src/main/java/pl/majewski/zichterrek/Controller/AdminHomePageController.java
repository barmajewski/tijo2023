package pl.majewski.zichterrek.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.majewski.zichterrek.Model.Message;
import pl.majewski.zichterrek.Model.User;
import pl.majewski.zichterrek.Service.MessageService;
import pl.majewski.zichterrek.Service.UserService;

import java.util.List;

@Controller
@RequestMapping("/panel/admin")
public class AdminHomePageController {
    private final UserService userService;
    private final MessageService messageService;

    public AdminHomePageController(UserService userService, MessageService messageService) {
        this.userService = userService;
        this.messageService = messageService;
    }

    @ModelAttribute
    void addAttribute(Model model){
        User loggedUser = userService.findLogged().get();
        model.addAttribute("loggedUser",loggedUser);
    }

    @GetMapping("")
    public ModelAndView getAdminHomePageView(){
        ModelAndView model = new ModelAndView("admin/dashboard");
        List<Message> messageList = messageService.findAllMessage();
        model.addObject("messageList",messageList);
        return model;
    }

    @GetMapping("/uzytkownicy")
    public ModelAndView getUserListView(){
        ModelAndView model = new ModelAndView("/admin/user-list");
        List<User> userList = userService.findAllUser();
        model.addObject("userList",userList);
        return model;
    }

    @GetMapping("/zablokuj/{id}")
    public String banUser(@PathVariable Long id){
        try {
            User user = userService.findById(id).get();
            user.setEnabled(false);
            userService.save(user);
            return "redirect:/panel/admin?success";
        } catch (Exception e){
            e.printStackTrace();
            return "redirect:/panel/admin?error";
        }
    }
}
