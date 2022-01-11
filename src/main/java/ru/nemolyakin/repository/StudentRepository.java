package ru.nemolyakin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nemolyakin.model.Student;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByLastName (String lastName);
}
