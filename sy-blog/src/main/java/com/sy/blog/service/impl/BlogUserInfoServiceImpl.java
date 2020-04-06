package com.sy.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sy.blog.beans.BlogUserInfo;
import com.sy.blog.beans.BlogUserInfo;
import com.sy.blog.mapper.BlogUserInfoMapper;
import com.sy.blog.service.BlogUserInfoService;
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
public class BlogUserInfoServiceImpl extends ServiceImpl<BlogUserInfoMapper, BlogUserInfo> implements BlogUserInfoService {

    @Autowired
    private BlogUserInfoMapper blogUserInfoMapper;

    @Override
    public JSONObject addUserInfo(JSONObject param) {
        JSONObject jsonObject = new JSONObject();
        BlogUserInfo blogUserInfo = JSON.toJavaObject(param, BlogUserInfo.class);
        blogUserInfo.setStatus(1);
        boolean flag = this.insert(blogUserInfo);
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
    public JSONObject deleteUserInfo(JSONObject param) {
        JSONObject jsonObject = new JSONObject();
        EntityWrapper<BlogUserInfo> ew = new EntityWrapper<>();
        ew.in("id",param.getString("id"));//格式(1,2,3)
        boolean flag = false;
        if(param.getInteger("type") == 1) {//物理删除
            flag = this.delete(ew);
        } else {//逻辑删除
            BlogUserInfo blogUserInfo = new BlogUserInfo();
            blogUserInfo.setStatus(0);
            flag = this.update(blogUserInfo,ew);
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
    public JSONObject updateUserInfo(JSONObject param) {
        JSONObject jsonObject = new JSONObject();
        BlogUserInfo blogUserInfo = JSON.toJavaObject(param, BlogUserInfo.class);
        if (this.updateById(blogUserInfo)) {
            jsonObject.put("code",200);
            jsonObject.put("msg","成功");
        } else {
            jsonObject.put("code",400);
            jsonObject.put("msg","失败");
        }
        return jsonObject;
    }

    @Override
    public JSONObject getUserInfo(JSONObject param) {
        JSONObject jsonObject = new JSONObject();
        EntityWrapper<BlogUserInfo> ew = new EntityWrapper<>();
        RowBounds rowBounds = PageUtil.getRowBounds(param);
        int total = blogUserInfoMapper.selectCount(ew);
        //column,value
        ew.like(param.getString("column"),param.getString("value"));
        List<BlogUserInfo> pageList = blogUserInfoMapper.selectPage(rowBounds,ew);
        jsonObject = JsonMaker.List2Json(pageList);
        jsonObject.put("total",total);
        jsonObject.put("size",param.getInteger("size"));
        jsonObject.put("current",param.getInteger("current"));
        return jsonObject;
    }
}
