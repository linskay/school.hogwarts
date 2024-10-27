package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty findFaculty(Long id) {
        return facultyRepository.findById(id).orElseThrow(() ->
                new FacultyNotFoundException(id));
    }

    @Override
    public void deleteFaculty(long id) {
        if (!facultyRepository.existsById(id)) {
            throw new FacultyNotFoundException(id);
        }
        facultyRepository.deleteById(id);
    }

    @Override
    public Faculty editFaculty(Faculty faculty) {
        if (!facultyRepository.existsById(faculty.getId())) {
            throw new FacultyNotFoundException(faculty.getId());
        }
        return facultyRepository.save(faculty);
    }

    @Override
    public Collection<Faculty> findByColorBetween(String color) {
        return facultyRepository.findAllByNameContainingIgnoreCase(color);
    }

    @Override
    public Collection<Faculty> findAllFaculty() {
        return facultyRepository.findAll();
    }

    @Override
    public Collection<Student> getStudentsByFaculty(long id) {
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> new FacultyNotFoundException(id));
        return studentRepository.findAllByFaculty(faculty);
    }
}