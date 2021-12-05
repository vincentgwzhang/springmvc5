package org.personal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.personal.WebMvcConfig;
import org.personal.data.entity.StudentEntity;
import org.personal.data.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { WebMvcConfig.class })
public class StudentIntegrationTesting {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private final String controller_url_base = "/rest";

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    public void setUp()
    {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void testGetEndpoint() throws Exception
    {
        StudentEntity studentEntity = newStudentEntity();
        studentRepository.save(studentEntity);

        final String requestPath = controller_url_base + "/get";
        MvcResult mvcResult = this.mockMvc.perform(get(requestPath)).andReturn();

        final String strJsonResult = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        List<StudentEntity> studentEntities = Arrays.asList(objectMapper.readValue(strJsonResult, StudentEntity[].class));
        assertTrue(studentEntities.contains(studentEntity));
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
