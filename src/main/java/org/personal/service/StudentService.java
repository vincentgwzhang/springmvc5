package org.personal.service;

import org.personal.data.entity.StudentEntity;
import org.personal.data.entity.StudentWrapper;
import org.personal.data.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public record StudentService(StudentRepository studentRepository) {

    @Autowired
    public StudentService {
    }

    public List<StudentEntity> loadStudentEntities() {
        return studentRepository.findAll();
    }

    public StudentEntity createStudent(final StudentEntity studentEntity) {
        return this.studentRepository.save(studentEntity);
    }

    public StudentWrapper getStudents() {
        List<StudentEntity> studentEntities = this.studentRepository.findAll();
        return new StudentWrapper(studentEntities);
    }

    public Optional<StudentEntity> getStudentById(Integer id) {
        return this.studentRepository.findById(id);
    }

    public void deleteStudentById(Integer id) {
        this.studentRepository.deleteById(id);
    }

    public void deleteAllStudents() {
        this.studentRepository.deleteAll();
    }

    public StudentEntity updateStudent(StudentEntity studentEntity) {
        return this.studentRepository.save(studentEntity);
    }
}
