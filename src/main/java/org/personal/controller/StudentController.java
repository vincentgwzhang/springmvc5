package org.personal.controller;

import org.personal.data.entity.StudentEntity;
import org.personal.data.entity.StudentWrapper;
import org.personal.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(final StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    public ResponseEntity<StudentEntity> getStudentById(@PathVariable Integer id) {
        Optional<StudentEntity> optionalStudent = this.studentService.getStudentById(id);
        if (optionalStudent.isPresent()) {
            return new ResponseEntity<>(optionalStudent.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<StudentWrapper> getStudents() {
        StudentWrapper studentWrapper = this.studentService.getStudents();
        return new ResponseEntity<>(studentWrapper, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<StudentEntity> createStudent(@RequestBody StudentEntity studentEntity) {
        StudentEntity student = this.studentService.createStudent(studentEntity);
        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteStudentById(@PathVariable Integer id) {
        this.studentService.deleteStudentById(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping
    public ResponseEntity deleteAllStudents() {
        this.studentService.deleteAllStudents();
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping
    public ResponseEntity<StudentEntity> updateStudent(@RequestBody StudentEntity studentEntity) {
        StudentEntity student = this.studentService.updateStudent(studentEntity);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

}
