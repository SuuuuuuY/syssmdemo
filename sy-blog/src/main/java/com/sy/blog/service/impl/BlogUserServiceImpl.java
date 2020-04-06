package com.sy.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sy.blog.beans.BlogUser;
import com.sy.blog.mapper.BlogUserMapper;
import com.sy.blog.service.BlogUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.sy.blog.utils.*;
import com.sy.blog.utils.reids.RedisUtil;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Su.Yang
 * @since 2019-10-15
 */

@Service
public class BlogUserServiceImpl extends ServiceImpl<BlogUserMapper, BlogUser> implements BlogUserService {

    @Autowired
    private BlogUserMapper blogUserMapper;


    @Override
    public JSONObject addUser(JSONObject param) {
        JSONObject jsonObject = new JSONObject();
        BlogUser blogUser = JSON.toJavaObject(param, BlogUser.class);
        //前面已经有了
//        EntityWrapper<BlogUser> ew = new EntityWrapper<>();
//        ew.setSqlSelect("user_name AS userName");
//        ew.eq("user_name",blogUser.getUserName());
//        BlogUser blogU = this.selectOne(ew);
//        if(blogU != null) {
//            jsonObject.put("code",400);
//            jsonObject.put("msg","该邮箱已注册");
//            return jsonObject;
//        }
        //todo 启用之后用md5加密
        blogUser.setPassword(MD5Util.MD5(param.getString("password")));
        blogUser.setCreateTime(new Date());
        blogUser.setStatus(1);
        blogUser.setPower(1);
        //blogUser.setUserId(CheckMakeUtil.RoNum(6));
        int a = blogUserMapper.insert(blogUser);
        if( a > 0 ) {
            jsonObject.put("code",200);
            jsonObject.put("msg","成功");
        } else {
            jsonObject.put("code",400);
            jsonObject.put("msg","失败");
        }
        return jsonObject;
    }

    @Override
    public JSONObject deleteUser(JSONObject param) {
        JSONObject jsonObject = new JSONObject();
        EntityWrapper<BlogUser> ew = new EntityWrapper<>();
        ew.in("id",param.getString("id"));//格式(1,2,3)
        int flag = 0;
        int type = param.getInteger("type");
        if(type == 1) {//物理删除
            flag = blogUserMapper.delete(ew);
        } else if (type == 2) {//逻辑删除
            BlogUser blogUser = new BlogUser();
            blogUser.setStatus(0);
            flag = blogUserMapper.update(blogUser,ew);
        } else if (type == 3) {//冻结
            BlogUser blogUser = new BlogUser();
            blogUser.setStatus(3);
            flag = blogUserMapper.update(blogUser,ew);
        }


        if( flag > 0 ) {
            jsonObject.put("code",200);
            jsonObject.put("msg","成功");
        } else {
            jsonObject.put("code",400);
            jsonObject.put("msg","失败");
        }
        return jsonObject;
    }

    @Override
    public JSONObject updateUser(JSONObject param) {
        JSONObject jsonObject = new JSONObject();
        BlogUser blogUser = JSON.toJavaObject(param, BlogUser.class);
        int a = blogUserMapper.updateById(blogUser);
        if( a > 0 ) {
            jsonObject.put("code",200);
            jsonObject.put("msg","成功");
        } else {
            jsonObject.put("code",400);
            jsonObject.put("msg","失败");
        }
        return jsonObject;
    }

    @Override
    public JSONObject getUser(JSONObject param) {
        JSONObject jsonObject = new JSONObject();
        EntityWrapper<BlogUser> ew = new EntityWrapper<>();
        RowBounds rowBounds = PageUtil.getRowBounds(param);
        int total = blogUserMapper.selectCount(ew);
        //column,value
        ew.like(param.getString("column"),param.getString("value"));
        List<BlogUser> pageList = blogUserMapper.selectPage(rowBounds,ew);
        jsonObject = JsonMaker.List2Json(pageList);
        jsonObject.put("total",total);
        jsonObject.put("size",param.getInteger("size"));
        jsonObject.put("current",param.getInteger("current"));
        return jsonObject;
    }

    @Override
    public JSONObject login(JSONObject param) {
        JSONObject jsonObject = new JSONObject();
        JSONObject object =new JSONObject();
        EntityWrapper<BlogUser> ew = new EntityWrapper<>();
        ew.eq("user_name",param.getString("userName"));
//        ew.eq("status",1);
        if(param.containsKey("admin")) {
            ew.gt("power",50);
        }
        BlogUser blogUser = this.selectOne(ew);
        if(blogUser == null){
            jsonObject.put("code",400);
            jsonObject.put("msg","密码错误或者账号不存在");
            return jsonObject;
        }
        if (blogUser.getStatus() == 3) {
            jsonObject.put("code",400);
            jsonObject.put("msg","账号已经冻结,请联系管理员解冻");
            return jsonObject;
        }
        //todo 启用之后用md5加密
        if(blogUser.getStatus() == 1 && blogUser.getPassword().equals(MD5Util.MD5(param.getString("password")))) {
//        if(blogUser.getStatus() == 1 && blogUser.getPassword().equals(param.getString("password"))) {
            String token = TokenUtils.generateShortUUID();
            //检查是否已经登录
            if (RedisUtil.exists(token)) {
//            if (RedisUtil.exists(id)) {
//                String value = RedisUtil.getSomething(id);//{id,value}
                RedisUtil.moveSomething(token);//{value,id}
//                RedisUtil.moveSomething(id);//{id,value}
            }
            //todo bug数据库为只读
//            BlogUser blogUserT = new BlogUser();
//            blogUserT.setId(blogUser.getId());
//            blogUserT.setUpTime(new Date());
//            blogUserMapper.updateById(blogUserT);
            //缓存token
//            RedisUtil.putSomething(id,30*60,token);
            RedisUtil.putSomething(token,60*60*24,JSON.toJSONString(blogUser));
            object.put("token",token);
            object.put("power",blogUser.getPower());
            object.put("userId",blogUser.getId());
            jsonObject.put("code",200);
            jsonObject.put("msg","登录成功");
            jsonObject.put("data",object);
        } else {
            jsonObject.put("code",400);
            jsonObject.put("msg","密码错误或者账号不存在");
        }

        return jsonObject;
    }

    @Override
    public Boolean haveEmail(String email) {
        Boolean flag = false;
        EntityWrapper<BlogUser> ew = new EntityWrapper<>();
        ew.setSqlSelect("user_name AS userName");
        ew.eq("user_name",email);
        BlogUser blogU = this.selectOne(ew);
        if(blogU != null) {
            flag = true;
        }
        return flag;
    }

    @Override
    public Boolean haveName(String name) {
        Boolean flag = false;
        EntityWrapper<BlogUser> ew = new EntityWrapper<>();
        ew.setSqlSelect("nick_name AS nickName");
        ew.eq("user_name",name);
        BlogUser blogU = this.selectOne(ew);
        if(blogU != null) {
            flag = true;
        }
        return flag;
    }


}
