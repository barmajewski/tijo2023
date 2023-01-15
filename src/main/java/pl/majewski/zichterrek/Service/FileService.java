package pl.majewski.zichterrek.Service;

import pl.majewski.zichterrek.Model.File;

import java.util.Optional;

public interface FileService {

    File saveFile(File file);
    File saveImage(File image);
    Optional<File> findById(Long fileId);
    void delete(Long id);
}
