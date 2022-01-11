package ru.nemolyakin.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import ru.nemolyakin.dto.SubjectDto;
import ru.nemolyakin.model.Subject;
import ru.nemolyakin.service.SubjectService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/subjects")
public class SubjectRestControllerV1 {

    private final SubjectService subjectService;

    @GetMapping
    public ResponseEntity<List<SubjectDto>> getAllSubjects() {
        List<Subject> subjects = subjectService.findAll();

        if (CollectionUtils.isEmpty(subjects)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<SubjectDto> subjectDtos = subjects.stream().map(SubjectDto::new).collect(Collectors.toList());

        return new ResponseEntity<>(subjectDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectDto> getSubjectById(@NonNull @PathVariable("id") Long id) {
        Subject subject = subjectService.findById(id);
        return new ResponseEntity<>(new SubjectDto(subject), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SubjectDto> saveSubject(@NonNull @RequestBody @Valid SubjectDto subjectDto){
        Subject subject = subjectDto.toSubject();
        Subject subjectNew =  subjectService.save(subject);
        return new ResponseEntity<>(new SubjectDto(subjectNew), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<SubjectDto> updateSubject(@NonNull @RequestBody @Valid SubjectDto subjectDto){
        Subject subject = subjectDto.toSubject();
        Subject subjectUpdate =  subjectService.update(subject);
        return new ResponseEntity<>(new SubjectDto(subjectUpdate), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SubjectDto> deleteSubject(@NonNull @PathVariable("id") Long id){
        subjectService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
