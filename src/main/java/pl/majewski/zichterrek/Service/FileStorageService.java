package pl.majewski.zichterrek.Service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileStorageService {

    public void init();
    public String saveFile(MultipartFile file, String directory);
    public String saveImage(MultipartFile image, String directory);
    public Resource load(String directory, String filename);
    public void deleteAll();
    public void deleteByName(String directory, String filename);
    public Stream<Path> loadAll();
}
