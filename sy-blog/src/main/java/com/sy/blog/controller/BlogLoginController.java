package com.sy.blog.controller;

import com.alibaba.fastjson.JSONObject;
import com.sy.blog.service.BlogUserService;
import com.sy.blog.utils.HttpServletUtil;
import com.sy.blog.utils.JsonMaker;
import com.sy.blog.utils.reids.RedisUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class BlogLoginController {
    private Logger logger = Logger.getLogger(BlogUserController.class);

    @Autowired
    private BlogUserService blogUserService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public void login (HttpServletResponse res, HttpServletRequest request, @RequestBody Map<String,Object> data){
        //数据打包格式化
        JSONObject param = JsonMaker.noDuang(res,request,data);
        JSONObject jsonObject = new JSONObject();
        if(param == null) {
            jsonObject.put("code",404);
            jsonObject.put("msg","别搞事");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        if (!param.containsKey("userName")) {
            jsonObject.put("code",400);
            jsonObject.put("msg","请填写邮箱");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        if (!param.containsKey("password")) {
            jsonObject.put("code",400);
            jsonObject.put("msg","请填写密码");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        if (!param.containsKey("check") || param.getString("check").equals("")) {
            jsonObject.put("code",400);
            jsonObject.put("msg","请填写验证码");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        //检查验证码
//        System.out.println(RedisUtil.getSomething(param.getString("check")));
//        System.out.println(RedisUtil.getSomething(param.getString("check")).equals("ok"));
        if(RedisUtil.getSomething(param.getString("check")).equals("ok")) {
            jsonObject = blogUserService.login(param);
            RedisUtil.moveSomething(param.getString("check"));
        } else {
            jsonObject.put("code",400);
            jsonObject.put("msg","验证码不正确");
        }
        HttpServletUtil.response(request,res,jsonObject);
    }

    @RequestMapping(value = "/loginStatus",method = RequestMethod.POST)
    public void loginStatus (HttpServletResponse res, HttpServletRequest request){
        //数据打包格式化
        JSONObject param = JsonMaker.checkingNoAdmin(res,request);
        JSONObject jsonObject = new JSONObject();
        if(param == null) {
            jsonObject.put("code",404);
            jsonObject.put("msg","没有登录呢");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        jsonObject.put("data",param);
        jsonObject.put("code",200);
        jsonObject.put("msg","已登录");
        HttpServletUtil.response(request,res,jsonObject);
    }

    @RequestMapping(value = "/loginAdminStatus",method = RequestMethod.POST)
    public void loginAdminStatus (HttpServletResponse res, HttpServletRequest request){
        //数据打包格式化
        JSONObject param = JsonMaker.checking(res,request);
        JSONObject jsonObject = new JSONObject();
        if(param == null) {
            jsonObject.put("code",404);
            jsonObject.put("msg","没有登录呢");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        jsonObject.put("data",param);
        jsonObject.put("code",200);
        jsonObject.put("msg","已登录");
        HttpServletUtil.response(request,res,jsonObject);
    }

}
