package com.sy.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sy.blog.beans.BlogTitleDetail;
import com.sy.blog.beans.BlogTitleDetail;
import com.sy.blog.mapper.BlogTitleDetailMapper;
import com.sy.blog.service.BlogTitleDetailService;
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
public class BlogTitleDetailServiceImpl extends ServiceImpl<BlogTitleDetailMapper, BlogTitleDetail> implements BlogTitleDetailService {

    @Autowired
    private BlogTitleDetailMapper blogTitleDetailMapper;

    @Override
    public JSONObject addTitleDetail(JSONObject param) {
        JSONObject jsonObject = new JSONObject();
        BlogTitleDetail blogTitleDetail = JSON.toJavaObject(param, BlogTitleDetail.class);
        blogTitleDetail.setStatus(1);
        boolean flag = this.insert(blogTitleDetail);
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
    public JSONObject deleteTitleDetail(JSONObject param) {
        JSONObject jsonObject = new JSONObject();
        EntityWrapper<BlogTitleDetail> ew = new EntityWrapper<>();
        ew.in("id",param.getString("id"));//格式(1,2,3)
        boolean flag = false;
        if(param.getInteger("type") == 1) {//物理删除
            flag = this.delete(ew);
        } else {//逻辑删除
            BlogTitleDetail blogTitleDetail = new BlogTitleDetail();
            blogTitleDetail.setStatus(0);
            flag = this.update(blogTitleDetail,ew);
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
    public JSONObject updateTitleDetail(JSONObject param) {
        JSONObject jsonObject = new JSONObject();
        BlogTitleDetail blogTitleDetail = JSON.toJavaObject(param, BlogTitleDetail.class);
        if (this.updateById(blogTitleDetail)) {
            jsonObject.put("code",200);
            jsonObject.put("msg","成功");
        } else {
            jsonObject.put("code",400);
            jsonObject.put("msg","失败");
        }
        return jsonObject;
    }

    @Override
    public JSONObject getTitleDetail(JSONObject param) {
        JSONObject jsonObject = new JSONObject();
        EntityWrapper<BlogTitleDetail> ew = new EntityWrapper<>();
        RowBounds rowBounds = PageUtil.getRowBounds(param);
        int total = blogTitleDetailMapper.selectCount(ew);
        //column,value
        ew.like(param.getString("column"),param.getString("value"));
        List<BlogTitleDetail> pageList = blogTitleDetailMapper.selectPage(rowBounds,ew);
        jsonObject = JsonMaker.List2Json(pageList);
        jsonObject.put("total",total);
        jsonObject.put("size",param.getInteger("size"));
        jsonObject.put("current",param.getInteger("current"));
        return jsonObject;
    }
}
