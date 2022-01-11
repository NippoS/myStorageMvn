package ru.nemolyakin.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.nemolyakin.model.Status;
import ru.nemolyakin.model.Subject;
import ru.nemolyakin.repository.SubjectRepository;
import ru.nemolyakin.service.impl.SubjectServiceImpl;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SubjectServiceImplTest {

    @Mock
    SubjectRepository subjectRepository;

    @InjectMocks
    SubjectServiceImpl subjectServiceUnderTests;

    private Subject getSubjectForTests() {
        Subject subject = new Subject();

        subject.setId(1L);
        subject.setName("TEST");
        subject.setStatus(Status.ACTIVE);
        subject.setStudents(null);
        subject.setMarks(null);

        return subject;
    }

    @Test
    public void testGetByIdIsDone(){
        when(subjectRepository.findById(any())).thenReturn(Optional.of(getSubjectForTests()));
        Subject subject = subjectServiceUnderTests.findById(1L);

        assertEquals(subject.getName(), "TEST");

        verify(subjectRepository, times(1)).findById(any(Long.class));
    }

    @Test
    public void testGetByIdIsNotDone(){
        when(subjectRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> subjectServiceUnderTests.findById(1L));
    }

    @Test
    void testSaveIsDone() {
        when(subjectRepository.save(any())).thenReturn(getSubjectForTests());
        Subject subject = subjectServiceUnderTests.save(getSubjectForTests());

        assertEquals(subject.getName(), "TEST");

        verify(subjectRepository, times(1)).save(any(Subject.class));
    }

    @Test
    void testSaveHaveException() {
        when(subjectRepository.save(any())).thenThrow(new IllegalArgumentException());

        assertThrows(IllegalArgumentException.class, () -> subjectServiceUnderTests.save(getSubjectForTests()));
    }

    @Test
    void testUpdateIsDone() {
        Subject subjectFD = getSubjectForTests();

        when(subjectRepository.findById(anyLong())).thenReturn(Optional.of(subjectFD));
        when(subjectRepository.save(any(Subject.class))).thenReturn(getSubjectForTests());

        Subject subjectToUpdate = getSubjectForTests();
        subjectToUpdate.setName("TESTUpdate");

        subjectServiceUnderTests.update(subjectToUpdate);

        assertEquals(subjectToUpdate.getName(), "TESTUpdate");
        verify(subjectRepository, times(1)).save(subjectToUpdate);
    }

    @Test
    void testUpdateWhenIdIsNotFound() {
        when(subjectRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> subjectServiceUnderTests.update(getSubjectForTests()));
    }

    @Test
    void testDeleteById() {
        when(subjectRepository.findById(anyLong())).thenReturn(Optional.of(getSubjectForTests()));

        subjectServiceUnderTests.deleteById(1L);

        verify(subjectRepository, times(1)).findById(anyLong());
        verify(subjectRepository, times(1)).save(any());
    }

    @Test
    void testDeleteByIdWhenStudentDoesNotFound() {
        when(subjectRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> subjectServiceUnderTests.deleteById(1L));
    }
}
