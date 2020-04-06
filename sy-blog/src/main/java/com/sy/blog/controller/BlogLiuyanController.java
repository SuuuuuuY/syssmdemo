package com.sy.blog.controller;

import com.alibaba.fastjson.JSONObject;
import com.sy.blog.service.BlogLiuyanService;
import com.sy.blog.service.BlogLiuyanService;
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
public class BlogLiuyanController {
    private Logger logger = Logger.getLogger(BlogLiuyanController.class);

    @Autowired
    private BlogLiuyanService blogLiuyanService;

    @RequestMapping(value = "/addLiuyan",method = RequestMethod.POST)
    public void addLiuyan (HttpServletResponse res, HttpServletRequest request, @RequestBody Map<String,Object> data){
        PrintWriter printWriter = null;
        JSONObject jsonObject = JsonMaker.duangNoAdmin(res,request,data);
        if(jsonObject == null) {
            jsonObject = new JSONObject();
            jsonObject.put("code",404);
            jsonObject.put("msg","请登陆");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        jsonObject.put("userId",jsonObject.getInteger("whoMake"));
        if (!jsonObject.containsKey("userId")) {
            jsonObject.put("code",400);
            jsonObject.put("msg","缺少userId参数");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        if (!jsonObject.containsKey("content")) {
            jsonObject.put("code",400);
            jsonObject.put("msg","缺少content参数");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        if (!jsonObject.containsKey("replyType")) {
            jsonObject.put("code",400);
            jsonObject.put("msg","缺少replyType参数");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        if (!jsonObject.containsKey("replyId")) {
            jsonObject.put("code",400);
            jsonObject.put("msg","缺少replyId参数");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        JSONObject param = blogLiuyanService.addLiuyan(jsonObject);
        HttpServletUtil.response(request,res,param);
    }

    @RequestMapping(value = "/deleteLiuyan",method = RequestMethod.POST)
    public void deleteLiuyan (HttpServletResponse res, HttpServletRequest request, @RequestBody Map<String,Object> data){
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
        JSONObject param = blogLiuyanService.deleteLiuyan(jsonObject);
        HttpServletUtil.response(request,res,param);
    }

    @RequestMapping(value = "/updateLiuyan",method = RequestMethod.POST)
    public void updateLiuyan (HttpServletResponse res, HttpServletRequest request, @RequestBody Map<String,Object> data){
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
        JSONObject param = blogLiuyanService.updateLiuyan(jsonObject);
        HttpServletUtil.response(request,res,param);
    }

    @RequestMapping(value = "/getLiuyan",method = RequestMethod.POST)
    public void getLiuyan (HttpServletResponse res, HttpServletRequest request, @RequestBody Map<String,Object> data){
        PrintWriter printWriter = null;
        JSONObject jsonObject = JsonMaker.duang(res,request,data);
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
        JSONObject param = blogLiuyanService.getLiuyan(jsonObject);
        HttpServletUtil.response(request,res,param);
    }

    @RequestMapping(value = "/getLiuyanByArt",method = RequestMethod.POST)
    public void getLiuyanByArt (HttpServletResponse res, HttpServletRequest request, @RequestBody Map<String,Object> data){
        PrintWriter printWriter = null;
        JSONObject jsonObject = JsonMaker.noDuang(res,request,data);
        if(jsonObject == null) {
            jsonObject = new JSONObject();
            jsonObject.put("code",404);
            jsonObject.put("msg","请登陆");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        if (!jsonObject.containsKey("replyId")) {
            jsonObject.put("code",400);
            jsonObject.put("msg","缺少replyId参数");
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
        JSONObject param = blogLiuyanService.getLiuyanByArt(jsonObject);
        HttpServletUtil.response(request,res,param);
    }

    @RequestMapping(value = "/getLiuyanCount",method = RequestMethod.POST)
    public void getLiuyanCount (HttpServletResponse res, HttpServletRequest request, @RequestBody Map<String,Object> data){
        PrintWriter printWriter = null;
        JSONObject jsonObject = JsonMaker.noDuang(res,request,data);
        if(jsonObject == null) {
            jsonObject = new JSONObject();
            jsonObject.put("code",404);
            jsonObject.put("msg","未知错误");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        if (!jsonObject.containsKey("value")) {
            jsonObject.put("code",400);
            jsonObject.put("msg","缺少value参数");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        if (!jsonObject.containsKey("key")) {
            jsonObject.put("code",400);
            jsonObject.put("msg","缺少key参数");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        JSONObject param = new JSONObject();
        int total = blogLiuyanService.getLiuyanCount(jsonObject);
        param.put("data",total);
        param.put("code",200);
        param.put("msg","成功");
        HttpServletUtil.response(request,res,param);
    }

    @RequestMapping(value = "/getLiuyanByParentIdAndArt",method = RequestMethod.POST)
    public void getLiuyanByParentIdAndArt (HttpServletResponse res, HttpServletRequest request, @RequestBody Map<String,Object> data){
        PrintWriter printWriter = null;
        JSONObject jsonObject = JsonMaker.noDuang(res,request,data);
        if(jsonObject == null) {
            jsonObject = new JSONObject();
            jsonObject.put("code",404);
            jsonObject.put("msg","请登陆");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        if (!jsonObject.containsKey("replyId")) {
            jsonObject.put("code",400);
            jsonObject.put("msg","缺少replyId参数");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        if (!jsonObject.containsKey("parentId")) {
            jsonObject.put("code",400);
            jsonObject.put("msg","缺少parentId参数");
            HttpServletUtil.response(request,res,jsonObject);
            return;
        }
        //todo 暂时不做分页
        if (!jsonObject.containsKey("size") || !(jsonObject.get("size") instanceof Integer)) {
            jsonObject.remove("size");
            jsonObject.put("size",100);
        }
        if (!jsonObject.containsKey("current") || !(jsonObject.get("current") instanceof Integer)) {
            jsonObject.remove("current");
            jsonObject.put("current",1);
        }
        JSONObject param = blogLiuyanService.getLiuyanByParentIdAndArt(jsonObject);
        HttpServletUtil.response(request,res,param);
    }

}
