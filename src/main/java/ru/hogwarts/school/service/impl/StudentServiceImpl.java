package ru.hogwarts.school.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.NotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student findStudent(long id) {
        return studentRepository.getById(id);
    }

    @Override
    public Student editStudent(Student student) {
        if (!studentRepository.existsById(student.getId())) {
            throw new NotFoundException(Student.class, student.getId());
        }
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Student.class, id));
        studentRepository.deleteById(student.getId());
    }

    @Override
    public List<Student> findByAgeBetween(int minAge, int maxAge) {
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    @Override
    public List<Student> findAllStudent() {
        return studentRepository.findAll();
    }

    @Override
    public Faculty getFacultyByStudentId(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        return student.map(Student::getFaculty)
                .orElseThrow(() -> new NotFoundException(Student.class, id));
    }
}