package pl.majewski.zichterrek.Service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.majewski.zichterrek.Model.Tag;
import pl.majewski.zichterrek.Repository.TagRepo;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TagServiceImplTest {

    TagRepo tagRepo = mock(TagRepo.class);
    TagService tagService = new TagServiceImpl(tagRepo);

    @Test
    void findByNameShouldSucceed() {
        //given
        Tag tag = Tag.builder()
                .tagId(1L)
                .name("Sport")
                .build();
        doReturn(Optional.ofNullable(tag)).when(tagRepo).findByName("Sport");
        //when
        Optional<Tag> optionalTag = tagService.findByName("Sport");
        //then
        Assertions.assertEquals(tag,optionalTag.get());
    }

    @Test
    void findByNameShouldFail() {
        //given
        doReturn(null).when(tagRepo).findByName("Sport");
        //when
        Optional<Tag> optionalTag = tagService.findByName("Sport");
        //then
        Assertions.assertNull(optionalTag);
    }

    @Test
    void saveShouldSucceed() {
        //given
        Tag tag = Tag.builder()
                .tagId(1L)
                .name("Sport")
                .build();
        doReturn(tag).when(tagRepo).save(tag);
        //when
        Tag savedTag = tagService.save(tag);
        //then
        Assertions.assertEquals(tag,savedTag);
    }

    @Test
    void saveShouldFail() {
        //given
        Tag tag = Tag.builder()
                .tagId(1L)
                .name("Sport")
                .build();
        doReturn(null).when(tagRepo).save(tag);
        //when
        Tag savedTag = tagService.save(tag);
        //then
        Assertions.assertNotEquals(tag,savedTag);
    }
}