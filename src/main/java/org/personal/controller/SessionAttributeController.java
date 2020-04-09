package org.personal.controller;

import java.util.Map;
import org.personal.dto.Address;
import org.personal.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import static org.personal.controller.SessionAttributeController.SESSION_USER_KEY;

/**
 * 解释： 如果有一个 map.put("user"), 而这里 SessionAttributes 有同样的 “user”, 那么就会寄存到 session 中去
 * 而如果使用SessionAttributes 的type, 那么就是凡是该类型的都会放到 session
 */
@SessionAttributes(value={SESSION_USER_KEY}, types = {String.class})
@Controller
@RequestMapping("sessionAttribute")
public class SessionAttributeController
{
    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final String BASE_URL = "sessionAttribute";

    public static final String SESSION_USER_KEY = "user";

    @GetMapping
    public String index() {
        return BASE_URL;
    }

    @GetMapping("case1")
    public String testSession1(Map<String, Object> map) {
        final User user = newUser();
        map.put(SESSION_USER_KEY, user);
        map.put("school", "ZhuHai");
        logger.info("SessionAttributeController::testSession1");
        return BASE_URL;
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
