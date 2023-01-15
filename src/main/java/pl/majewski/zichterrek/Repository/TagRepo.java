package pl.majewski.zichterrek.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.majewski.zichterrek.Model.Tag;

import java.util.Optional;

@Repository
public interface TagRepo extends JpaRepository<Tag,Long> {

    Optional<Tag> findByName(String name);
}
