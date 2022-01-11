package ru.nemolyakin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "subjects")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subject extends BaseEntity{

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subject")
    private List<Mark> marks;

    @ManyToMany(mappedBy = "subjects", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Student> students;
}
