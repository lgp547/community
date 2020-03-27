package com.study.community.advice;

import com.study.community.dto.ResultDTO;
import com.study.community.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

//这个是异常捕捉控制类
@ControllerAdvice
public class CustomizeExceptionHandler {
    //这个是指定捕抓那些异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    Object handle(HttpServletRequest request, Throwable e, Model model) {
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)){
            //返回JSON
            if (e instanceof CustomizeException){
                return ResultDTO.errorOf((CustomizeException)e);
            } else {
                //return ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
                return e;
            }
        } else {
            //错误页面
            //instanceof是判断e是否属于CustomizeException类
            if (e instanceof CustomizeException){
                model.addAttribute("message",e.getMessage());
            } else {
//                model.addAttribute("message",CustomizeErrorCode.SYS_ERROR.getMessage());
                return e;
            }
            return new ModelAndView("error");
        }
    }
}
