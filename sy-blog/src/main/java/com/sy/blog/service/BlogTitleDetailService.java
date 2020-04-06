package com.sy.blog.service;

import com.alibaba.fastjson.JSONObject;
import com.sy.blog.beans.BlogTitleDetail;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Su.Yang
 * @since 2019-10-15
 */
public interface BlogTitleDetailService extends IService<BlogTitleDetail> {
    JSONObject addTitleDetail(JSONObject param);

    JSONObject deleteTitleDetail(JSONObject param);

    JSONObject updateTitleDetail(JSONObject param);

    JSONObject getTitleDetail(JSONObject param);
}
