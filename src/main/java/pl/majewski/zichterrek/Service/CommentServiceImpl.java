package pl.majewski.zichterrek.Service;

import org.springframework.stereotype.Service;
import pl.majewski.zichterrek.Model.Comments;
import pl.majewski.zichterrek.Repository.CommentRepo;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepo commentRepo;

    public CommentServiceImpl(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    @Override
    public Comments save(Comments comments) {
        return commentRepo.save(comments);
    }
}
