package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final Map<Long, Faculty> faculties = new HashMap<>();
    private long counter = 0;

    @Override
    public long createFaculty(Faculty faculty) {
        faculty.setId(++counter);
        faculties.put(counter, faculty);
        return counter;
    }

    @Override
    public Faculty findFaculty(long id) {
        if (!faculties.containsKey(id)) {
            throw new FacultyNotFoundException(id);
        }
        return faculties.get(id);
    }

    @Override
    public Faculty deleteFaculty(long id) {
        if (!faculties.containsKey(id)) {
            throw new FacultyNotFoundException(id);
        }
        return faculties.remove(id);
    }

    @Override
    public Faculty editFaculty(long id, Faculty faculty) {
        if (!faculties.containsKey(id)) {
            throw new FacultyNotFoundException(id);
        }
        return faculties.put(id, faculty);
    }

    @Override
    public List<Faculty> filterByColor(String color) {
        return faculties.values().stream()
                .filter(faculty -> faculty.getColor().equals(color))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Faculty> findAllFaculty() {
        return faculties.values();
    }
}

