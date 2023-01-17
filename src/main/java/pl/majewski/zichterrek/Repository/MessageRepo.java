package pl.majewski.zichterrek.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.majewski.zichterrek.Model.Message;
import pl.majewski.zichterrek.Model.User;

import java.util.List;

@Repository
public interface MessageRepo extends JpaRepository<Message,Long> {
    List<Message> findAllByUserOrderByMessageIdDesc(User user);

//    @Modifying
//    @Query(nativeQuery = true,value ="DELETE FROM public.wiadomosc_tagi WHERE wiadomosc_id=?1;" )
    void deleteByMessageId(Long messageId);

    @Query(nativeQuery = true,value = "INSERT INTO public.wiadomosc_tagi VALUES (?1,?2)")
    void addTags(Long messageId,Long tagId);
}
