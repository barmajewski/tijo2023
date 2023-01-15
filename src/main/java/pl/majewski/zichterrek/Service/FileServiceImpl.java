package pl.majewski.zichterrek.Service;

import org.springframework.stereotype.Service;
import pl.majewski.zichterrek.Model.File;
import pl.majewski.zichterrek.Repository.FileRepo;

import java.util.Optional;

@Service
public class FileServiceImpl implements FileService{

    private final FileRepo fileRepo;

    public FileServiceImpl(FileRepo fileRepo){
        this.fileRepo = fileRepo;
    }

    @Override
    public File saveFile(File file) {
        return fileRepo.saveAndFlush(file);
    }

    @Override
    public Optional<File> findById(Long fileId) {
        return fileRepo.findById(fileId);
    }

    @Override
    public File saveImage(File image) {
        return fileRepo.saveAndFlush(image);
    }

    @Override
    public void delete(Long id) {
        fileRepo.deleteById(id);
    }
}
