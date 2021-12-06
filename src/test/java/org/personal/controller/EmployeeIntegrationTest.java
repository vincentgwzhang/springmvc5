package org.personal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.personal.WebMvcConfig;
import org.personal.data.employee.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.personal.controller.EmployeeController.INDEX_PAGE;
import static org.personal.controller.EmployeeController.INSERT_PAGE;
import static org.personal.controller.EmployeeController.LIST_PAGE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@ActiveProfiles("test")
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WebMvcConfig.class})
@Transactional // Auto rollback
public class EmployeeIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private final String controller_url_base = "/employee";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void testGotoEmployeePage() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get(controller_url_base)).andReturn();
        assertThat(mvcResult.getModelAndView().getViewName(), equalTo(INDEX_PAGE));
        assertThat(mvcResult.getResponse().getForwardedUrl(), containsString("employee/index.jsp"));
        assertTrue(mvcResult.getModelAndView().getModel().containsKey("genders"));
        assertTrue(mvcResult.getModelAndView().getModel().containsKey("departments"));
    }

    @Test
    public void testListEmployeePage() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get(controller_url_base + "/get")).andReturn();
        assertThat(mvcResult.getModelAndView().getViewName(), equalTo(LIST_PAGE));
        assertThat(mvcResult.getResponse().getForwardedUrl(), containsString("employee/list.jsp"));
        assertTrue(mvcResult.getModelAndView().getModel().containsKey("employees"));
    }

    @Test
    public void testGotoCreateEmployeePage() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get(controller_url_base + "/create")).andReturn();
        assertThat(mvcResult.getModelAndView().getViewName(), equalTo(INSERT_PAGE));
        assertThat(mvcResult.getResponse().getForwardedUrl(), containsString("employee/input.jsp"));
        assertTrue(mvcResult.getModelAndView().getModel().containsKey("org.springframework.validation.BindingResult.employee"));
        assertTrue(mvcResult.getModelAndView().getModel().containsKey("employee"));

        BeanPropertyBindingResult bindingResult = (BeanPropertyBindingResult)mvcResult.getModelAndView().getModel().get("org.springframework.validation.BindingResult.employee");
        assertThat(bindingResult.getErrorCount(), Matchers.equalTo(0));
    }

}
