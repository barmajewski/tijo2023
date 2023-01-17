package pl.majewski.zichterrek.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import pl.majewski.zichterrek.Forms.NewPost;
import pl.majewski.zichterrek.Model.File;
import pl.majewski.zichterrek.Model.Message;
import pl.majewski.zichterrek.Model.Tag;
import pl.majewski.zichterrek.Model.User;
import pl.majewski.zichterrek.Service.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/panel/uzytkownik/wiadomosc")
public class UserMessageController {
    private final UserService userService;
    private final FileService fileService;
    private final FileStorageService fileStorageService;
    private final TagService tagService;
    private final MessageService messageService;

    public UserMessageController(UserService userService, FileService fileService, FileStorageService fileStorageService, TagService tagService, MessageService messageService) {
        this.userService = userService;
        this.fileService = fileService;
        this.fileStorageService = fileStorageService;
        this.tagService = tagService;
        this.messageService = messageService;
    }

    @GetMapping("/utworz-wiadomosc")
    public ModelAndView getMessageCreatorView(){
        ModelAndView model = new ModelAndView("user/post-message");
        model.addObject("loggedUser",userService.findLogged().get());
        model.addObject("NewPost",new NewPost());
        model.addObject("tagList",tagService.findAllTags());
        return model;
    }

    @PostMapping("/utworz-wiadomosc")
    @Transactional
    public String getMessageCreator(@ModelAttribute @Valid Message message/*, @RequestParam(name = "attachment") MultipartFile attachment*/){
        try{
            Optional<User> loggedUser = userService.findLogged();
            File newPhoto = new File();

            Set<Tag> tagSet = new HashSet<>();
            if (message.getTagNames() != null && message.getTagNames().length > 0) {
                Arrays.stream(message.getTagNames()).forEach(name -> tagService.findByName(name).ifPresentOrElse(
                        tag -> tagSet.add(tag),
                        () -> {
                            Tag newTag = new Tag();
                            newTag.setName(name);
                            tagService.save(newTag);
                            tagSet.add(newTag);
                        }));
                message.setTags(tagSet);
            }

//            if (!attachment.isEmpty()){
//                String filename = fileStorageService.saveImage(attachment, "attachment");
//                String url = MvcUriComponentsBuilder.fromMethodName(File.class, "getFile","attachment", filename).build().toString();
//                newPhoto.setUrl(url);
//                newPhoto.setName(filename);
//                newPhoto = fileService.saveImage(newPhoto);
//
//                message.setAttachment(newPhoto);
//            }
            message.setUser(loggedUser.get());
            messageService.save(message);
            return "redirect:/panel/uzytkownik?success";
        } catch (Exception e){
            e.printStackTrace();
            return "redirect:/panel/uzytkownik?error";
        }
    }

    @GetMapping("/moje-wiadomosci")
    public ModelAndView getAllUserMessageView(){
        ModelAndView model = new ModelAndView("user/user-messages");
        Optional<User> loggedUser = userService.findLogged();
        model.addObject("loggedUser",loggedUser.get());
        List<Message> messageList = messageService.findAllUserMessage(loggedUser.get());
        model.addObject("messageList",messageList);
        return model;
    }

    @GetMapping("/{id}")
    public ModelAndView getMessageEditView(@PathVariable("id") Long id){
        ModelAndView model = new ModelAndView("user/edit-message");
        Optional<User> loggedUser = userService.findLogged();
        Optional<Message> message = messageService.findById(id);
        List<Tag> tagList = tagService.findAllTags();
        model.addObject("loggedUser",loggedUser.get());
        model.addObject("message",message.get());
        model.addObject("tagList",tagList);
        return model;
    }

    @PostMapping("/edytuj")
    @Transactional
    public String editPost(@ModelAttribute Message message){
        try {
            File newPhoto = new File();

            Optional<Message> optionalMessage = messageService.findById(message.getMessageId());
            if (optionalMessage.isPresent()) {
                Set<Tag> newTagSet = new HashSet<>();
                if (message.getTagNames() != null && message.getTagNames().length > 0) {
                    Arrays.stream(message.getTagNames()).forEach(name -> tagService.findByName(name).ifPresentOrElse(
                            tag -> {
                                newTagSet.add(tag);
                            },
                            () -> {
                                Tag newTag = new Tag();
                                newTag.setName(name);
                                tagService.save(newTag);
                                newTagSet.add(newTag);
                            }));
                    message.setTags(newTagSet);
                }
                message.setCommentsList(optionalMessage.get().getCommentsList());

                messageService.save(message);
            }
            return "redirect:/panel/uzytkownik/wiadomosc/moje-wiadomosci?success";
        } catch (Exception e){
            return "redirect:/panel/uzytkownik/wiadomosc/moje-wiadomosci?error";
        }
    }
}
