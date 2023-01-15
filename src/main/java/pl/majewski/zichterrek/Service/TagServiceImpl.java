package pl.majewski.zichterrek.Service;

import org.springframework.stereotype.Service;
import pl.majewski.zichterrek.Model.Tag;
import pl.majewski.zichterrek.Repository.TagRepo;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService{
    private TagRepo tagRepo;

    public TagServiceImpl(TagRepo tagRepo) {
        this.tagRepo = tagRepo;
    }

    @Override
    public List<Tag> findAllTags() {
        return tagRepo.findAll();
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return tagRepo.findByName(name);
    }

    @Override
    public void save(Tag newTag) {
        tagRepo.save(newTag);
    }
}
