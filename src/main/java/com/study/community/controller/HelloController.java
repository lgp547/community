package com.study.community.controller;

import com.study.community.mapper.UserMapper;
import com.study.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class HelloController {

    @Autowired(required = false)
    private UserMapper userMapper;

    /**
     * 响应根目录
     */
    @GetMapping("/")
    public String index(HttpServletRequest request) {
        //每次进来都进行查询token，和数据库匹配即是登陆
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    User user = userMapper.findByToken(cookie.getValue());
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }

        return "index";
    }

//    @GetMapping("/hello")
//    public String hello(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
//        model.addAttribute("name", name);
//        return "hello";
//    }
//    页面响应：
//    <!DOCTYPE HTML>
//<html xmlns:th="http://www.thymeleaf.org">
//<head>
//    <title>Getting Started: Serving Web Content</title>
//    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
//</head>
//<body>
//<p th:text="'Hello, ' + ${name} + '!'" />
//</body>
//</html>
}