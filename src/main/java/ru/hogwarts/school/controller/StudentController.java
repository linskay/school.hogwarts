package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.OneToMany;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("/student")
@Tag(name = "Контроллер студентов", description = "Контроллеры для добавления студентов")
public class StudentController {

    private final StudentService studentService;

    @OneToMany(mappedBy = "student")
    private Faculty faculty;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * @param id идентификатор студента
     * @return возвращает id студента, если найден, иначе null+exception
     */

    @GetMapping("{id}/find-student")
    @Operation(summary = "Ищет студента",
            description = "Ищет студента по его id",
            responses = {@ApiResponse(responseCode = "404", description = "Студент не найден"),
                    @ApiResponse(responseCode = "200", description = "Студент найден")})
    public Student getFindStudent(@PathVariable long id) {
        return studentService.findStudent(id);
    }

    /**
     * @param student набор параметров студента для добавления
     * @return возвращает counter - установленный id
     */
    @PostMapping("/add-student")
    @Operation(summary = "Добавляет студента",
            description = "Добавляет студента и устанавливает id",
            responses = @ApiResponse(responseCode = "200", description = "Студент добавлен"))
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    /**
     * @param id      идентификатор студента
     * @param student студент для обновления
     * @return возвращает нового студента
     */
    @PutMapping("{id}/edite")
    @Operation(summary = "Обновляет студента",
            description = "Обновляет студента и устанавливает id",
            responses = {@ApiResponse(responseCode = "404", description = "Студент не найден"),
                    @ApiResponse(responseCode = "200", description = "Студент найден")})
    public Student editStudent(@PathVariable("id") long id,
                               @RequestBody Student student) {
        return studentService.editStudent(student);
    }

    /**
     * @param id идентификатор студента
     * @return возвращает удаленного студента или null, если студент с заданным id не был найден
     */
    @DeleteMapping("{id}/deleteFaculty")
    @Operation(summary = "Удаляет студента",
            responses = {@ApiResponse(responseCode = "404", description = "Студент не найден"),
                    @ApiResponse(responseCode = "200", description = "Студент удален")})

    public ResponseEntity deleteStudent(@PathVariable("id") long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    /**
     * @param age параметр возраст студента
     * @return возвращает коллекцию по указанному возрасту
     */
    @GetMapping("{age}/by-age")
    @Operation(summary = "Поиск по студентам",
            description = "Поиск по студентам указанного возраста",
            responses = @ApiResponse(responseCode = "200", description = "Коллекция сформирована"))
    public Collection<Student> getStudentsByAge(@PathVariable("age") int age) {
        return studentService.findByAgeBetween(age);
    }

    @GetMapping("/{id}/faculty-by-student")
    public Faculty getFacultyByStudentId(@PathVariable Long id) {
        return studentService.getFacultyByStudentId(id);
    }
}
