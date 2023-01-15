package pl.majewski.zichterrek.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.majewski.zichterrek.Model.File;

@Repository
public interface FileRepo extends JpaRepository<File,Long> {
}
