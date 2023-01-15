package pl.majewski.zichterrek.Service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import pl.majewski.zichterrek.Model.Message;
import pl.majewski.zichterrek.Model.User;
import pl.majewski.zichterrek.Repository.MessageRepo;
import pl.majewski.zichterrek.Repository.UserRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@SpringBootTest
class MessageServiceImplTest {

    MessageService messageService;
    MessageRepo messageRepo = mock(MessageRepo.class);
    @Autowired
    private UserRepo userRepo;

    List<User> createUsers(){
        User user1 = User
                .builder()
                .userId(1L)
                .firstName("Jacek")
                .lastName("Wiśniewski")
                .email("jacekw@test.com")
                .build();
        User user2 = User
                .builder()
                .userId(2L)
                .firstName("Artur")
                .lastName("Nowak")
                .email("arturn@test.com")
                .build();
        User user3 = User
                .builder()
                .userId(3L)
                .firstName("Tomasz")
                .lastName("Kwaśniewski")
                .email("tomaszk@test.com")
                .build();
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        return userList;
    }

    @BeforeEach
    void createMessages(){
        List<User> userList = createUsers();
        List<Message> messageList = new ArrayList<>();
        Message message1 = Message.builder()
                .messageId(1L)
                .messageContent("test1")
                .user(userList.get(0))
                .build();
        Message message2 = Message.builder()
                .messageId(2L)
                .messageContent("test2")
                .user(userList.get(2))
                .build();
        Message message3 = Message.builder()
                .messageId(3L)
                .messageContent("test3")
                .user(userList.get(1))
                .build();
        Message message4 = Message.builder()
                .messageId(4L)
                .messageContent("test4")
                .user(userList.get(0))
                .build();
        Message message5 = Message.builder()
                .messageId(5L)
                .messageContent("test5")
                .user(userList.get(0))
                .build();
        Message message6 = Message.builder()
                .messageId(6L)
                .messageContent("test6")
                .user(userList.get(1))
                .build();
        Message message7 = Message.builder()
                .messageId(7L)
                .messageContent("test7")
                .user(userList.get(2))
                .build();
        Message message8 = Message.builder()
                .messageId(8L)
                .messageContent("test8")
                .user(userList.get(1))
                .build();
        this.messageList.add(message1);
        this.messageList.add(message2);
        this.messageList.add(message3);
        this.messageList.add(message4);
        this.messageList.add(message5);
        this.messageList.add(message6);
        this.messageList.add(message7);
        this.messageList.add(message8);
    }

    List<Message> messageList = new ArrayList<>();

    @Test
    void saveShouldSucceed() {
        //given
        User user = User.builder().userId(1L).build();
        Message message1 = Message.builder()
                .messageId(1L)
                .messageContent("test1")
                .user(user)
                .build();
        doReturn(message1).when(messageRepo).save(message1);
        //when
        messageService = new MessageServiceImpl(messageRepo);
        Message savedMessage = messageService.save(message1);
        //then
        Assertions.assertEquals(message1,savedMessage);
    }

    @Test
    void saveShouldFail() {
        //given
        User user = User.builder().userId(1L).build();
        Message message1 = Message.builder()
                .messageId(1L)
                .messageContent("test1")
                .user(user)
                .build();
        doReturn(null).when(messageRepo).save(message1);
        //when
        messageService = new MessageServiceImpl(messageRepo);
        Message savedMessage = messageService.save(message1);
        //then
        Assertions.assertNull(savedMessage);
    }

    @Test
    void editShouldSucceed() {
        //given
        User user = User.builder().userId(1L).build();
        Message message1 = Message.builder()
                .messageId(1L)
                .messageContent("test1")
                .user(user)
                .build();
        doReturn(message1).when(messageRepo).save(message1);
        //when
        messageService = new MessageServiceImpl(messageRepo);
        message1.setMessageContent("test2");
        Message editedMessage = messageService.edit(message1);
        //then
        Assertions.assertEquals(message1,editedMessage);
    }

    @Test
    void editShouldFail() {
        //given
        User user = User.builder().userId(1L).build();
        Message message1 = Message.builder()
                .messageId(1L)
                .messageContent("test1")
                .user(user)
                .build();
        doReturn(null).when(messageRepo).save(message1);
        //when
        messageService = new MessageServiceImpl(messageRepo);
        message1.setMessageContent("test2");
        Message editedMessage = messageService.edit(message1);
        //then
        Assertions.assertNull(editedMessage);
    }

    @Test
    void findByIdShouldSucceed() {
        //given
        Optional<Message> optionalMessage = Optional.ofNullable(messageList.get(1));
        doReturn(optionalMessage).when(messageRepo).findById(2L);
        //when
        messageService = new MessageServiceImpl(messageRepo);
        Optional<Message> findMessage = messageService.findById(2L);
        //then
        Assertions.assertEquals(messageList.get(1),findMessage.get());
    }

    @Test
    void findByIdShouldFail() {
        //given
        doReturn(null).when(messageRepo).findById(9L);
        //when
        messageService = new MessageServiceImpl(messageRepo);
        Optional<Message> findMessage = messageService.findById(9L);
        //then
        Assertions.assertNull(findMessage);
    }

    @Test
    void findAllMessageShouldSucceed() {
        //given
        doReturn(messageList).when(messageRepo).findAll();
        //when
        messageService = new MessageServiceImpl(messageRepo);
        List<Message> findMessageList = messageService.findAllMessage();
        //then
        Assertions.assertEquals(messageList,findMessageList);
    }

    @Test
    void findAllMessageShouldFail() {
        //given
        doReturn(null).when(messageRepo).findAll();
        //when
        messageService = new MessageServiceImpl(messageRepo);
        List<Message> findMessageList = messageService.findAllMessage();
        //then
        Assertions.assertNotEquals(messageList,findMessageList);
    }

    @Test
    void findAllUserMessageShouldSucceed() {
    }

    @Test
    void findAllUserMessageShouldFail() {
    }
}