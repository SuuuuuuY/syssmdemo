package com.sy.blog.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.session.RowBounds;

public class PageUtil {

    public static int pageOperation (int size ,int current) {
        if(size <= 0 || current <=0) {return 0;}
        return ( current * size ) - size;
    }

    public static RowBounds getRowBounds (JSONObject param) {
        return new RowBounds(PageUtil.pageOperation(param.getInteger("size"), param.getInteger("current")),
                param.getInteger("size"));
    }

}
