package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final Map<Long, Student> students = new HashMap<>();
    private long counter = 0;

    @Override
    public long createStudent(Student student) {
        student.setId(++counter);
        students.put(counter, student);
        return counter;
    }

    @Override
    public Student findStudent(long id) {
        if (!students.containsKey(id)) {
            throw new StudentNotFoundException(id);
        }
        return students.get(id);
    }

    @Override
    public Student editStudent(long id, Student student) {
        if (!students.containsKey(id)) {
            throw new StudentNotFoundException(id);
        }
        return students.put(id, student);
    }

    @Override
    public Student deleteStudent(long id) {
        if (!students.containsKey(id)) {
            throw new StudentNotFoundException(id);
        }
        return students.remove(id);
    }

    @Override
    public List<Student> filterByAge(int age) {
        return students.values().stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toList());
    }

    public Collection<Student> findAllStudent() {
        return students.values();
    }
}
