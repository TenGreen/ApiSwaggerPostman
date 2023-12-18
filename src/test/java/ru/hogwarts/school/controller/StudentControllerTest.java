package ru.hogwarts.school.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.ApiSwaggerPostmanApplication;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = ApiSwaggerPostmanApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {
    TestRestTemplate template;
    FacultyRepository facultyRepository;
    StudentRepository studentRepository;
    @Autowired
    AvatarRepository avatarRepository;

    public StudentControllerTest(TestRestTemplate template,
                                 FacultyRepository facultyRepository,
                                 StudentRepository studentRepository, AvatarRepository avatarRepository
                                 ) {
        this.template = template;
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
    }
//    @Autowired
//    private DataSource dataSource;

    @BeforeEach
    void clearDB(){
        avatarRepository.deleteAll();
        studentRepository.deleteAll();
        facultyRepository.deleteAll();
    }
//    @Test
//    void create() {
//        String name = "history";
//        String color = "yellow";
//        ResponseEntity<Faculty> response = template.
//                postForEntity("/faculty", new Faculty(null, name, color), Faculty.class);
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody()).isNotNull();
//        assertThat(response.getBody().getName()).isEqualTo(name);
//        assertThat(response.getBody().getColor()).isEqualTo(color);
//    }
    @Test
void create() {
        String name = "anna";
        Integer age = 23;
        ResponseEntity<Student> response = template.postForEntity("/student",
                new Student(null, name, age), Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }
}
