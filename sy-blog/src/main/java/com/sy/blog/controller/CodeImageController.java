package com.sy.blog.controller;

import com.sy.blog.utils.CodeUtil;
import com.sy.blog.utils.reids.RedisUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

@Controller
public class CodeImageController {

    @RequestMapping(value = "/imgs",method = RequestMethod.GET)
    public void authImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        // 生成随机大写字串
        String verifyCode = CodeUtil.generateVerifyCode(4);
        //存入redis,有效时间为3分钟
        RedisUtil.putSomething(verifyCode,60*3,"ok");
        // 存入会话session
//        HttpSession session = request.getSession(true);
        // 删除以前的
//        session.removeAttribute("verCode");
//        session.removeAttribute("codeTime");
//        session.setAttribute("verCode", verifyCode.toLowerCase());
//        session.setAttribute("codeTime", new Date());
        // 生成图片
        int w = 116, h = 38;
        OutputStream out = response.getOutputStream();
        CodeUtil.outputImage(w, h, out, verifyCode);
    }
}
