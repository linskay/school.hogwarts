package ru.hogwarts.school.service.impl;

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
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    public StudentServiceImpl(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student findStudent(long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Student.class, id));
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

    @Override
    public Student assignFacultyToStudent(Long studentId, Long facultyId) {
        Optional<Student> student = studentRepository.findById(studentId);
        Optional<Faculty> faculty = facultyRepository.findById(facultyId);

        if (student.isPresent() && faculty.isPresent()) {
            student.get().setFaculty(faculty.get());
            return studentRepository.save(student.get());
        } else {
            try {
                throw new ClassNotFoundException("Студент или факультет не найден");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}