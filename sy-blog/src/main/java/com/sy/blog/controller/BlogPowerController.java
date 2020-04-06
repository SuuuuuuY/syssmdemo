package com.sy.blog.controller;

import com.alibaba.fastjson.JSONObject;
import com.sy.blog.service.BlogPowerService;
import com.sy.blog.utils.HttpServletUtil;
import com.sy.blog.utils.JsonMaker;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

@Controller
public class BlogPowerController {

    private Logger logger = Logger.getLogger(BlogPowerController.class);

    @Autowired
    private BlogPowerService blogPowerService;

    @RequestMapping(value = "/addPower",method = RequestMethod.POST)
    public void addPower (HttpServletResponse res, HttpServletRequest request, @RequestBody Map<String,Object> data){
        PrintWriter printWriter = null;
        JSONObject jsonObject = JsonMaker.duang(res,request,data);
        if(jsonObject == null) {
            jsonObject.put("code",404);
            jsonObject.put("msg","请登陆");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        if (!jsonObject.containsKey("powerName")) {
            jsonObject.put("code",400);
            jsonObject.put("msg","缺少powerName参数");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        if (!jsonObject.containsKey("open")) {
            jsonObject.put("code",400);
            jsonObject.put("msg","缺少open参数");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        JSONObject param = blogPowerService.addPower(jsonObject);
        HttpServletUtil.response(request,res,param);
    }

    @RequestMapping(value = "/deletePower",method = RequestMethod.POST)
    public void deletePower (HttpServletResponse res, HttpServletRequest request, @RequestBody Map<String,Object> data){
        PrintWriter printWriter = null;
        JSONObject jsonObject = JsonMaker.duang(res,request,data);
        if(jsonObject == null) {
            jsonObject.put("code",404);
            jsonObject.put("msg","请登陆");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        if (!jsonObject.containsKey("id")) {
            jsonObject.put("code",400);
            jsonObject.put("msg","缺少id参数");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        if (!jsonObject.containsKey("type")) {
            jsonObject.put("code",400);
            jsonObject.put("msg","缺少type参数");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        JSONObject param = blogPowerService.deletePower(jsonObject);
        HttpServletUtil.response(request,res,param);
    }

    @RequestMapping(value = "/updatePower",method = RequestMethod.POST)
    public void updatePower (HttpServletResponse res, HttpServletRequest request, @RequestBody Map<String,Object> data){
        PrintWriter printWriter = null;
        JSONObject jsonObject = JsonMaker.duang(res,request,data);
        if(jsonObject == null) {
            jsonObject.put("code",404);
            jsonObject.put("msg","请登陆");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        if (!jsonObject.containsKey("id")) {
            jsonObject.put("code",400);
            jsonObject.put("msg","缺少id参数");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        JSONObject param = blogPowerService.updatePower(jsonObject);
        HttpServletUtil.response(request,res,param);
    }

    @RequestMapping(value = "/getPower",method = RequestMethod.POST)
    public void getPower (HttpServletResponse res, HttpServletRequest request, @RequestBody Map<String,Object> data){
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
        JSONObject param = blogPowerService.getPower(jsonObject);
        HttpServletUtil.response(request,res,param);
    }
}
