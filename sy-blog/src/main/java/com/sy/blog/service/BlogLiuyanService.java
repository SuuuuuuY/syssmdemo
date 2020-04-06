package com.sy.blog.service;

import com.alibaba.fastjson.JSONObject;
import com.sy.blog.beans.BlogLiuyan;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Su.Yang
 * @since 2019-10-15
 */
public interface BlogLiuyanService extends IService<BlogLiuyan> {

    JSONObject addLiuyan(JSONObject param);

    JSONObject deleteLiuyan(JSONObject param);

    JSONObject updateLiuyan(JSONObject param);

    JSONObject getLiuyan(JSONObject param);

    Integer getLiuyanCount (JSONObject param);

    JSONObject getLiuyanByArt(JSONObject param);

    JSONObject getLiuyanByParentIdAndArt(JSONObject param);
}
