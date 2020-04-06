package com.sy.blog.service.impl;

import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlCommitStatement;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sy.blog.beans.BlogClassify;
import com.sy.blog.beans.BlogUser;
import com.sy.blog.mapper.BlogClassifyMapper;
import com.sy.blog.service.BlogClassifyService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.sy.blog.utils.JsonMaker;
import com.sy.blog.utils.PageUtil;
import com.sy.blog.utils.reids.RedisOps;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class BlogClassifyServiceImpl extends ServiceImpl<BlogClassifyMapper, BlogClassify> implements BlogClassifyService {

    @Autowired
    private BlogClassifyMapper blogClassifyMapper;

    @Override
    public JSONObject addClassify(JSONObject param) {
        JSONObject jsonObject = new JSONObject();
        BlogClassify blogClassify = JSON.toJavaObject(param, BlogClassify.class);
        blogClassify.setStatus(1);
        boolean flag = this.insert(blogClassify);
        if(flag) {
            jsonObject.put("code",200);
            jsonObject.put("msg","成功");
//            RedisOps.setJsonString(this.getClass().getName(),);
        } else {
            jsonObject.put("code",400);
            jsonObject.put("msg","失败");
        }
        return jsonObject;
    }

    @Override
    public JSONObject deleteClassify(JSONObject param) {
        JSONObject jsonObject = new JSONObject();
        EntityWrapper<BlogClassify> ew = new EntityWrapper<>();
        ew.in("id",param.getString("id"));//格式(1,2,3)
        boolean flag = false;
        if(param.getInteger("type") == 1) {//物理删除
            flag = this.delete(ew);
        } else {//逻辑删除
            BlogClassify blogClassify = new BlogClassify();
            blogClassify.setStatus(0);
            flag = this.update(blogClassify,ew);
        }
        if(flag) {
            jsonObject.put("code",200);
            jsonObject.put("msg","成功");
        } else {
            jsonObject.put("code",400);
            jsonObject.put("msg","失败");
        }
        return jsonObject;
    }

    @Override
    public JSONObject updateClassify(JSONObject param) {
        JSONObject jsonObject = new JSONObject();
        BlogClassify blogClassify = JSON.toJavaObject(param, BlogClassify.class);
        if (this.updateById(blogClassify)) {
            jsonObject.put("code",200);
            jsonObject.put("msg","成功");
        } else {
            jsonObject.put("code",400);
            jsonObject.put("msg","失败");
        }
        return jsonObject;
    }

    @Override
    public JSONObject getClassify(JSONObject param) {
        JSONObject jsonObject = new JSONObject();
        EntityWrapper<BlogClassify> ew = new EntityWrapper<>();
        RowBounds rowBounds = PageUtil.getRowBounds(param);
        int total = blogClassifyMapper.selectCount(ew);
        //column,value
        ew.like(param.getString("column"),param.getString("value"));
        List<BlogClassify> pageList = blogClassifyMapper.selectPage(rowBounds,ew);
        //缓存(暂停开发)
//        RedisOps<BlogClassify> blogClassifyRedisOps = new RedisOps<>();
//        blogClassifyRedisOps.setJsonStringPage(this.getClass().getName()+","+param.getInteger("size")+","
//                + param.getInteger("current") + "," +
//                        param.getString("column") + "," +
//                        param.getString("value")
//                ,"",pageList);
        //
        jsonObject = JsonMaker.List2Json(pageList);
        jsonObject.put("total",total);
        jsonObject.put("size",param.getInteger("size"));
        jsonObject.put("current",param.getInteger("current"));
        System.out.println(jsonObject.toJSONString());
        return jsonObject;
    }
}
