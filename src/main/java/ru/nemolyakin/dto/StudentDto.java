package ru.nemolyakin.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nemolyakin.model.Status;
import ru.nemolyakin.model.Student;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentDto extends BaseDtoEntity {

    private String lastName;
    private List<SubjectDto> subjects = new ArrayList<>();
    private Status status;

    public StudentDto(@NotNull Student student) {
        this.id = student.getId();
        this.lastName = student.getLastName();
        if (student.getSubjects() != null) {
            this.subjects = student.getSubjects().stream().map(subject -> new SubjectDto(subject, student)).collect(Collectors.toList());
        }
        this.status = student.getStatus();
    }

    public Student toStudent() {
        Student student = new Student();

        student.setId(this.id);
        student.setLastName(this.lastName);

        return student;
    }

}
