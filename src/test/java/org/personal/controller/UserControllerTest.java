package org.personal.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.personal.WebMvcConfig;
import org.personal.controller.util.MockMvcRequestBuilderUtils;
import org.personal.dto.Address;
import org.personal.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { WebMvcConfig.class })
public class UserControllerTest
{
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private String controller_url_base = "/user";

    @BeforeEach
    public void setUp()
    {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void userController_FormSubmit() throws Exception
    {
        User user = newUser();
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilderUtils.postForm(controller_url_base + "/post", user)).andReturn();
        assertThat(mvcResult.getModelAndView().getViewName(), Matchers.equalTo("user"));
    }

    @Test
    public void userController_FormSubmit2() throws Exception
    {
        User user = newUser();
        MvcResult mvcResult = this.mockMvc
                .perform(
                        post(controller_url_base + "/post")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "username")
                        .param("password", "password")
                        .param("email", "abc@def.com")
                        .param("age", "15")
                        .param("address.city", "city")
                        .param("address.province", "province")
                )
                .andReturn();
        assertThat(mvcResult.getModelAndView().getViewName(), Matchers.equalTo("user"));
        User modelUser = (User)mvcResult.getModelAndView().getModelMap().getAttribute("userAttribute");
        assertThat(modelUser, Matchers.equalTo(user));
    }

    private User newUser()
    {
        final User user = new User();
        user.setAge(15);
        user.setEmail("abc@def.com");
        user.setPassword("password");
        user.setUsername("username");

        final Address address = new Address();
        address.setCity("city");
        address.setProvince("province");
        user.setAddress(address);
        return user;
    }
}
