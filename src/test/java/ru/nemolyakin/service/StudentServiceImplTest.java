package ru.nemolyakin.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.nemolyakin.model.Status;
import ru.nemolyakin.model.Student;
import ru.nemolyakin.repository.StudentRepository;
import ru.nemolyakin.service.impl.StudentServiceImpl;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceImplTest {

    @Mock
    StudentRepository studentRepository;

    @InjectMocks
    StudentServiceImpl studentServiceUnderTests;

    private Student getStudentForTests() {
        Student student = new Student();

        student.setId(1L);
        student.setLastName("Kirix");
        student.setStatus(Status.ACTIVE);
        student.setMarks(null);
        student.setSubjects(null);

        return student;
    }

    @Test
    public void testGetByIdIsDone(){
        when(studentRepository.findById(any())).thenReturn(Optional.of(getStudentForTests()));
        Student student = studentServiceUnderTests.findById(1L);

        assertEquals(student.getLastName(), "Kirix");

        verify(studentRepository, times(1)).findById(any(Long.class));
    }

    @Test
    public void testGetByIdIsNotDone(){
        when(studentRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> studentServiceUnderTests.findById(1L));
    }

    @Test
    public void testGetByLastNameIsDone(){
        when(studentRepository.findByLastName(anyString())).thenReturn(Optional.of(getStudentForTests()));

        Student student = studentServiceUnderTests.findByLastName("Kirix");

        assertEquals(student.getLastName(), "Kirix");

        verify(studentRepository, times(1)).findByLastName(any(String.class));
    }

    @Test
    public void testGetByLastNameIsNotFound() {
        when(studentRepository.findByLastName(anyString())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> studentServiceUnderTests.findByLastName("Kirix"));
    }

    @Test
    void testSaveIsDone() {
        when(studentRepository.save(any())).thenReturn(getStudentForTests());
        Student student = studentServiceUnderTests.save(getStudentForTests());

        assertEquals(student.getLastName(), "Kirix");

        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void testSaveHaveException() {
        when(studentRepository.save(any())).thenThrow(new IllegalArgumentException());

        assertThrows(IllegalArgumentException.class, () -> studentServiceUnderTests.save(getStudentForTests()));
    }

    @Test
    void testUpdateIsDone() {
        Student studentFD = getStudentForTests();

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(studentFD));
        when(studentRepository.save(any(Student.class))).thenReturn(getStudentForTests());

        Student studentToUpdate = getStudentForTests();
        studentToUpdate.setLastName("KirixUpdate");

        studentServiceUnderTests.update(studentToUpdate);

        assertEquals(studentToUpdate.getLastName(), "KirixUpdate");
        verify(studentRepository, times(1)).save(studentToUpdate);
    }

    @Test
    void testUpdateWhenIdIsNotFound() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> studentServiceUnderTests.update(getStudentForTests()));
    }

    @Test
    void testDeleteById() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(getStudentForTests()));

        studentServiceUnderTests.deleteById(1L);

        verify(studentRepository, times(1)).findById(anyLong());
        verify(studentRepository, times(1)).save(any());
    }

    @Test
    void testDeleteByIdWhenStudentDoesNotFound() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> studentServiceUnderTests.deleteById(1L));
    }
}
