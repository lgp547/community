package com.study.community.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常处理类p35
 */

@Controller
//地址实际就是/error 这个是仿照BasicErrorController的写的
//查看它的值可以在application.properties里面查看默认值
@RequestMapping("${server.error.path:${error.path:/error}}")
public class CustomizeErrorController implements ErrorController {
    @Override
    public String getErrorPath() {
        return "error";
    }

    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(HttpServletRequest request, Model model) {
        //抄写源码来的，为了实现其他错误也捕获
        HttpStatus status = getStatus(request);

        if (status.is4xxClientError()) {
            model.addAttribute("message", "你这个请求错了吧，要不然换个姿势？");
        }
        if (status.is5xxServerError()) {
            model.addAttribute("message", "服务器冒烟了,要不等会再试试");
        }
        return new ModelAndView("error");
    }

    /**
     * 抄写源码来的
     * @param request
     * @return
     */
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        } catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

}
