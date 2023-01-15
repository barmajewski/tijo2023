package pl.majewski.zichterrek.Service;

import pl.majewski.zichterrek.Model.Message;
import pl.majewski.zichterrek.Model.User;

import java.util.List;
import java.util.Optional;

public interface MessageService {
    Optional<Message> findById(Long messageId);
    Message save(Message message);
    Message edit(Message message);
    List<Message> findAllMessage();
    List<Message> findAllUserMessage(User user);
}
