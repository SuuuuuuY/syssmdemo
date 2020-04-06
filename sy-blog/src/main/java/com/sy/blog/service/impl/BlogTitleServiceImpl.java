package com.sy.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sy.blog.beans.BlogTitle;
import com.sy.blog.beans.BlogTitle;
import com.sy.blog.beans.BlogUser;
import com.sy.blog.mapper.BlogClassifyMapper;
import com.sy.blog.mapper.BlogTitleMapper;
import com.sy.blog.mapper.BlogTitleMapper;
import com.sy.blog.mapper.BlogUserMapper;
import com.sy.blog.service.BlogTitleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.sy.blog.utils.JsonMaker;
import com.sy.blog.utils.PageUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class BlogTitleServiceImpl extends ServiceImpl<BlogTitleMapper, BlogTitle> implements BlogTitleService {


    @Autowired
    private BlogTitleMapper blogTitleMapper;

    @Autowired
    private BlogClassifyMapper blogClassifyMapper;

    @Autowired
    private BlogUserMapper blogUserMapper;

    @Override
    public JSONObject addTitle(JSONObject param) {
        JSONObject jsonObject = new JSONObject();
        BlogTitle blogTitle = JSON.toJavaObject(param, BlogTitle.class);
        blogTitle.setStatus(1);
        blogTitle.setPublishDate(new Date());
        boolean flag = this.insert(blogTitle);
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
    public JSONObject deleteTitle(JSONObject param) {
        JSONObject jsonObject = new JSONObject();
        EntityWrapper<BlogTitle> ew = new EntityWrapper<>();
        ew.in("id",param.getString("id"));//格式(1,2,3)
        boolean flag = false;
        if(param.getInteger("type") == 1) {//物理删除
            flag = this.delete(ew);
        } else {//逻辑删除
            BlogTitle blogTitle = new BlogTitle();
            blogTitle.setStatus(0);
            flag = this.update(blogTitle,ew);
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
    public JSONObject updateTitle(JSONObject param) {
        JSONObject jsonObject = new JSONObject();
        BlogTitle blogTitle = JSON.toJavaObject(param, BlogTitle.class);
        blogTitle.setUpdateDate(new Date());
        if (this.updateById(blogTitle)) {
            jsonObject.put("code",200);
            jsonObject.put("msg","成功");
        } else {
            jsonObject.put("code",400);
            jsonObject.put("msg","失败");
        }
        return jsonObject;
    }

    @Override
    public JSONObject getTitle(JSONObject param) {
        JSONObject jsonObject = new JSONObject();
        EntityWrapper<BlogTitle> ew = new EntityWrapper<>();
        RowBounds rowBounds = PageUtil.getRowBounds(param);
        int total = blogTitleMapper.selectCount(ew);
        //column,value
        ew.like(param.getString("column"),param.getString("value"));
        List<BlogTitle> pageList = blogTitleMapper.selectPage(rowBounds,ew);
        jsonObject = JsonMaker.List2Json(pageList);
        jsonObject.put("total",total);
        jsonObject.put("size",param.getInteger("size"));
        jsonObject.put("current",param.getInteger("current"));
        return jsonObject;
    }

    @Override
    public JSONObject getTitleNoOutline(JSONObject param) {
        JSONObject jsonObject = new JSONObject();
        List<JSONObject> object = new ArrayList<>();
        EntityWrapper<BlogTitle> ew = new EntityWrapper<>();
        RowBounds rowBounds = PageUtil.getRowBounds(param);
        int total = blogTitleMapper.selectCount(ew);
        //column,value
        ew.setSqlSelect("id,title,classify_id as classifyId,author,status,publish_date as publishDate,update_date as updateDate");
        List<BlogTitle> pageList = blogTitleMapper.selectPage(rowBounds,ew);
        for (BlogTitle blogTitle : pageList) {
            jsonObject.put("id",blogTitle.getId());
            jsonObject.put("title",blogTitle.getTitle());
            jsonObject.put("author",blogTitle.getAuthor());
            jsonObject.put("authorName",blogUserMapper.selectById(blogTitle.getAuthor()).getNickName());
            jsonObject.put("classifyId",blogTitle.getClassifyId());
            if(blogTitle.getClassifyId() == 0) {
                jsonObject.put("classifyName","动态");
            } else {
                jsonObject.put("classifyName",blogClassifyMapper.selectById(blogTitle.getClassifyId()).getClassifyName());
            }
            jsonObject.put("status",blogTitle.getStatus());
            jsonObject.put("publishDate",blogTitle.getPublishDate());
            jsonObject.put("updateDate",blogTitle.getUpdateDate());
            object.add(jsonObject);
            jsonObject = new JSONObject();
        }
        jsonObject.put("data",object);
        jsonObject.put("msg","成功");
        jsonObject.put("code",200);
        jsonObject.put("total",total);
        jsonObject.put("size",param.getInteger("size"));
        jsonObject.put("current",param.getInteger("current"));
        return jsonObject;
    }

    @Override
    public JSONObject getTitleById(JSONObject param) {
        JSONObject jsonObject = new JSONObject();
        BlogTitle pageList = blogTitleMapper.selectById(param.getInteger("id"));
        JSONObject object = new JSONObject();
        object.put("id",pageList.getId());
        object.put("title",pageList.getTitle());
        object.put("author",pageList.getAuthor());
        object.put("authorName",blogUserMapper.selectById(pageList.getAuthor()).getNickName());
        object.put("classifyId",pageList.getClassifyId());
        if(pageList.getClassifyId() == 0) {
            jsonObject.put("classifyName","动态");
        } else {
            jsonObject.put("classifyName",blogClassifyMapper.selectById(pageList.getClassifyId()).getClassifyName());
        }
        object.put("outline",pageList.getOutline());
        object.put("status",pageList.getStatus());
        object.put("publishDate",pageList.getPublishDate());
        object.put("updateDate",pageList.getUpdateDate());
        //object.put("img",pageList.getImg());

        jsonObject.put("code",200);
        jsonObject.put("msg","成功");
        jsonObject.put("data",object);
        return jsonObject;
    }

    @Override
    public JSONObject getTitleAndOutline(JSONObject param) {
        JSONObject jsonObject = new JSONObject();
        EntityWrapper<BlogTitle> ew = new EntityWrapper<>();
        RowBounds rowBounds = PageUtil.getRowBounds(param);
        //column,value
        ew.setSqlSelect("id,title,classify_id as classifyId, LEFT(outline, 30) AS outline,author,status,publish_date as publishDate,update_date as updateDate");
        if(param.containsKey("column")) {
            ew.like(param.getString("column"), param.getString("value"));
        }
        if(param.containsKey("classifyId")){
            ew.eq("classify_id",param.getInteger("classifyId"));
        }
        ew.ne("classify_id",0);
        int total = blogTitleMapper.selectCount(ew);
        List<BlogTitle> pageList = blogTitleMapper.selectPage(rowBounds,ew);
        jsonObject = JsonMaker.List2Json(pageList);
        jsonObject.put("total",total);
        jsonObject.put("size",param.getInteger("size"));
        jsonObject.put("current",param.getInteger("current"));
        return jsonObject;
    }

    @Override
    public JSONObject getTimex(JSONObject param) {
        JSONObject jsonObject = new JSONObject();
        EntityWrapper<BlogTitle> ew = new EntityWrapper<>();
        RowBounds rowBounds = PageUtil.getRowBounds(param);
        //column,value
        ew.setSqlSelect("id,outline,publish_date as publishDate");
        ew.eq("classify_id",0);
        ew.orderBy("publish_date",false);
        int total = blogTitleMapper.selectCount(ew);
        List<BlogTitle> pageList = blogTitleMapper.selectPage(rowBounds,ew);
        jsonObject = JsonMaker.List2Json(pageList);
        jsonObject.put("total",total);
        jsonObject.put("size",param.getInteger("size"));
        jsonObject.put("current",param.getInteger("current"));
        return jsonObject;

    }
}
