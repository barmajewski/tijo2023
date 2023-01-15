package pl.majewski.zichterrek.Service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.majewski.zichterrek.Model.Message;
import pl.majewski.zichterrek.Model.User;
import pl.majewski.zichterrek.Repository.MessageRepo;

import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepo messageRepo;

    public MessageServiceImpl(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @Override
    public Optional<Message> findById(Long messageId) {
        return messageRepo.findById(messageId);
    }

    @Override
    public Message save(Message message) {
        return messageRepo.save(message);
    }

    @Override
    public Message edit(Message message) {
        return messageRepo.save(message);
    }

    @Override
    public List<Message> findAllMessage() {
        return messageRepo.findAll(Sort.by(Sort.Direction.DESC,"messageId"));
    }

    @Override
    public List<Message> findAllUserMessage(User user) {
        return messageRepo.findAllByUserOrderByMessageIdDesc(user);
    }
}
