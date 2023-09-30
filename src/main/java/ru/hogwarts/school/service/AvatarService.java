package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class AvatarService {
    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;
    @Value("${path.to.avatars.folder}")
    private Path pathToAvatars;

    public Avatar getById(Long id) {
        return avatarRepository.findById(id).orElseThrow();
    }
    public AvatarService(AvatarRepository avatarRepository, StudentRepository studentRepository, Path pathToAvatars) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
    }

    public Long save(Long studentId, MultipartFile multipartFile) throws IOException {
        String absolutePath = saveToDisk(studentId, multipartFile);
        Avatar avatar = saveToDb(studentId, multipartFile, absolutePath);
        return avatar.getId();
    }

    private Avatar saveToDb(Long studentId, MultipartFile multipartFile, String absolutePath) throws IOException {
        Student studentReference = studentRepository.getReferenceById(studentId);
        Avatar avatar = avatarRepository.findByStudent(studentReference).orElse(new Avatar());

        avatar.setStudent(studentReference);
        avatar.setFilePath(absolutePath);
        avatar.setMediaType(multipartFile.getContentType());
        avatar.setFileSize(multipartFile.getSize());
        avatar.setData(multipartFile.getBytes());
        avatarRepository.save(avatar);
        return avatar;
    }

    private String saveToDisk(Long studentId, MultipartFile multipartFile) throws IOException {
        Files.createDirectories(pathToAvatars);
        String originalFilename = multipartFile.getOriginalFilename();
        assert originalFilename != null;
        int dotIndex = originalFilename.lastIndexOf(".");
        String extension = originalFilename.substring(dotIndex);
        String fileName = studentId + extension;
        String absolutePath = pathToAvatars.toAbsolutePath() + "/" + fileName;
        FileOutputStream fos = new FileOutputStream(absolutePath);
        multipartFile.getInputStream().transferTo(fos);
        fos.close();
        return absolutePath;
    }
}
