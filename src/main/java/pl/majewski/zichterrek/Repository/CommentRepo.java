package pl.majewski.zichterrek.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.majewski.zichterrek.Model.Comments;

@Repository
public interface CommentRepo extends JpaRepository<Comments,Long> {
}
