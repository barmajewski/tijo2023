package pl.majewski.zichterrek.Service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pl.majewski.zichterrek.Model.User;
import pl.majewski.zichterrek.Repository.UserRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {

    UserService userService;
    UserRepo userRepo = mock(UserRepo.class);

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

    @Test
    void saveShouldSucceed() {
        //given
        User user1 = User
                .builder()
                .userId(1L)
                .firstName("Jacek")
                .lastName("Wiśniewski")
                .email("jacekw@test.com")
                .build();

        doReturn(user1).when(userRepo).save(user1);
        //when
        userService = new UserServiceImpl(userRepo);
        User savedUser =userService.save(user1);
        //then
        Assertions.assertEquals(user1,savedUser);
    }

    @Test
    void saveShouldFail() {
        //given
        User user1 = User
                .builder()
                .userId(1L)
                .firstName("Jacek")
                .lastName("Wiśniewski")
                .email("jacekw@test.com")
                .build();
        User user2 = User
                .builder()
                .userId(1L)
                .firstName("Jacek")
                .lastName("Wiśniewski")
                .email("jacekw@test.com")
                .build();

        doReturn(user1).when(userRepo).save(user1);
        doReturn(null).when(userRepo).save(user2);
        //when
        userService = new UserServiceImpl(userRepo);
        userService.save(user1);
        User savedUser =userService.save(user1);
        //then
        Assertions.assertNotEquals(user2,savedUser);
    }

    @Test
    void editShouldSucceed() {
        //given
        User user1 = User
                .builder()
                .userId(1L)
                .firstName("Jacek")
                .lastName("Wiśniewski")
                .email("jacekw@test.com")
                .build();
        doReturn(user1).when(userRepo).save(user1);
        //when
        userService = new UserServiceImpl(userRepo);
        user1.setFirstName("Tomek");
        User editedUser = userService.edit(user1);
        //then
        Assertions.assertEquals("Tomek",editedUser.getFirstName());
    }

    @Test
    void editShouldFail() {
        //given
        User user1 = User
                .builder()
                .userId(1L)
                .firstName("Jacek")
                .lastName("Wiśniewski")
                .email("jacekw@test.com")
                .build();
        doReturn(null).when(userRepo).save(user1);
        //when
        userService = new UserServiceImpl(userRepo);
        User editedUser = userService.edit(user1);
        //then
        Assertions.assertNull(editedUser);
    }

    @Test
    void findByIdShouldSucceed() {
        List<User> userList = createUsers();
        Optional<User> optionalUser = Optional.ofNullable(userList.get(0));
        doReturn(optionalUser).when(userRepo).findById(1L);
        //when
        userService = new UserServiceImpl(userRepo);
        Optional<User> findUser = userService.findById(1L);
        //then
        Assertions.assertEquals(userList.get(0),findUser.get());
    }

    @Test
    void findByIdShouldFail() {
        List<User> userList = createUsers();
        Optional<User> optionalUser = Optional.ofNullable(userList.get(0));
        doReturn(null).when(userRepo).findById(1L);
        //when
        userService = new UserServiceImpl(userRepo);
        Optional<User> findUser = userService.findById(1L);
        //then
        Assertions.assertNull(findUser);
    }

    @Test
    void findAllUserShouldSucceed() {
        List<User> userList = createUsers();
        doReturn(userList).when(userRepo).findAll();
        //when
        userService = new UserServiceImpl(userRepo);
        List<User> searchedList = userService.findAllUser();
        //then
        Assertions.assertEquals(userList,searchedList);
    }

    @Test
    void findAllUserShouldFail() {
        List<User> userList = createUsers();
        doReturn(null).when(userRepo).findAll();
        //when
        userService = new UserServiceImpl(userRepo);
        List<User> searchedList = userService.findAllUser();
        //then
        Assertions.assertNotEquals(userList,searchedList);
    }
}