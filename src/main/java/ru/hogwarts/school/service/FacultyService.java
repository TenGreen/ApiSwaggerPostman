package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class FacultyService {


    private final FacultyRepository facultyRepository;
    @Autowired
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty getById(Long id) {
        return facultyRepository.findById(id).orElseThrow(FacultyNotFoundException::new);
    }

    public Collection<Faculty> getAll() {

        return facultyRepository.findAll();
    }

    public Collection<Faculty> getByColor(String color) {
        return facultyRepository.findAllByColor(color);
    }
    public Collection<Faculty> getByColorOrName(String color, String name) {
        return facultyRepository.findAllByColorIgnoreCaseOrNameIgnoreCase(color, name);
    }

    public Faculty create(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty update(Long id, Faculty faculty) {
        Faculty exsistingFaculty = facultyRepository.findById(id).
                orElseThrow(FacultyNotFoundException::new);
        Optional.ofNullable(faculty.getName()).ifPresent(exsistingFaculty::setName);
        Optional.ofNullable(faculty.getColor()).ifPresent((exsistingFaculty::setColor));
        return facultyRepository.save(exsistingFaculty);
    }

    public void delete(Long id) {

        Faculty faculty = facultyRepository.findById(id).
                orElseThrow(FacultyNotFoundException::new);
        facultyRepository.delete(faculty);
    }

    public Faculty getByStudentId(Long studentId) {
        Optional<Student> getStudent = facultyRepository.findByStudent_Id(studentId);
        return getStudent.orElseThrow(FacultyNotFoundException::new).getFaculty();

    }
}
