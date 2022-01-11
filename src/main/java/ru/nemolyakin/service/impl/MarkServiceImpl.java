package ru.nemolyakin.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nemolyakin.model.Mark;
import ru.nemolyakin.model.Status;
import ru.nemolyakin.repository.MarkRepository;
import ru.nemolyakin.service.MarkService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class MarkServiceImpl implements MarkService {

    private MarkRepository markRepository;


    @Override
    public Mark save(Mark mark) {
        mark.setStatus(Status.ACTIVE);

        Mark markNew = markRepository.save(mark);

        log.info("IN MarkServiceImpl save {}", markNew);

        return markNew;
    }

    @Override
    public List<Mark> findAll() {
        log.info("IN MarkServiceImpl findAll");
        return markRepository.findAll();
    }

    @Override
    public Mark findById(Long id) throws EntityNotFoundException {
        log.info("IN MarkServiceImpl find by id {}", id);
        return markRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Mark with id %d is not found", id)));
    }

    @Override
    public void deleteById(Long id) {
        Mark markDelete = markRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Mark with id %d is not found", id)));

        markDelete.setStatus(Status.DELETED);

        markRepository.save(markDelete);

        log.info("IN MarkServiceImpl delete mark with id {}", id);
    }

    @Override
    public Mark update(Mark mark) {
        if (mark.getId() == null) {
            throw new NullPointerException("Id is required");
        }

        Mark markOld = markRepository.findById(mark.getId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Mark with that id %d is not found", mark.getId())));

        mark.setStatus(markOld.getStatus());
        Mark markUpdate = markRepository.save(mark);

        log.info("IN MarkServiceImpl update mark {} was updated to mark {}", markOld, mark);

        return markUpdate;
    }
}
