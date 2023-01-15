package pl.majewski.zichterrek.Service;

import pl.majewski.zichterrek.Forms.RegisterForm;
import pl.majewski.zichterrek.Model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User save(User user);
    User edit(User user);
    User activeteUser(String activateCode);
    Optional<User> findLogged();

    User createNewUser(RegisterForm registerForm);

    Optional<User> findById(Long id);

    List<User> findAllUser();
}
