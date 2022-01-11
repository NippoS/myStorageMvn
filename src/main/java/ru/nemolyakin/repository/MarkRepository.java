package ru.nemolyakin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nemolyakin.model.Mark;


public interface MarkRepository extends JpaRepository<Mark, Long> {

}
