package org.personal.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.personal.WebMvcConfig;
import org.personal.data.entity.StudentEntity;
import org.personal.dto.RestfulEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { WebMvcConfig.class })
public class RestfulControllerTest
{
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private final String controller_url_base = "/rest";

    @BeforeEach
    public void setUp()
    {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void testPostEndpoint() throws Exception
    {
        final String requestPath = controller_url_base + "/post";
        MvcResult mvcResult = this.mockMvc.perform(post(requestPath)).andReturn();

        assertThat(mvcResult.getResponse().getStatus(), Matchers.equalTo(HttpStatus.CREATED.value()));
    }

    @Test
    public void testPutEndpoint() throws Exception
    {
        final String requestPath = controller_url_base + "/put";
        MvcResult mvcResult = this.mockMvc.perform(put(requestPath)).andReturn();

        assertThat(mvcResult.getResponse().getStatus(), Matchers.equalTo(HttpStatus.ACCEPTED.value()));
    }

    @Test
    public void testDeleteEndpoint() throws Exception
    {
        final String requestPath = controller_url_base + "/delete";
        MvcResult mvcResult = this.mockMvc.perform(delete(requestPath)).andReturn();

        assertThat(mvcResult.getResponse().getStatus(), Matchers.equalTo(HttpStatus.NO_CONTENT.value()));
    }
}
