package com.sy.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sy.blog.beans.BlogClassify;
import com.sy.blog.beans.BlogLiuyan;
import com.sy.blog.beans.BlogUser;
import com.sy.blog.mapper.BlogLiuyanMapper;
import com.sy.blog.mapper.BlogUserMapper;
import com.sy.blog.service.BlogLiuyanService;
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
public class BlogLiuyanServiceImpl extends ServiceImpl<BlogLiuyanMapper, BlogLiuyan> implements BlogLiuyanService {

    @Autowired
    private BlogLiuyanMapper blogLiuyanMapper;

    @Autowired
    private BlogUserMapper blogUserMapper;

    @Override
    public JSONObject addLiuyan(JSONObject param) {
        JSONObject jsonObject = new JSONObject();
        BlogLiuyan blogLiuyan = JSON.toJavaObject(param, BlogLiuyan.class);
        blogLiuyan.setLiuyanDate(new Date());
        boolean flag = this.insert(blogLiuyan);
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
    public JSONObject deleteLiuyan(JSONObject param) {
        JSONObject jsonObject = new JSONObject();
        EntityWrapper<BlogLiuyan> ew = new EntityWrapper<>();
        ew.in("id",param.getString("id"));//格式(1,2,3)
        boolean flag = false;
        if(param.getInteger("type") == 1) {//物理删除
            flag = this.delete(ew);
        } else {//逻辑删除
            BlogLiuyan blogLiuyan = new BlogLiuyan();
            blogLiuyan.setStatus(0);
            flag = this.update(blogLiuyan,ew);
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
    public JSONObject updateLiuyan(JSONObject param) {
        JSONObject jsonObject = new JSONObject();
        BlogLiuyan blogLiuyan = JSON.toJavaObject(param, BlogLiuyan.class);
        if (this.updateById(blogLiuyan)) {
            jsonObject.put("code",200);
            jsonObject.put("msg","成功");
        } else {
            jsonObject.put("code",400);
            jsonObject.put("msg","失败");
        }
        return jsonObject;
    }

    @Override
    public JSONObject getLiuyan(JSONObject param) {
        JSONObject jsonObject = new JSONObject();
        EntityWrapper<BlogLiuyan> ew = new EntityWrapper<>();
        RowBounds rowBounds = PageUtil.getRowBounds(param);
        //column,value
        ew.like(param.getString("column"),param.getString("value"));
        int total = blogLiuyanMapper.selectCount(ew);
        List<BlogLiuyan> pageList = blogLiuyanMapper.selectPage(rowBounds,ew);
        jsonObject = JsonMaker.List2Json(pageList);
        jsonObject.put("total",total);
        jsonObject.put("size",param.getInteger("size"));
        jsonObject.put("current",param.getInteger("current"));
        return jsonObject;
    }

    @Override
    public Integer getLiuyanCount(JSONObject param) {
        JSONObject jsonObject = new JSONObject();
        EntityWrapper<BlogLiuyan> ew = new EntityWrapper<>();
        //column,value
        ew.eq(param.getString("key"),param.getInteger("value"));
        int total = blogLiuyanMapper.selectCount(ew);
        return total;
    }

    @Override
    public JSONObject getLiuyanByArt(JSONObject param) {
        JSONObject jsonObject = new JSONObject();
        List<JSONObject> jsonObjects = new ArrayList<>();
        EntityWrapper<BlogLiuyan> ew = new EntityWrapper<>();
        RowBounds rowBounds = PageUtil.getRowBounds(param);
        ew.eq("reply_id",param.getInteger("replyId"));
        ew.eq("parent_id",0);
        int total = blogLiuyanMapper.selectCount(ew);
        List<BlogLiuyan> pageList = blogLiuyanMapper.selectPage(rowBounds,ew);
        for (BlogLiuyan blogLiuyan : pageList) {
            jsonObject.put("id",blogLiuyan.getId());
            jsonObject.put("userId",blogLiuyan.getUserId());
            BlogUser blogUser = blogUserMapper.selectById(blogLiuyan.getUserId());
            jsonObject.put("userName",blogUser.getNickName());
//            jsonObject.put("userHead",blogUser.getHeadImage());
            jsonObject.put("content",blogLiuyan.getContent());
            jsonObject.put("replyType",blogLiuyan.getReplyType());
            jsonObject.put("parentId",blogLiuyan.getParentId());
            jsonObject.put("replyId",blogLiuyan.getReplyId());
            jsonObject.put("status",blogLiuyan.getStatus());
            jsonObject.put("liuyanDate",blogLiuyan.getLiuyanDate().getTime());
            jsonObjects.add(jsonObject);
            jsonObject = new JSONObject();
        }
        jsonObject.put("code",200);
        jsonObject.put("msg","成功");
        jsonObject.put("data",jsonObjects);
        jsonObject.put("total",total);
        jsonObject.put("size",param.getInteger("size"));
        jsonObject.put("current",param.getInteger("current"));
        return jsonObject;
    }

    @Override
    public JSONObject getLiuyanByParentIdAndArt(JSONObject param) {
        JSONObject jsonObject = new JSONObject();
        List<JSONObject> jsonObjects = new ArrayList<>();
        EntityWrapper<BlogLiuyan> ew = new EntityWrapper<>();
        RowBounds rowBounds = PageUtil.getRowBounds(param);
        ew.eq("reply_id",param.getInteger("replyId"));
        ew.eq("parent_id",param.getInteger("parentId"));
        int total = blogLiuyanMapper.selectCount(ew);
        List<BlogLiuyan> pageList = blogLiuyanMapper.selectPage(rowBounds,ew);
        for (BlogLiuyan blogLiuyan : pageList) {
            jsonObject.put("id",blogLiuyan.getId());
            jsonObject.put("userId",blogLiuyan.getUserId());
            BlogUser blogUser = blogUserMapper.selectById(blogLiuyan.getUserId());
            jsonObject.put("userName",blogUser.getNickName());
//            jsonObject.put("userHead",blogUser.getHeadImage());
            jsonObject.put("content",blogLiuyan.getContent());
            jsonObject.put("replyType",blogLiuyan.getReplyType());
            jsonObject.put("parentId",blogLiuyan.getParentId());
            jsonObject.put("parentName",blogUserMapper.selectById(this.selectById(blogLiuyan.getParentId()).getUserId()).getNickName());
            jsonObject.put("replyId",blogLiuyan.getReplyId());
            jsonObject.put("status",blogLiuyan.getStatus());
            jsonObject.put("liuyanDate",blogLiuyan.getLiuyanDate().getTime());
            jsonObjects.add(jsonObject);
            jsonObject = new JSONObject();
        }
        jsonObject.put("code",200);
        jsonObject.put("msg","成功");
        jsonObject.put("data",jsonObjects);
        jsonObject.put("total",total);
        jsonObject.put("size",param.getInteger("size"));
        jsonObject.put("current",param.getInteger("current"));
        return jsonObject;
    }
}
