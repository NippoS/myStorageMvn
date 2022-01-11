package ru.nemolyakin.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import ru.nemolyakin.dto.MarkDto;
import ru.nemolyakin.model.Mark;
import ru.nemolyakin.model.Student;
import ru.nemolyakin.model.Subject;
import ru.nemolyakin.service.MarkService;
import ru.nemolyakin.service.StudentService;
import ru.nemolyakin.service.SubjectService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/marks")
public class MarkRestControllerV1 {

    private final SubjectService subjectService;
    private final MarkService markService;
    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<List<MarkDto>> getAllMarks() {
        List<Mark> marks = markService.findAll();

        if (CollectionUtils.isEmpty(marks)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<MarkDto> markDtos = marks.stream().map(MarkDto::new).collect(Collectors.toList());

        return new ResponseEntity<>(markDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarkDto> getMarkById(@NonNull @PathVariable("id") Long id) {
        Mark mark = markService.findById(id);
        return new ResponseEntity<>(new MarkDto(mark), HttpStatus.OK);
    }

    //Выбрана передача student и subject по Id для примера, соответственно можно передать сразу объект
    @PostMapping
    public ResponseEntity<MarkDto> saveMark(@NonNull @RequestBody @Valid MarkDto markDto){
        Mark mark = getFullMark(markDto);
        Mark markNew =  markService.save(mark);
        return new ResponseEntity<>(new MarkDto(markNew), HttpStatus.CREATED);
    }

    //Выбрана передача student и subject по Id для примера, соответственно можно передать сразу объект
    @PutMapping
    public ResponseEntity<MarkDto> updateMark(@NonNull @RequestBody @Valid MarkDto markDto){
        Mark mark = getFullMark(markDto);
        Mark markUpdate =  markService.update(mark);
        return new ResponseEntity<>(new MarkDto(markUpdate), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MarkDto> deleteMark(@NonNull @PathVariable("id") Long id){
        markService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private Mark getFullMark(MarkDto markDto){
        Mark mark = markDto.toMark();
        Student student = studentService.findById(markDto.getStudentId());
        Subject subject = subjectService.findById(markDto.getSubjectId());
        mark.setStudent(student);
        mark.setSubject(subject);

        return mark;
    }
}
