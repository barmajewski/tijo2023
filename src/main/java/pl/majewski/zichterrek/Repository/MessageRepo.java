package pl.majewski.zichterrek.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.majewski.zichterrek.Model.Message;
import pl.majewski.zichterrek.Model.User;

import java.util.List;

@Repository
public interface MessageRepo extends JpaRepository<Message,Long> {
    List<Message> findAllByUserOrderByMessageIdDesc(User user);
}
