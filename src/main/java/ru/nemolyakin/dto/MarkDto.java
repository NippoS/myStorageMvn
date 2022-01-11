package ru.nemolyakin.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nemolyakin.model.Mark;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MarkDto extends BaseDtoEntity {

    @Min(value = 2, message = "Оценка должна быть не меньше 2")
    @Max(value = 5, message = "Оценка должна быть не больше 5")
    private int mark;
    private Long studentId;
    private Long subjectId;

    public MarkDto(@NotNull Mark mark) {
        this.id = mark.getId();
        this.mark = mark.getMark();
    }

    public Mark toMark() {
        Mark mark = new Mark();

        mark.setId(this.id);
        mark.setMark(this.mark);

        return mark;
    }

}
