package com.sy.blog.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sy.blog.service.BlogTitleService;
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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Controller
public class BlogTitleController {
    private Logger logger = Logger.getLogger(BlogTitleController.class);

    @Autowired
    private BlogTitleService blogTitleService;

    @RequestMapping(value = "/addTitle",method = RequestMethod.POST)
    public void addTitle (HttpServletResponse res, HttpServletRequest request, @RequestBody Map<String,Object> data){
        PrintWriter printWriter = null;
        JSONObject param = new JSONObject();
        JSONObject jsonObject = JsonMaker.duang(res,request,data);
        if(jsonObject == null) {
            jsonObject = new JSONObject();
            param.put("code",400);
            param.put("msg","请登陆");
            HttpServletUtil.response(request,res,param);
            return;
        }
        jsonObject.put("author",jsonObject.getInteger("whoMake"));
        if (!jsonObject.containsKey("title")) {
            param.put("code",400);
            param.put("msg","缺少title参数");
            HttpServletUtil.response(request,res,param);
            return;
        }
        if (!jsonObject.containsKey("author")) {
            param.put("code",400);
            param.put("msg","缺少author参数");
            HttpServletUtil.response(request,res,param);
            return;
        }
        if (!jsonObject.containsKey("outline")) {
            param.put("code",400);
            param.put("msg","缺少outline参数");
            HttpServletUtil.response(request,res,param);
            return;
        }
        System.out.println(jsonObject.getString("outline")+"===================");
        param = blogTitleService.addTitle(jsonObject);
        HttpServletUtil.response(request,res,param);
    }

    @RequestMapping(value = "/deleteTitle",method = RequestMethod.POST)
    public void deleteTitle (HttpServletResponse res, HttpServletRequest request, @RequestBody Map<String,Object> data){
        PrintWriter printWriter = null;
        JSONObject jsonObject = JsonMaker.duang(res,request,data);
        if(jsonObject == null) {
            jsonObject = new JSONObject();
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
        JSONObject param = blogTitleService.deleteTitle(jsonObject);
        HttpServletUtil.response(request,res,param);
    }

    @RequestMapping(value = "/updateTitle",method = RequestMethod.POST)
    public void updateTitle (HttpServletResponse res, HttpServletRequest request, @RequestBody Map<String,Object> data){
        PrintWriter printWriter = null;
        JSONObject jsonObject = JsonMaker.duang(res,request,data);
        if(jsonObject == null) {
            jsonObject = new JSONObject();
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
        JSONObject param = blogTitleService.updateTitle(jsonObject);
        HttpServletUtil.response(request,res,param);
    }

    @RequestMapping(value = "/getTitle",method = RequestMethod.POST)
    public void getTitle (HttpServletResponse res, HttpServletRequest request, @RequestBody Map<String,Object> data){
        PrintWriter printWriter = null;
        JSONObject jsonObject = JsonMaker.noDuang(res,request,data);
        if(jsonObject == null) {
            jsonObject = new JSONObject();
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
        JSONObject param = blogTitleService.getTitle(jsonObject);
        HttpServletUtil.response(request,res,param);
    }

    @RequestMapping(value = "/getTitleNoOutline",method = RequestMethod.POST)
    public void getTitleNoOutline (HttpServletResponse res, HttpServletRequest request, @RequestBody Map<String,Object> data){
        PrintWriter printWriter = null;
        //JSONObject jsonObject = JsonMaker.duang(res,request,data);
        JSONObject jsonObject = JsonMaker.noDuang(res,request,data);
        if(jsonObject == null) {
            jsonObject = new JSONObject();
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
        JSONObject param = blogTitleService.getTitleNoOutline(jsonObject);
        HttpServletUtil.response(request,res,param);
    }

    @RequestMapping(value = "/getTitleById",method = RequestMethod.POST)
    public void getTitleById (HttpServletResponse res, HttpServletRequest request, @RequestBody Map<String,Object> data){
        PrintWriter printWriter = null;
        //JSONObject jsonObject = JsonMaker.duang(res,request,data);
        JSONObject jsonObject = JsonMaker.noDuang(res,request,data);
        if(jsonObject == null) {
            jsonObject = new JSONObject();
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
        JSONObject param = blogTitleService.getTitleById(jsonObject);
        HttpServletUtil.response(request,res,param);
    }

    @RequestMapping(value = "/getTitleAndOutline",method = RequestMethod.POST)
    public void getTitleAndOutline (HttpServletResponse res, HttpServletRequest request, @RequestBody Map<String,Object> data){
        PrintWriter printWriter = null;
        JSONObject jsonObject = JsonMaker.noDuang(res,request,data);
        if(jsonObject == null) {
            jsonObject = new JSONObject();
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
        JSONObject param = blogTitleService.getTitleAndOutline(jsonObject);
        HttpServletUtil.response(request,res,param);
    }

    @RequestMapping(value = "/getTimex",method = RequestMethod.GET)
    public void getTimex (HttpServletResponse res, HttpServletRequest request) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("size",10);
        jsonObject.put("current",request.getParameter("current"));
        JSONObject param = blogTitleService.getTimex(jsonObject);
        res.getWriter().write(JSON.toJSONString(param));
    }

}
