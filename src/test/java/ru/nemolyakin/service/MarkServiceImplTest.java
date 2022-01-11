package ru.nemolyakin.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.nemolyakin.model.Mark;
import ru.nemolyakin.model.Status;
import ru.nemolyakin.repository.MarkRepository;
import ru.nemolyakin.service.impl.MarkServiceImpl;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MarkServiceImplTest {

    @Mock
    MarkRepository markRepository;

    @InjectMocks
    MarkServiceImpl markServiceUnderTests;

    private Mark getMarkForTests() {
        Mark mark = new Mark();

        mark.setId(1L);
        mark.setMark(5);
        mark.setSubject(null);
        mark.setStudent(null);
        mark.setStatus(Status.ACTIVE);

        return mark;
    }

    @Test
    public void testGetByIdIsDone(){
        when(markRepository.findById(any())).thenReturn(Optional.of(getMarkForTests()));
        Mark mark = markServiceUnderTests.findById(1L);

        assertEquals(mark.getMark(), 5);

        verify(markRepository, times(1)).findById(any(Long.class));
    }

    @Test
    public void testGetByIdIsNotDone(){
        when(markRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> markServiceUnderTests.findById(1L));
    }

    @Test
    void testSaveIsDone() {
        when(markRepository.save(any())).thenReturn(getMarkForTests());
        Mark mark = markServiceUnderTests.save(getMarkForTests());

        assertEquals(mark.getMark(), 5);

        verify(markRepository, times(1)).save(any(Mark.class));
    }

    @Test
    void testSaveHaveException() {
        when(markRepository.save(any())).thenThrow(new IllegalArgumentException());

        assertThrows(IllegalArgumentException.class, () -> markServiceUnderTests.save(getMarkForTests()));
    }

    @Test
    void testUpdateIsDone() {
        Mark markFD = getMarkForTests();

        when(markRepository.findById(anyLong())).thenReturn(Optional.of(markFD));
        when(markRepository.save(any(Mark.class))).thenReturn(getMarkForTests());

        Mark markToUpdate = getMarkForTests();
        markToUpdate.setMark(3);

        markServiceUnderTests.update(markToUpdate);

        assertEquals(markToUpdate.getMark(), 3);
        verify(markRepository, times(1)).save(markToUpdate);
    }

    @Test
    void testUpdateWhenIdIsNotFound() {
        when(markRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> markServiceUnderTests.update(getMarkForTests()));
    }

    @Test
    void testDeleteById() {
        when(markRepository.findById(anyLong())).thenReturn(Optional.of(getMarkForTests()));

        markServiceUnderTests.deleteById(1L);

        verify(markRepository, times(1)).findById(anyLong());
        verify(markRepository, times(1)).save(any());
    }

    @Test
    void testDeleteByIdWhenStudentDoesNotFound() {
        when(markRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> markServiceUnderTests.deleteById(1L));
    }
}
