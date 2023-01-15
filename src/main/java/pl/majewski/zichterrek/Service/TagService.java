package pl.majewski.zichterrek.Service;

import pl.majewski.zichterrek.Model.Tag;

import java.util.List;
import java.util.Optional;

public interface TagService {
    List<Tag> findAllTags();

    Optional<Tag> findByName(String name);

    void save(Tag newTag);
}
