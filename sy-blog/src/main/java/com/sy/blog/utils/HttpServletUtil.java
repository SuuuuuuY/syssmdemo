package com.sy.blog.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class HttpServletUtil {
    public static boolean response(HttpServletRequest req, HttpServletResponse res, JSONObject jsonObject) {
        PrintWriter printWriter = null;
        try {
            printWriter = res.getWriter();
            printWriter.print(JSON.toJSONString(jsonObject));
            printWriter.flush();
            printWriter.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
