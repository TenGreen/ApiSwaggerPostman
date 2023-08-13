package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.DataNotFoundExceptions;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class FacultyService {
    private final Map<Long, Faculty> map = new HashMap<>();
    private Long COUNTER = 1L;

    public Faculty getById(Long id) {
        return map.get(id);
    }

    public Collection<Faculty> getAll() {
        return map.values();
    }

    public Faculty create(Faculty faculty) {
        Long nextId = COUNTER++;
        faculty.setId(nextId);
        map.put(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty update(Long id, Faculty faculty) {
        if (!map.containsKey(id)) {
            throw new DataNotFoundExceptions();
        }
        Faculty exsistingFaculty = map.get(id);
        exsistingFaculty.setName(faculty.getName());
        exsistingFaculty.setColor(faculty.getColor());
        return exsistingFaculty;
    }

    public void delete(Long id) {
        if (!map.containsKey(id)) {
            throw new DataNotFoundExceptions();
        }
        map.remove(id);
    }
}
