package com.sy.blog.service;

import com.alibaba.fastjson.JSONObject;
import com.sy.blog.beans.BlogTitle;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Su.Yang
 * @since 2019-10-15
 */
public interface BlogTitleService extends IService<BlogTitle> {

    JSONObject addTitle(JSONObject param);

    JSONObject deleteTitle(JSONObject param);

    JSONObject updateTitle(JSONObject param);

    JSONObject getTitle(JSONObject param);

    JSONObject getTitleNoOutline(JSONObject param);

    JSONObject getTitleById(JSONObject param);

    JSONObject getTitleAndOutline (JSONObject param);

    JSONObject getTimex (JSONObject param);
}
