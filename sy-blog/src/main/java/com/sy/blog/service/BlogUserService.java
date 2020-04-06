package com.sy.blog.service;

import com.alibaba.fastjson.JSONObject;
import com.sy.blog.beans.BlogUser;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Su.Yang
 * @since 2019-10-15
 */
public interface BlogUserService extends IService<BlogUser> {
	JSONObject addUser(JSONObject param);

	JSONObject deleteUser(JSONObject param);

	JSONObject updateUser(JSONObject param);

	JSONObject getUser(JSONObject param);

	JSONObject login(JSONObject param);

	Boolean haveEmail(String email);

	Boolean haveName(String name);
}
