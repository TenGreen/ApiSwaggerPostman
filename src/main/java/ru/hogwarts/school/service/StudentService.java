package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.DataNotFoundExceptions;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class StudentService {
    private final Map<Long, Student> map = new HashMap<>();
    private Long COUNTER = 1L;

    public Student getById(Long id) {
        return map.get(id);
    }

    public Collection<Student> getAll() {
        return map.values();
    }

    public Student create(Student student) {
        Long nextId = COUNTER++;
        student.setId(nextId);
        map.put(student.getId(), student);
        return student;
    }

    public Student update(Long id, Student student) {
        if (!map.containsKey(id)) {
            throw new DataNotFoundExceptions();
        }
        Student exsistingStudent = map.get(id);
        exsistingStudent.setName(student.getName());
        exsistingStudent.setAge(student.getAge());
        return exsistingStudent;
    }

    public void delete(Long id) {
        if (!map.containsKey(id)) {
            throw new DataNotFoundExceptions();
        }
        map.remove(id);
    }
}
