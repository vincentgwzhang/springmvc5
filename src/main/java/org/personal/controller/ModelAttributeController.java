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
 * ModelAttribute 的意思是说，例如这个 user, 它在JSP 页面的时候根本就不显示 password, 甚至 hidden 都无
 * 然后由于传回来的时候有个id, 而这个 id 马上被 loadUser 获取到，然后从数据库获取到一个 db-user
 * 然后这个 db-user 就会被 updateUser 函数里面的 user 覆盖，所以到最后， password 实质上仍然是从数据库获取到，但是缺无法修改
 *
 * ModelAttribute有时候会和SessionAttributes混淆并产生异常。例如，
 * 1, 当目标方法，updateUser, 使用了 @ModelAttribute参数，
 * 2, 然后没有 @ModelAttribute所标注的方法或者即使标注了， 所标注的方法没有 updateUser 所要求的 key 输入到 map 中，例如，没有这句：  map.put(MODEL_USER_ATTRIBUTE_NAME, user);
 * 3, 然后，这里又有SessionAttributes的说话，就会去sessionAttributes 去找。如果还是找不到，那么就抛出异常。
 *
 * 为什么呢？因为根据 SessionAttributes 的特性，它会自动捕捉 map 曾经注入过的 key
 *
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
    public void loadUser(@RequestParam(value = "id", required = false) Integer id, Map<String, Object> map) {
        if (id != null) {
            User user = newUser();
            map.put(MODEL_USER_ATTRIBUTE_NAME, user);//注意，这里默认应该是 user
            logger.info("Load from DB, user = {}", user);
        }
    }

    /**
     * 由 ModelAttribute 标记的方法，会在每个endpoint 执行前调用
     */
    @ModelAttribute
    public void executeBeforeMethod() {
        logger.info("ModelAttributeController::executeBeforeMethod");
    }

    @PostMapping("post")
    public String updateUser(@ModelAttribute(MODEL_USER_ATTRIBUTE_NAME) User user, ModelMap modelMap) {
        logger.info("ModelAttributeController::updateUser, user = {}", user);
        logger.info("ModelAttributeController::updateUser, modelMap has key = {}", modelMap.containsKey(MODEL_USER_ATTRIBUTE_NAME));
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
