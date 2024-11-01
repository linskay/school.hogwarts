package ru.hogwarts.school.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.impl.StudentServiceImpl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static ru.hogwarts.school.service.TestConstants.TEST_STUDENT;
import static ru.hogwarts.school.service.TestConstants.TEST_STUDENT_2;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private FacultyRepository facultyRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    @DisplayName("Создание студента")
    void createStudent() {
        Student student = TEST_STUDENT;

        when(studentRepository.save(student)).thenReturn(student);

        Student createdStudent = studentService.createStudent(student);

        assertThat(createdStudent).isEqualTo(student);
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    @DisplayName("Поиск студента по ID")
    void findStudent() {
        Student student = TEST_STUDENT;

        when(studentRepository.getById(student.getId())).thenReturn(student);

        Student foundStudent = studentService.findStudent(student.getId());

        assertThat(foundStudent).isEqualTo(student);
        verify(studentRepository, times(1)).getById(student.getId());
    }

    @Test
    @DisplayName("Редактирование студента")
    void editStudent() {
        Student student = TEST_STUDENT;

        when(studentRepository.save(student)).thenReturn(student);

        Student editedStudent = studentService.editStudent(student);

        assertThat(editedStudent).isEqualTo(student);
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    @DisplayName("Удаление студента")
    void deleteStudent() {
        long studentId = TEST_STUDENT.getId();

        studentService.deleteStudent(studentId);

        verify(studentRepository, times(1)).deleteById(studentId);
    }

    @Test
    @DisplayName("Поиск студентов по возрасту")
    void findByAgeBetween() {
        int minAge = 20;
        int maxAge = 22;
        List<Student> students = List.of(
                TEST_STUDENT,
                TEST_STUDENT_2
        );

        when(studentRepository.findByAgeBetween(minAge, maxAge)).thenReturn(students);

        Collection<Student> foundStudents = studentService.findByAgeBetween(minAge, maxAge);

        assertThat(foundStudents).containsExactlyInAnyOrderElementsOf(students);
    }

    @Test
    @DisplayName("Получение всех студентов")
    void findAllStudent() {
        List<Student> students = List.of(
                TEST_STUDENT,
                TEST_STUDENT_2
        );

        when(studentRepository.findAll()).thenReturn(students);

        Collection<Student> foundStudents = studentService.findAllStudent();

        assertThat(foundStudents).containsExactlyInAnyOrderElementsOf(students);
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Получение факультета по ID студента")
    void getFacultyByStudentId() {
        long studentId = TEST_STUDENT.getId();
        Faculty faculty = TestConstants.TEST_FACULTY;

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(TEST_STUDENT));
        when(facultyRepository.findById(studentId)).thenReturn(Optional.of(faculty));

        Faculty foundFaculty = studentService.getFacultyByStudentId(studentId);

        assertThat(foundFaculty).isEqualTo(faculty);
        verify(studentRepository, times(1)).findById(studentId);
        verify(facultyRepository, times(1)).findById(studentId);
    }
}