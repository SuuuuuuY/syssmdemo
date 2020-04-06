package com.sy.blog.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sy.blog.service.BlogUserService;
import com.sy.blog.service.impl.BlogUserServiceImpl;
import com.sy.blog.utils.HttpServletUtil;
import com.sy.blog.utils.IpUtil;
import com.sy.blog.utils.JsonMaker;
import com.sy.blog.utils.MailUtil;
import com.sy.blog.utils.reids.RedisUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Controller
public class BlogUserController{
    private Logger logger = Logger.getLogger(BlogUserController.class);

    @Autowired
    private BlogUserService blogUserService;

    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    public void addUser (HttpServletResponse res,HttpServletRequest request,@RequestBody Map<String,Object> data){
        //数据打包格式化
        JSONObject param = JsonMaker.duang(res,request,data);
        JSONObject jsonObject = new JSONObject();
        if(param == null) {
            jsonObject.put("code",404);
            jsonObject.put("msg","请登陆");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        if (!param.containsKey("nickName")) {
            jsonObject.put("code",400);
            jsonObject.put("msg","缺少nickName参数");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        if (!param.containsKey("userName")) {
            jsonObject.put("code",400);
            jsonObject.put("msg","缺少userName参数");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        if (!param.containsKey("password")) {
            jsonObject.put("code",400);
            jsonObject.put("msg","缺少password参数");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        jsonObject = blogUserService.addUser(param);
        HttpServletUtil.response(request,res,jsonObject);
    }

    @RequestMapping(value = "/deleteUser",method = RequestMethod.POST)
    public void deleteUser (HttpServletResponse res,HttpServletRequest request,@RequestBody Map<String,Object> data){
        //数据打包格式化
        JSONObject param = JsonMaker.duang(res,request,data);
        JSONObject jsonObject = new JSONObject();
        if(param == null) {
            jsonObject.put("code",404);
            jsonObject.put("msg","请登陆");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        if (!param.containsKey("id")) {
            jsonObject.put("code",400);
            jsonObject.put("msg","缺少id参数");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        if (!param.containsKey("type")) {
            jsonObject.put("code",400);
            jsonObject.put("msg","缺少type参数");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        jsonObject = blogUserService.deleteUser(param);
        HttpServletUtil.response(request,res,jsonObject);
    }

    @RequestMapping(value = "/updateUser",method = RequestMethod.POST)
    public void updateUser (HttpServletResponse res,HttpServletRequest request,@RequestBody Map<String,Object> data){
        //数据打包格式化
        JSONObject param = JsonMaker.duang(res,request,data);
        JSONObject jsonObject = new JSONObject();
        if(param == null) {
            jsonObject.put("code",404);
            jsonObject.put("msg","请登陆");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        if (!param.containsKey("id")) {
            jsonObject.put("code",400);
            jsonObject.put("msg","缺少id参数");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        jsonObject = blogUserService.updateUser(param);
        HttpServletUtil.response(request,res,jsonObject);
    }

    @RequestMapping(value = "/getUser",method = RequestMethod.POST)
    public void getUser (HttpServletResponse res, HttpServletRequest request, @RequestBody Map<String,Object> data){
        PrintWriter printWriter = null;
        JSONObject jsonObject = JsonMaker.duang(res,request,data);
        if(jsonObject == null) {
            jsonObject.put("code",404);
            jsonObject.put("msg","请登陆");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        if (!jsonObject.containsKey("size") || !(jsonObject.get("size") instanceof Integer)) {
            jsonObject.remove("size");
            jsonObject.put("size",10);
        }
        if (!jsonObject.containsKey("current") || !(jsonObject.get("current") instanceof Integer)) {
            jsonObject.remove("current");
            jsonObject.put("current",1);
        }
        JSONObject param = blogUserService.getUser(jsonObject);
        HttpServletUtil.response(request,res,param);
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public void register(HttpServletResponse res, HttpServletRequest request, @RequestBody Map<String,Object> data){
        PrintWriter printWriter = null;
        JSONObject jsonObject = JsonMaker.noDuang(res,request,data);
        if(jsonObject == null) {
            jsonObject = new JSONObject();
            jsonObject.put("code",404);
            jsonObject.put("msg","如果你看见这句话，请告诉我");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        if (!jsonObject.containsKey("nickName")) {
            jsonObject.put("code",400);
            jsonObject.put("msg","请填写昵称");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        if (!jsonObject.containsKey("userName")) {
            jsonObject.put("code",400);
            jsonObject.put("msg","请填写邮箱");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
//        if (!MailUtil.isEmail(jsonObject.getString("userName"))) {
//            jsonObject.put("code",400);
//            jsonObject.put("msg","邮箱格式不对");
//            HttpServletUtil.response(request,res,jsonObject);
//            return;
//        }
        if (!jsonObject.containsKey("password")) {
            jsonObject.put("code",400);
            jsonObject.put("msg","请填写密码");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        if (!jsonObject.containsKey("check")) {
            jsonObject.put("code",400);
            jsonObject.put("msg","验证码没填呢");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        if(blogUserService.haveEmail(jsonObject.getString("userName"))) {
            jsonObject.put("code",400);
            jsonObject.put("msg","账号已经存在");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        if(blogUserService.haveName(jsonObject.getString("nickName"))) {
            jsonObject.put("code",400);
            jsonObject.put("msg","昵称已经存在");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
//        Boolean flag = MailUtil.checkY(jsonObject.getString("userName"),jsonObject.getString("check"));
        Boolean flag = RedisUtil.getSomething(jsonObject.getString("check")).equals("ok");
        JSONObject param = new JSONObject();
        if (flag) {
            param = blogUserService.addUser(jsonObject);
        } else {
            param.put("code",400);
            param.put("msg","验证码错误");
        }
        HttpServletUtil.response(request,res,param);
    }

    @RequestMapping(value = "/check",method = RequestMethod.POST)
    public void check(HttpServletResponse res, HttpServletRequest request, @RequestBody Map<String,Object> data){
        PrintWriter printWriter = null;
        JSONObject jsonObject = JsonMaker.noDuang(res,request,data);
        if(jsonObject == null) {
            jsonObject = new JSONObject();
            jsonObject.put("code",404);
            jsonObject.put("msg","如果你看见这句话，请告诉我");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        if (!jsonObject.containsKey("userName")) {
            jsonObject.put("code",400);
            jsonObject.put("msg","请填写邮箱");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        if (!MailUtil.isEmail(jsonObject.getString("userName"))) {
            jsonObject.put("code",400);
            jsonObject.put("msg","邮箱格式不对");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        Boolean flag = blogUserService.haveEmail(jsonObject.getString("userName"));
        JSONObject param = new JSONObject();
        if(flag) {
            param.put("code",400);param.put("msg","邮箱已经注册了");
        } else {
            param = MailUtil.checkS(jsonObject.getString("userName"));
        }

        HttpServletUtil.response(request,res,param);
    }

}
