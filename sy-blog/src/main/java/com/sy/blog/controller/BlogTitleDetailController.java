package com.sy.blog.controller;

import com.alibaba.fastjson.JSONObject;
import com.sy.blog.service.BlogTitleDetailService;
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
public class BlogTitleDetailController {
    private Logger logger = Logger.getLogger(BlogTitleDetailController.class);

    @Autowired
    private BlogTitleDetailService blogTitleDetailService;

    @RequestMapping(value = "/addTitleDetail",method = RequestMethod.POST)
    public void addTitleDetail (HttpServletResponse res, HttpServletRequest request, @RequestBody Map<String,Object> data){
        PrintWriter printWriter = null;
        JSONObject jsonObject = JsonMaker.duang(res,request,data);
        if(jsonObject == null) {
            jsonObject.put("code",400);
            jsonObject.put("msg","请登陆");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        if (!jsonObject.containsKey("content")) {
            jsonObject.put("code",400);
            jsonObject.put("msg","缺少classifyName参数");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        JSONObject param = blogTitleDetailService.addTitleDetail(jsonObject);
        HttpServletUtil.response(request,res,param);
    }

    @RequestMapping(value = "/deleteTitleDetail",method = RequestMethod.POST)
    public void deleteTitleDetail (HttpServletResponse res, HttpServletRequest request, @RequestBody Map<String,Object> data){
        PrintWriter printWriter = null;
        JSONObject jsonObject = JsonMaker.duang(res,request,data);
        if(jsonObject == null) {
            jsonObject.put("code",400);
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
        JSONObject param = blogTitleDetailService.deleteTitleDetail(jsonObject);
        HttpServletUtil.response(request,res,param);
    }

    @RequestMapping(value = "/updateTitleDetail",method = RequestMethod.POST)
    public void updateTitleDetail (HttpServletResponse res, HttpServletRequest request, @RequestBody Map<String,Object> data){
        PrintWriter printWriter = null;
        JSONObject jsonObject = JsonMaker.duang(res,request,data);
        if(jsonObject == null) {
            jsonObject.put("code",400);
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
        JSONObject param = blogTitleDetailService.updateTitleDetail(jsonObject);
        HttpServletUtil.response(request,res,param);
    }

    @RequestMapping(value = "/getTitleDetail",method = RequestMethod.POST)
    public void getTitleDetail (HttpServletResponse res, HttpServletRequest request, @RequestBody Map<String,Object> data){
        PrintWriter printWriter = null;
        JSONObject jsonObject = JsonMaker.duang(res,request,data);
        if(jsonObject == null) {
            jsonObject.put("code",400);
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
        JSONObject param = blogTitleDetailService.getTitleDetail(jsonObject);
        HttpServletUtil.response(request,res,param);
    }
}
