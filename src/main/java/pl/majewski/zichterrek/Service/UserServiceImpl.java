package pl.majewski.zichterrek.Service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.majewski.zichterrek.Forms.RegisterForm;
import pl.majewski.zichterrek.Model.User;
import pl.majewski.zichterrek.Repository.UserRepo;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User save(User user) {
        return userRepo.save(user);
    }

    @Override
    public User edit(User user) {
        return userRepo.save(user);
    }

    @Override
    public User activeteUser(String activateCode) {
        User user = userRepo.findByActivationCode(activateCode).get();
        user.setEnabled(true);
        return userRepo.save(user);
    }

    @Override
    public Optional<User> findLogged() {
        return userRepo.findByEmailAndEnabledTrue(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Override
    public User createNewUser(RegisterForm registerForm) {
        User user = new User();
        user.setEmail(registerForm.getEmail());
        user.setFirstName(registerForm.getFirstName());
        user.setLastName(registerForm.getLastName());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        user.setPassword(encoder.encode(registerForm.getPassword()));
        user.setAuthorities(Arrays.asList("ROLE_USER"));
        user.setActivationCode(RandomStringUtils.randomAlphanumeric(16));
        user.setEnabled(false);
        return userRepo.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepo.findById(id);
    }

    @Override
    public List<User> findAllUser() {
        return userRepo.findAll();
    }
}
