package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.ApiSwaggerPostmanApplication;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = ApiSwaggerPostmanApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTest {
    @Autowired
    TestRestTemplate template;


    @Autowired
    FacultyRepository facultyRepository;
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    void clearDb() {
        studentRepository.deleteAll();
        facultyRepository.deleteAll();
    }

    @Test
    void create() {
        String name = "history";
        String color = "yellow";
        ResponseEntity<Faculty> response = template.
                postForEntity("/faculty", new Faculty(null, name, color), Faculty.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo(name);
        assertThat(response.getBody().getColor()).isEqualTo(color);
    }

    @Test
    void getById() {
        String name = "history";
        String color = "yellow";
        ResponseEntity<Faculty> response = createFaculty(name, color);
        Long facultyId = response.getBody().getId();

        response = template.getForEntity("/faculty/" + facultyId, Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getColor()).isEqualTo("yellow");
        assertThat(response.getBody().getName()).isEqualTo("history");
    }

    @Test
    void update() {
        String name = "history";
        String color = "yellow";
        ResponseEntity<Faculty> response = createFaculty(name, color);
        Long facultyId = response.getBody().getId();

        template.put("/faculty/" + facultyId,
                new Faculty(null, name, "yellow"));

        response = template.getForEntity("/faculty/" + facultyId, Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getColor()).isEqualTo("yellow");
    }

    @Test
    void getAll() {
        createFaculty("math", "red");
        createFaculty("history", "green");

        ResponseEntity<Collection> response = template.getForEntity("/faculty", Collection.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(2);
    }

    @Test
    void delete() {
        String name = "history";
        String color = "yellow";
        ResponseEntity<Faculty> response = createFaculty(name, color);
        Long facultyId = response.getBody().getId();

        template.delete("/faculty/" + facultyId);

        response = template.getForEntity("/faculty/" + facultyId, Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void getByColorOrName() {
        createFaculty("math", "red");
        createFaculty("history", "green");

        ResponseEntity<ArrayList> response = template.
                getForEntity("/faculty/by-color-or-name?color=red",
                ArrayList.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(1);
        Map<String, String> next = (HashMap)response.getBody().iterator().next();
        assertThat(next.get("color")).isEqualTo("red");
    }

    @Test
    void getColor() {
        createFaculty("math", "red");
        createFaculty("history", "green");
        createFaculty("biologi", "green");

        ResponseEntity<ArrayList> response = template.
                getForEntity("/faculty/filtered?color=green",
                        ArrayList.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(2);
        Map<String, String> next = (HashMap)response.getBody().iterator().next();
        assertThat(next.get("color")).isEqualTo("green");
    }
//    @Test
//    void byStudent(){
//        String name = "anna";
//        Integer age = 23;
//        ResponseEntity<Faculty> response = createFaculty("biologi", "green");
//        Faculty faculty = response.getBody();
//        Student student = new Student(null, name, age);
//        student.setFaculty(faculty);
//        ResponseEntity<Student> studentResponse = template.
//                 postForEntity("/faculty/student", student, Student.class);
//        assertThat(studentResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(studentResponse.getBody().getName()).isEqualTo("anna");
//        assertThat(studentResponse.getBody().getAge()).isEqualTo(23);
//        assertThat(studentResponse.getBody().getFaculty().getColor()).isEqualTo("green");
//        long studentId = studentResponse.getBody().getId();
//
//        response = template.getForEntity("/faculty//by-student?studentId=" + studentId, Faculty.class);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody()).isNotNull();
//        assertThat(response.getBody()).isEqualTo(faculty);
//
//
//    }
    private ResponseEntity<Faculty> createFaculty(String name, String color) {
        ResponseEntity<Faculty> response = template.postForEntity("/faculty",
                new Faculty(null, name, color),
                Faculty.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        return response;
    }
}
