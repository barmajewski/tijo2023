package pl.majewski.zichterrek.Config;

import lombok.SneakyThrows;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import pl.majewski.zichterrek.Model.User;
import pl.majewski.zichterrek.Repository.UserRepo;
import pl.majewski.zichterrek.exception.EmailNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    public CustomUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<User> user = userRepo.findByEmailAndEnabledTrue(email);
        if(!user.isPresent()) {
            throw new EmailNotFoundException("Incorrect email:" + email);
        }

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        for(String authority : user.get().getAuthorities()) {
            authorityList.add(new SimpleGrantedAuthority(authority));
        }
        return new org.springframework.security.core.userdetails.User(user.get().getEmail(),user.get().getPassword(),authorityList);
    }
}
