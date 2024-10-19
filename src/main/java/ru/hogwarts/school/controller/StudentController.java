package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.impl.StudentServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/student")
@Tag(name = "Контроллер студентов", description = "Контроллеры для добавления студентов")
public class StudentController {

    private final StudentServiceImpl studentServiceImpl;

    public StudentController(StudentServiceImpl studentServiceImpl) {
        this.studentServiceImpl = studentServiceImpl;
    }

    /**
     *
     * @param id идентификатор студента
     * @return возвращает id студента, если найден, иначе null+exception
     */

    @GetMapping("{id}/find-student")
    @Operation(summary = "Ищет студента",
            description = "Ищет студента по его id",
            responses = {@ApiResponse(responseCode = "404", description = "Студент не найден"),
                    @ApiResponse(responseCode = "200", description = "Студент найден")})
    public Student getFindStudent(@PathVariable long id) {
        return studentServiceImpl.findStudent(id);
    }

    /**
     *
     * @param student набор параметров студента для добавления
     * @return возвращает counter - установленный id
     */
    @PostMapping("/add-student")
    @Operation(summary = "Добавляет студента",
            description = "Добавляет студента и устанавливает id",
            responses = @ApiResponse(responseCode = "200", description = "Студент добавлен"))
    public long createStudent(@RequestBody Student student) {
        return studentServiceImpl.createStudent(student);
    }

    /**
     *
     * @param id идентификатор студента
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
        return studentServiceImpl.editStudent(id, student);
    }

    /**
     *
     * @param id идентификатор студента
     * @return возвращает удаленного студента или null, если студент с заданным id не был найден
     */
    @DeleteMapping("{id}/deleteFaculty")
    @Operation(summary = "Удаляет студента",
            responses = {@ApiResponse(responseCode = "404", description = "Студент не найден"),
                    @ApiResponse(responseCode = "200", description = "Студент удален")})

    public Student deleteStudent(@PathVariable("id") long id) {
        return studentServiceImpl.deleteStudent(id);
    }

    /**
     *
     * @param age параметр возраст студента
     * @return возвращает коллекцию по указанному возрасту
     */
    @GetMapping("{age}/by-age")
    @Operation(summary = "Поиск по студентам",
            description = "Поиск по студентам указанного возраста",
            responses = @ApiResponse(responseCode = "200", description = "Коллекция сформирована"))
    public List<Student> getStudentsByAge(@PathVariable("age") int age) {
        return studentServiceImpl.filterByAge(age);
    }
}

