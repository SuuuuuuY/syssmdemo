package com.sy.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sy.blog.beans.BlogPower;
import com.sy.blog.beans.BlogPower;
import com.sy.blog.mapper.BlogPowerMapper;
import com.sy.blog.mapper.BlogPowerMapper;
import com.sy.blog.service.BlogPowerService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.sy.blog.utils.JsonMaker;
import com.sy.blog.utils.PageUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class BlogPowerServiceImpl extends ServiceImpl<BlogPowerMapper, BlogPower> implements BlogPowerService {

    @Autowired
    private BlogPowerMapper blogPowerMapper;
    
    @Override
    public JSONObject addPower(JSONObject param) {
        JSONObject jsonObject = new JSONObject();
        BlogPower blogPower = JSON.toJavaObject(param, BlogPower.class);
        boolean flag = this.insert(blogPower);
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
    public JSONObject deletePower(JSONObject param) {
        JSONObject jsonObject = new JSONObject();
        EntityWrapper<BlogPower> ew = new EntityWrapper<>();
        ew.in("id",param.getString("id"));//格式(1,2,3)
        boolean flag = false;
        if(param.getInteger("type") == 1) {//物理删除
            flag = this.delete(ew);
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
    public JSONObject updatePower(JSONObject param) {
        JSONObject jsonObject = new JSONObject();
        BlogPower blogPower = JSON.toJavaObject(param, BlogPower.class);
        if (this.updateById(blogPower)) {
            jsonObject.put("code",200);
            jsonObject.put("msg","成功");
        } else {
            jsonObject.put("code",400);
            jsonObject.put("msg","失败");
        }
        return jsonObject;
    }

    @Override
    public JSONObject getPower(JSONObject param) {
        JSONObject jsonObject = new JSONObject();
        EntityWrapper<BlogPower> ew = new EntityWrapper<>();
        RowBounds rowBounds = PageUtil.getRowBounds(param);
        int total = blogPowerMapper.selectCount(ew);
        //column,value
        ew.like(param.getString("column"),param.getString("value"));
        List<BlogPower> pageList = blogPowerMapper.selectPage(rowBounds,ew);
        jsonObject = JsonMaker.List2Json(pageList);
        jsonObject.put("total",total);
        jsonObject.put("size",param.getInteger("size"));
        jsonObject.put("current",param.getInteger("current"));
        return jsonObject;
    }
}
