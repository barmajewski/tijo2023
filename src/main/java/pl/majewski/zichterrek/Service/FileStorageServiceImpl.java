package pl.majewski.zichterrek.Service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class FileStorageServiceImpl implements FileStorageService{

    private final Path root = Paths.get("uploads");

    @Override
    public void init() {
        try {
            Path path = Paths.get("uploads");
            if(!Files.exists(path)) {
                Files.createDirectory(root);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public String saveFile(MultipartFile file, String directory) {
        try {
            // Create folder if not exist
            Path path = Paths.get(this.root.toString(), directory);
            if(!Files.exists(path)) {
                Files.createDirectory(path);
            }
            //  Name creator
            String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("." ) + 1);
            String filename =  UUID.randomUUID() + "." + extension;
            Files.copy(file.getInputStream(), path.resolve(filename));
            return filename;
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    @Override
    public String saveImage(MultipartFile file, String directory) {
        try {
            // Create folder if not exist
            Path path = Paths.get(this.root.toString(), directory);
            if(!Files.exists(path)) {
                Files.createDirectory(path);
            }
            //  Name creator
            String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("." ) + 1);
            String filename =  UUID.randomUUID() + "." + extension;

            byte[] compressedImageBytes = compressImage(file);

            Files.write(path.resolve(filename), compressedImageBytes);
            return filename;
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    @Override
    public Resource load(String directory, String filename) {
        try {
            Path path = Paths.get(this.root.toString(), directory);
            Path file = path.resolve(filename);

            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public void deleteByName(String directory, String filename) {
        Path path = Paths.get(this.root.toString(), directory, filename);
        FileSystemUtils.deleteRecursively(path.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 2).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }

    public byte[] compressImage(MultipartFile image) throws IOException {
        InputStream inputStream = image.getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        float imageQuality = 0.3f;

        // Create the buffered image
        BufferedImage bufferedImage = ImageIO.read(inputStream);

        // Get image writers
        String extension = image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf("." ) + 1);
        Iterator<ImageWriter> imageWriters = ImageIO.getImageWritersByFormatName(extension); // Input your Format Name here

        if (!imageWriters.hasNext()) {
            throw new IllegalStateException("Writers Not Found!!");
        }

        ImageWriter imageWriter = imageWriters.next();
        ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(outputStream);
        imageWriter.setOutput(imageOutputStream);

        ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();

        // Set the compress quality metrics
        imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        imageWriteParam.setCompressionQuality(imageQuality);

        // Compress and insert the image into the byte array.
        imageWriter.write(null, new IIOImage(bufferedImage, null, null), imageWriteParam);

        byte[] imageBytes = outputStream.toByteArray();

        // close all streams
        inputStream.close();
        outputStream.close();
        imageOutputStream.close();
        imageWriter.dispose();

        return imageBytes;
    }
}
