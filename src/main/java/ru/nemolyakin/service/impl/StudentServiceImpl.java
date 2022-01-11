package ru.nemolyakin.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nemolyakin.model.Status;
import ru.nemolyakin.model.Student;
import ru.nemolyakin.repository.StudentRepository;
import ru.nemolyakin.service.StudentService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    @Override
    public Student save(Student student) {
        Student studentNew = studentRepository.save(student);

        log.info("IN StudentServiceImpl save {}", studentNew);

        return studentNew;
    }

    @Override
    public List<Student> findAll() {
        log.info("IN StudentServiceImpl findAll");
        return studentRepository.findAll();
    }

    @Override
    public Student findById(Long id) throws EntityNotFoundException {
        log.info("IN StudentServiceImpl find by id {}", id);
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Student with id %d is not found", id)));
    }

    @Override
    public void deleteById(Long id) throws EntityNotFoundException{
        Student studentDelete = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Student with id %d is not found", id)));

        studentDelete.setStatus(Status.DELETED);

        studentRepository.save(studentDelete);

        log.info("IN StudentServiceImpl delete student with id {}", id);
    }

    @Override
    public Student update(Student student) throws EntityNotFoundException{
        if (student.getId() == null) {
            throw new NullPointerException("Id is required");
        }

        Student studentOld = studentRepository.findById(student.getId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Student with that id %d is not found", student.getId())));

        student.setStatus(studentOld.getStatus());
        Student studentUpdate = studentRepository.save(student);

        log.info("IN StudentServiceImpl update student {} was updated to student {}", studentOld, student);

        return studentUpdate;
    }

    @Override
    public Student findByLastName(String lastName) throws EntityNotFoundException{
        Student student = studentRepository.findByLastName(lastName)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Student with last name %s is not found", lastName)));

        return student;
    }
}
