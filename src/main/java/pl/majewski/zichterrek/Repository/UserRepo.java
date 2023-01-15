package pl.majewski.zichterrek.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.majewski.zichterrek.Model.User;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User> findByEmailAndEnabledTrue(String email);
    Optional<User> findByActivationCode(String activationCode);
}
