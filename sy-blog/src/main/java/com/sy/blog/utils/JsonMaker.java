package com.sy.blog.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sy.blog.beans.BlogUser;
import com.sy.blog.controller.BlogUserController;
import com.sy.blog.mapper.BlogUserMapper;
import com.sy.blog.service.BlogUserService;
import com.sy.blog.utils.reids.RedisUtil;
import org.apache.ibatis.ognl.Token;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.Kernel;
import java.util.List;
import java.util.Map;

public class JsonMaker {

    public static JSONObject duang(HttpServletResponse res, HttpServletRequest request, Map<String,Object> data){
        String json = JSON.toJSONString(data);//map转String
        JSONObject param = JSON.parseObject(json);//String转json
        param.put("ip",IpUtil.getIpAddress(request));//添加ip
        String token = request.getHeader("token");
        if(token != null && RedisUtil.exists(token)) {//验证成功
            BlogUser blogUser = JSON.toJavaObject(JSONObject.parseObject(RedisUtil.getSomething(token)),BlogUser.class);
            RedisUtil.reTrueToken(token);//刷新token有效期
            int id = Integer.valueOf(blogUser.getId());
            param.put("whoMake",id);
            int power = blogUser.getPower();
            if(power > 50) {
                return param;
            } else {
                return  null;
            }
        } else {
            return null;
        }
    }

    public static JSONObject duangNoAdmin(HttpServletResponse res, HttpServletRequest request, Map<String,Object> data){
        String json = JSON.toJSONString(data);//map转String
        JSONObject param = JSON.parseObject(json);//String转json
        param.put("ip",IpUtil.getIpAddress(request));//添加ip
        String token = request.getHeader("token");
        if(token != null && RedisUtil.exists(token)) {//验证成功
            BlogUser blogUser = JSON.toJavaObject(JSONObject.parseObject(RedisUtil.getSomething(token)),BlogUser.class);
            RedisUtil.reTrueToken(token);//刷新token有效期
            int id = Integer.valueOf(blogUser.getId());
            param.put("whoMake",id);
            return param;
        } else {
            return null;
        }
    }


    public static JSONObject noDuang(HttpServletResponse res, HttpServletRequest request, Map<String,Object> data){
        String json = JSON.toJSONString(data);//map转String
        JSONObject param = JSON.parseObject(json);//String转json
        param.put("ip",IpUtil.getIpAddress(request));//添加ip
        return param;
    }

    public static JSONObject List2Json(List list){

        String BmKhsStr=JSON.toJSONString(list);

        JSONArray array= JSONArray.parseArray(BmKhsStr);

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("data",array);
        System.out.println(jsonObject);
        System.out.println(jsonObject.toString());
        return jsonObject;
    }

    /**
     *仅仅是验证，并把操作对象加入param
     * @param res
     * @param request
     * @return
     */
    public static JSONObject checking(HttpServletResponse res, HttpServletRequest request){
        JSONObject param = new JSONObject();
        String token = request.getHeader("token");
        if(token != null && RedisUtil.exists(token)) {//验证成功
            BlogUser blogUser = JSON.toJavaObject(JSONObject.parseObject(RedisUtil.getSomething(token)),BlogUser.class);
            RedisUtil.reTrueToken(token);//刷新token有效期
            int id = Integer.valueOf(blogUser.getId());
            param.put("whoMake",id);
            int power = blogUser.getPower();
            if(power > 50) {
                return param;
            } else {
                return  null;
            }
        } else {
            return new JSONObject();
        }
    }

    /**
     * 仅仅是验证，用于非管理员
     * @param res
     * @param request
     * @return
     */
    public static JSONObject checkingNoAdmin(HttpServletResponse res, HttpServletRequest request){
        JSONObject param = new JSONObject();
        String token = request.getHeader("token");
        if(token != null && RedisUtil.exists(token)) {//验证成功
            BlogUser blogUser = JSON.toJavaObject(JSONObject.parseObject(RedisUtil.getSomething(token)),BlogUser.class);
            RedisUtil.reTrueToken(token);//刷新token有效期
            int id = Integer.valueOf(blogUser.getId());
            param.put("userName",blogUser.getNickName());
            param.put("whoMake",id);
            return param;
        } else {
            return new JSONObject();
        }
    }
}
