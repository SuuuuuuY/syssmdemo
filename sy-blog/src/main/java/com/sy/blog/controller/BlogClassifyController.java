package com.sy.blog.controller;

import com.alibaba.fastjson.JSONObject;
import com.sy.blog.service.BlogClassifyService;
import com.sy.blog.utils.HttpServletUtil;
import com.sy.blog.utils.JsonMaker;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

@Controller
public class BlogClassifyController {
    private Logger logger = Logger.getLogger(BlogClassifyController.class);

    @Autowired
    private BlogClassifyService blogClassifyService;

    @RequestMapping(value = "/addClassify",method = RequestMethod.POST)
    public void addClassify (HttpServletResponse res, HttpServletRequest request, @RequestBody Map<String,Object> data){
        PrintWriter printWriter = null;
        JSONObject jsonObject = JsonMaker.duang(res,request,data);
        if(jsonObject == null) {
            jsonObject = new JSONObject();
            jsonObject.put("code",404);
            jsonObject.put("msg","请登陆");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        if (!jsonObject.containsKey("classifyName")) {
            jsonObject.put("code",400);
            jsonObject.put("msg","缺少classifyName参数");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        JSONObject param = blogClassifyService.addClassify(jsonObject);
        HttpServletUtil.response(request,res,param);
    }

    @RequestMapping(value = "/deleteClassify",method = RequestMethod.POST)
    public void deleteClassify (HttpServletResponse res, HttpServletRequest request, @RequestBody Map<String,Object> data){
        PrintWriter printWriter = null;
        JSONObject jsonObject = JsonMaker.duang(res,request,data);
        if(jsonObject == null) {
            jsonObject = new JSONObject();
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
        JSONObject param = blogClassifyService.deleteClassify(jsonObject);
        HttpServletUtil.response(request,res,param);
    }

    @RequestMapping(value = "/updateClassify",method = RequestMethod.POST)
    public void updateClassify (HttpServletResponse res, HttpServletRequest request, @RequestBody Map<String,Object> data){
        PrintWriter printWriter = null;
        JSONObject jsonObject = JsonMaker.duang(res,request,data);
        if(jsonObject == null) {
            jsonObject = new JSONObject();
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
        JSONObject param = blogClassifyService.updateClassify(jsonObject);
        HttpServletUtil.response(request,res,param);
    }

    @RequestMapping(value = "/getClassify",method = RequestMethod.POST)
    public void getClassify (HttpServletResponse res, HttpServletRequest request, @RequestBody Map<String,Object> data){
        PrintWriter printWriter = null;
        JSONObject jsonObject = JsonMaker.noDuang(res,request,data);
        if(jsonObject == null) {
            jsonObject = new JSONObject();
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
        JSONObject param = blogClassifyService.getClassify(jsonObject);
        HttpServletUtil.response(request,res,param);
    }
}
