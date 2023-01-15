package pl.majewski.zichterrek.Controller;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import pl.majewski.zichterrek.Model.FileInfo;
import pl.majewski.zichterrek.Service.FileStorageService;
import pl.majewski.zichterrek.Service.UserService;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class FileController {

    private final FileStorageService fileStorageService;
    private final UserService userService;

    public FileController(FileStorageService fileStorageService, UserService userService) {
        this.fileStorageService = fileStorageService;
        this.userService = userService;
    }

    @PostMapping("/upload-file")
    public ResponseEntity<String> uploadFiles(@RequestParam("files") MultipartFile[] files, String dirPath) {
        String message = "";
        List<String> namesList = new ArrayList<>();
        try {
            List<String> fileNames = new ArrayList<>();
            Arrays.asList(files).stream().forEach(file -> {
                fileStorageService.saveFile(file, dirPath);
                fileNames.add(file.getOriginalFilename());
                //namesList used for logging file uploads
                namesList.add("File name: " + file.getName());
                namesList.add("Original file name: " + file.getOriginalFilename());
                namesList.add("File type: " + file.getContentType());
            });


            message = "Uploaded the files successfully: " + fileNames;
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "Fail to upload files!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @PostMapping("/upload-image")
    public ResponseEntity<String> uploadImages(@RequestParam("images") MultipartFile[] images, String dirPath) {
        String message = "";
        List<String> namesList = new ArrayList<>();
        try {
            List<String> imageNames = new ArrayList<>();
            Arrays.asList(images).stream().forEach(image -> {
                fileStorageService.saveImage(image, dirPath);
                imageNames.add(image.getOriginalFilename());
                //namesList used for logging image uploads
                namesList.add("File name: " + image.getName());
                namesList.add("Original name: " + image.getOriginalFilename());
                namesList.add("File type: " + image.getContentType());
            });


            message = "Uploaded the files successfully: " + imageNames;
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "Fail to upload files!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @GetMapping("/files/{directory}")
    public ResponseEntity<List<FileInfo>> getListFiles(@PathVariable String directory) {
        List<FileInfo> files = fileStorageService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(FileController.class, "getFile", directory, path.getFileName().toString()).build().toString();
            return new FileInfo(filename, url);
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/files/{directory}/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String directory, @PathVariable String filename) throws IOException {
        try {
            Resource file = fileStorageService.load(directory, filename);
            if(ImageIO.read(file.getURL()) != null) {
                return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(file);
            } else {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
