package org.personal.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.personal.WebMvcConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { WebMvcConfig.class })
public class RedirectControllerTest
{
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private final String controller_url_base = "/redirect";

    @BeforeEach
    public void setUp()
    {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void redirectController_testRedirect() throws Exception
    {
        final String requestPath = controller_url_base + "/testRedirect";
        MvcResult mvcResult = this.mockMvc.perform(get(requestPath)).andReturn();
        assertThat(mvcResult.getModelAndView(), Matchers.notNullValue());
        assertThat(mvcResult.getModelAndView().getViewName(), Matchers.equalTo("redirect:/redirect/commonView"));
    }

    @Test
    public void redirectController_testForward() throws Exception
    {
        final String requestPath = controller_url_base + "/testForward";
        MvcResult mvcResult = this.mockMvc.perform(get(requestPath)).andReturn();
        assertThat(mvcResult.getModelAndView(), Matchers.notNullValue());
        assertThat(mvcResult.getModelAndView().getViewName(), Matchers.equalTo("forward:/redirect/commonView"));
    }
}
