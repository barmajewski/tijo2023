package pl.majewski.zichterrek.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.majewski.zichterrek.Model.Comments;
import pl.majewski.zichterrek.Model.Message;
import pl.majewski.zichterrek.Model.User;
import pl.majewski.zichterrek.Service.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/panel/uzytkownik")
public class UserHomePageController {
    private final UserService userService;
    private final FileService fileService;
    private final FileStorageService fileStorageService;
    private final MessageService messageService;
    private final CommentService commentService;

    public UserHomePageController(UserService userService, FileService fileService, FileStorageService fileStorageService, MessageService messageService, CommentService commentService) {
        this.userService = userService;
        this.fileService = fileService;
        this.fileStorageService = fileStorageService;
        this.messageService = messageService;
        this.commentService = commentService;
    }

    @GetMapping("")
    public ModelAndView getDashboardView(){
        ModelAndView model = new ModelAndView("user/dashboard");
        Optional<User> loggedUser = userService.findLogged();
        model.addObject("loggedUser",loggedUser.get());
        List<Message> messageList = messageService.findAllMessage();
        model.addObject("messageList",messageList);
        return model;
    }

    @PostMapping("/komentuj")
    public String commentMessage(@RequestParam Long messageId, @ModelAttribute Comments comments){
        try {

            Optional<Message> optionalMessage = messageService.findById(messageId);
            if (optionalMessage.isPresent()){
                List<Comments> commentsList = optionalMessage.get().getCommentsList();
                comments = commentService.save(comments);
                commentsList.add(comments);
                optionalMessage.get().setCommentsList(commentsList);
                messageService.save(optionalMessage.get());
            }
            return "redirect:/panel/uzytkownik?success";
        } catch (Exception e){
            return "redirect:/panel/uzytkownik?error";
        }
    }

    @GetMapping("/ustawienia")
    public ModelAndView getSettingView(){
        ModelAndView model = new ModelAndView("/user/settings");
        Optional<User> loggedUser = userService.findLogged();
        model.addObject("loggedUser",loggedUser.get());
        return model;
    }
}
