package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public Collection<Student> getAll() {
        return studentService.getAll();
    }



    @GetMapping("/{id}")
    public Student getById(@PathVariable("id") Long id) {
        return studentService.getById(id);
    }

    @GetMapping("/filtered")
    public Collection<Student> getByAge(@RequestParam("age") int age) {
        return studentService.getByAge(age);
    }
    @GetMapping("/by-age")
    public Collection<Student> getByAge(@RequestParam int min, @RequestParam int max) {
        return studentService.getByAgeBetween(min, max);
    }
    @GetMapping("/by-faculty")
    public Collection<Student> getByFaculty(@RequestParam Long facultyId) {
        return studentService.getByFacultyId(facultyId);
    }

    @PostMapping
    public Student create(@RequestBody Student student) {
        return studentService.create(student);
    }
    @PutMapping
    public Student update (@PathVariable("id") Long id, @RequestBody Student student) {
        return studentService.update(id, student);
    }

    @DeleteMapping
    public void delete(@PathVariable("id") long id) {
        studentService.delete(id);
    }
}
