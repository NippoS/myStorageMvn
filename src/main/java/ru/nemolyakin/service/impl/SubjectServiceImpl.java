package ru.nemolyakin.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nemolyakin.model.Status;
import ru.nemolyakin.model.Subject;
import ru.nemolyakin.repository.SubjectRepository;
import ru.nemolyakin.service.SubjectService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    @Override
    public Subject save(Subject subject) {
        subject.setStatus(Status.ACTIVE);

        Subject subjectNew = subjectRepository.save(subject);

        log.info("IN SubjectServiceImpl save {}", subjectNew);

        return subjectNew;
    }

    @Override
    public List<Subject> findAll() {
        log.info("IN SubjectServiceImpl findAll");
        return subjectRepository.findAll();
    }

    @Override
    public Subject findById(Long id) throws EntityNotFoundException {
        log.info("IN SubjectServiceImpl find by id {}", id);
        return subjectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Subject with id %d is not found", id)));
    }

    @Override
    public void deleteById(Long id) throws EntityNotFoundException{
        Subject subjectDelete = subjectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Subject with id %d is not found", id)));

        subjectDelete.setStatus(Status.DELETED);

        subjectRepository.save(subjectDelete);

        log.info("IN SubjectServiceImpl delete subject with id {}", id);
    }

    @Override
    public Subject update(Subject subject) throws EntityNotFoundException {
        if (subject.getId() == null) {
            throw new NullPointerException("Id is required");
        }

        Subject subjectOld = subjectRepository.findById(subject.getId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Subject with that id %d is not found", subject.getId())));

        subject.setStatus(subjectOld.getStatus());
        Subject subjectUpdate = subjectRepository.save(subject);

        log.info("IN SubjectServiceImpl update subject {} was updated to subject {}", subjectOld, subject);

        return subjectUpdate;
    }
}