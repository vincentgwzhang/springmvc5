package org.personal.controller;

import java.util.Map;
import org.personal.dto.Address;
import org.personal.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ModelAttribute 使用场景：
 * 如果 user 是更新的话，如果其中有一个字段不能被修改，所以表单保存这个字段
 *
 * 下面是一个测试：
 * 1, 输入 http://localhost:22900/test/modelAttribute?id=1
 * 2, 此时，loadUser() 函数运行，并且模拟从数据库获取 user (因为此时 request parameter id = 1)
 * 3, 在表单处是没有 password 和 address 输入的
 * 4, 当点击 post 的时候，该 ModelAttributeController 首先依然运行 loadUser() 函数
 * 5, 然后再结合表单传回来的 user (表单的 user 传回来的时候 password 和 address为 null)
 * 6, 所以最后 password 和 address 并没有为 null
 */
@RequestMapping("modelAttribute")
@Controller
public class ModelAttributeController
{
    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final String BASE_URL = "modelAttribute";

    private static final String MODEL_USER_ATTRIBUTE_NAME = "userKey";

    @GetMapping
    public String index() {
        logger.info("ModelAttributeController::index");
        return BASE_URL;
    }

    /**
     * 由 ModelAttribute 标记的方法，会在每个endpoint 执行前调用
     */
    @ModelAttribute
    public void loadUser(@RequestParam(value = "id", required = false) Integer id, Map<String, User> map) {
        if (id != null) {
            User user = newUser();
            map.put(MODEL_USER_ATTRIBUTE_NAME, user);//注意，这里默认应该是 user
            logger.info("Load from DB, user = {}", user);
        } else {
            User user = new User();
            map.put(MODEL_USER_ATTRIBUTE_NAME, user);//注意，这里默认应该是 user
            logger.info("New user = {}", user);
        }
    }

    @PostMapping("post")
    public String updateUser(@ModelAttribute(MODEL_USER_ATTRIBUTE_NAME) User user, ModelMap modelMap) {
        // 此时，应该判断 user 的 id 是否为 null, 为 null 则为新的 user
        logger.info("ModelAttributeController::updateUser, user = {}", user);
        return BASE_URL;
    }

    private User newUser()
    {
        final User user = new User();
        user.setId(1);
        user.setAge(12);
        user.setEmail("tom@atguigu.com");
        user.setPassword("password");
        user.setUsername("tom");

        final Address address = new Address();
        address.setCity("city");
        address.setProvince("province");
        user.setAddress(address);
        return user;
    }


}
