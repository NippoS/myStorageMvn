package ru.nemolyakin.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import ru.nemolyakin.dto.StudentDto;
import ru.nemolyakin.model.Student;
import ru.nemolyakin.service.StudentService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/students")
public class StudentRestControllerV1 {

    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        List<Student> students = studentService.findAll();

        if (CollectionUtils.isEmpty(students)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<StudentDto> studentDtos = students.stream().map(StudentDto::new).collect(Collectors.toList());

        return new ResponseEntity<>(studentDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudentById(@NonNull @PathVariable("id") Long id) {
        Student student = studentService.findById(id);
        return new ResponseEntity<>(new StudentDto(student), HttpStatus.OK);
    }

    @PostMapping("/lastname")
    public ResponseEntity<StudentDto> getStudentByLastName(@NonNull @RequestBody @Valid StudentDto studentDto) {
        Student student = studentService.findByLastName(studentDto.getLastName());
        return new ResponseEntity<>(new StudentDto(student), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<StudentDto> saveStudent(@NonNull @RequestBody @Valid StudentDto studentDto){
        Student student = studentDto.toStudent();
        Student studentNew =  studentService.save(student);
        return new ResponseEntity<>(new StudentDto(studentNew), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<StudentDto> updateStudent(@NonNull @RequestBody @Valid StudentDto studentDto){
        Student student = studentDto.toStudent();
        Student studentUpdate =  studentService.update(student);
        return new ResponseEntity<>(new StudentDto(studentUpdate), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StudentDto> deleteStudent(@NonNull @PathVariable("id") Long id){
        studentService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
