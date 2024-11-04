package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.NotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

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
        return facultyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Faculty.class, id));
    }

    @Override
    public void deleteFaculty(long id) {
        facultyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Faculty.class, id));
        facultyRepository.deleteById(id);
    }

    @Override
    public Faculty editFaculty(Faculty faculty) {
        long id = faculty.getId();
        Faculty existingFaculty = facultyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Faculty.class, id));
        existingFaculty.setName(faculty.getName());
        existingFaculty.setColor(faculty.getColor());
        return facultyRepository.save(existingFaculty);
    }

    @Override
    public List<Faculty> findByColor(String color) {
        return facultyRepository.findByColorContainingIgnoreCase(color);
    }


    @Override
    public List<Faculty> findAllFaculty() {
        return facultyRepository.findAll();
    }

    @Override
    public List<Student> getStudentsByFaculty(long id) {
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Faculty.class, id));
        return studentRepository.findAllByFaculty(faculty);
    }
}

