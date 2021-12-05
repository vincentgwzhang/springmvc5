package org.personal.service;

import org.personal.data.entity.StudentEntity;
import org.personal.data.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(final StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<StudentEntity> loadStudentEntities() {
        return studentRepository.findAll();
    }

}
