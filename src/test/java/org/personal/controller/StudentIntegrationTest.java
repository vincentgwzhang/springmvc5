package org.personal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.personal.WebMvcConfig;
import org.personal.data.entity.StudentEntity;
import org.personal.data.entity.StudentWrapper;
import org.personal.data.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@ActiveProfiles("test")
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { WebMvcConfig.class })
@Transactional // Auto rollback
public class StudentIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private final String controller_url_base = "/student";

    @Autowired
    private StudentRepository studentRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp()
    {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void testCreateStudent() throws Exception {
        StudentEntity studentEntity = newStudentEntity();

        MvcResult mvcResult = this.mockMvc.perform(
                post(controller_url_base)
                .content(objectMapper.writeValueAsString(studentEntity))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        final String strJsonResult = mvcResult.getResponse().getContentAsString();
        StudentEntity responseStudent = objectMapper.readValue(strJsonResult, StudentEntity.class);
        assertEquals(responseStudent, studentEntity);
    }

    @Test
    public void testGetStudents() throws Exception {
        StudentEntity studentEntity = newStudentEntity();
        studentRepository.save(studentEntity);

        MvcResult mvcResult = this.mockMvc.perform(get(controller_url_base)).andReturn();
        final String strJsonResult = mvcResult.getResponse().getContentAsString();
        StudentWrapper studentWrapper = objectMapper.readValue(strJsonResult, StudentWrapper.class);
        assertThat(studentWrapper.getStudentEntityList(), hasSize(1));
        assertThat(studentWrapper.getStudentEntityList().get(0), equalTo(studentEntity));
    }

    @Test
    public void testGetStudentById() throws Exception {
        StudentEntity studentEntity = newStudentEntity();
        studentEntity = studentRepository.save(studentEntity);

        MvcResult mvcResult = this.mockMvc.perform(get(controller_url_base + "/{id}", studentEntity.getId())).andReturn();
        final String strJsonResult = mvcResult.getResponse().getContentAsString();
        StudentEntity responseStudent = objectMapper.readValue(strJsonResult, StudentEntity.class);
        assertThat(responseStudent, equalTo(studentEntity));
    }

    @Test
    public void testDeleteStudentById() throws Exception {
        StudentEntity studentEntity = newStudentEntity();
        studentEntity = studentRepository.save(studentEntity);

        this.mockMvc
                .perform(delete(controller_url_base + "/{id}", studentEntity.getId()))
                .andExpect(status().isAccepted())
                .andReturn();

        Optional<StudentEntity> optionalStudent = studentRepository.findById(studentEntity.getId());
        assertTrue(optionalStudent.isEmpty());
    }

    @Test
    public void testDeleteAllStudents() throws Exception {
        StudentEntity studentEntity = newStudentEntity();
        studentRepository.save(studentEntity);

        this.mockMvc
                .perform(delete(controller_url_base))
                .andExpect(status().isAccepted())
                .andReturn();

        List<StudentEntity> studentEntities = studentRepository.findAll();
        assertThat(studentEntities, empty());
    }

    @Test
    public void testUpdateStudent() throws Exception {
        StudentEntity studentEntity = newStudentEntity();
        studentRepository.save(studentEntity);

        studentEntity.setName("A_ANOTHER_NAME");

        MvcResult mvcResult = this.mockMvc.perform(
                put(controller_url_base)
                        .content(objectMapper.writeValueAsString(studentEntity))
                        .contentType(MediaType.APPLICATION_JSON)).andReturn();

        final String strJsonResult = mvcResult.getResponse().getContentAsString();
        StudentEntity responseStudent = objectMapper.readValue(strJsonResult, StudentEntity.class);
        assertEquals(responseStudent, studentEntity);
    }

    private StudentEntity newStudentEntity() {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setAddress("testAddress");
        studentEntity.setCountry("testESP");
        studentEntity.setFramework("testFramework");
        studentEntity.setEmail("testEmail@email.com");
        studentEntity.setName("testName");
        studentEntity.setNumber("testNumber");
        studentEntity.setSex("M");
        studentEntity.setSkill("testSkill");
        studentEntity.setNewsletter(1);
        return studentEntity;
    }

















}
