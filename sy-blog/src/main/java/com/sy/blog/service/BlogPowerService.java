package com.sy.blog.service;

import com.alibaba.fastjson.JSONObject;
import com.sy.blog.beans.BlogPower;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Su.Yang
 * @since 2019-10-15
 */
public interface BlogPowerService extends IService<BlogPower> {
    JSONObject addPower(JSONObject param);

    JSONObject deletePower(JSONObject param);

    JSONObject updatePower(JSONObject param);

    JSONObject getPower(JSONObject param);
}
