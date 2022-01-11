package ru.nemolyakin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nemolyakin.model.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
