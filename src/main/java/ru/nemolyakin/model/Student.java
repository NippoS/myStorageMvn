package ru.nemolyakin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student extends BaseEntity{

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "default 'ACTIVE'")
    private Status status = Status.ACTIVE;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "student")
    private List<Mark> marks;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "students_subjects",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private List<Subject> subjects;
}
