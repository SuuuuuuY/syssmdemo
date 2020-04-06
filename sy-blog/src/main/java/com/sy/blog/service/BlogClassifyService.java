package com.sy.blog.service;

import com.alibaba.fastjson.JSONObject;
import com.sy.blog.beans.BlogClassify;
import com.baomidou.mybatisplus.service.IService;
import org.springframework.scheduling.annotation.Async;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Su.Yang
 * @since 2019-10-15
 */
public interface BlogClassifyService extends IService<BlogClassify> {
    JSONObject addClassify(JSONObject param);

    JSONObject deleteClassify(JSONObject param);

    JSONObject updateClassify(JSONObject param);

    JSONObject getClassify(JSONObject param);
}
