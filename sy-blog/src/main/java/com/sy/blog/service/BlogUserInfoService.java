package com.sy.blog.service;

import com.alibaba.fastjson.JSONObject;
import com.sy.blog.beans.BlogUserInfo;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Su.Yang
 * @since 2019-10-15
 */
public interface BlogUserInfoService extends IService<BlogUserInfo> {

    JSONObject addUserInfo(JSONObject param);

    JSONObject deleteUserInfo(JSONObject param);

    JSONObject updateUserInfo(JSONObject param);

    JSONObject getUserInfo(JSONObject param);
}
