package ru.nemolyakin.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nemolyakin.model.Status;
import ru.nemolyakin.model.Student;
import ru.nemolyakin.model.Subject;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubjectDto extends BaseDtoEntity {

    private String name;
    private List<MarkDto> marks = new ArrayList<>();
    private Status status;

    public SubjectDto(@NotNull Subject subject) {
        this.id = subject.getId();
        this.name = subject.getName();
        this.status = subject.getStatus();
    }

    public SubjectDto(@NotNull Subject subject, Student student) {
        this.id = subject.getId();
        this.name = subject.getName();
        this.marks = student.getMarks()
                .stream()
                .filter(mark -> mark.getSubject().equals(subject))
                .map(MarkDto::new)
                .collect(Collectors.toList());
        this.status = subject.getStatus();
    }

    public Subject toSubject() {
        Subject subject = new Subject();

        subject.setId(this.id);
        subject.setName(this.name);

        return subject;
    }

}
