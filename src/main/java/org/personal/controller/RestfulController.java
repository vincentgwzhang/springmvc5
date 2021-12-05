package org.personal.controller;

import org.personal.data.entity.StudentEntity;
import org.personal.dto.RestfulEntity;
import org.personal.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("rest")
public class RestfulController
{
    @Autowired
    private StudentService studentService;

    @GetMapping("get")
    public List<StudentEntity> getEndpoint() {
        return studentService.loadStudentEntities();
    }

    @PutMapping("put")
    public ResponseEntity putEndpoint() {
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("delete")
    public ResponseEntity deleteEndpoint() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("post")
    public ResponseEntity postEndpoint() {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
