package ru.nemolyakin.service;

import ru.nemolyakin.model.Student;

public interface StudentService extends GenericService<Student, Long>{
    Student findByLastName(String lastName);
}
