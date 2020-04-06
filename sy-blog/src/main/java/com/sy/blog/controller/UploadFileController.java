package com.sy.blog.controller;

import com.alibaba.fastjson.JSONObject;
import com.sy.blog.utils.CheckMakeUtil;
import com.sy.blog.utils.HttpServletUtil;
import com.sy.blog.utils.JsonMaker;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UploadFileController {
    private Logger logger = Logger.getLogger(UploadFileController.class);

    @ResponseBody
    @RequestMapping(value = "/uploadFile")
    public void addUserInfo(HttpServletResponse res, HttpServletRequest request, @Param("file") MultipartFile file) throws IOException {
        PrintWriter printWriter = null;
        JSONObject param = JsonMaker.checking(res, request);
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> map2 = new HashMap<String, Object>();
        if (param == null) {
            map.put("code", 1);//0表示成功，1失败
            map.put("msg", "没有登陆呢");//提示消息
            String result = new JSONObject(map).toString();
            HttpServletUtil.response(request, res, new JSONObject(map));
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
        String re = sdf.format(new Date());
        //服务器上使用
        String rootPath = request.getServletContext().getRealPath("/static/img/uploads/");//target的目录
        //本地使用
        //String rootPath ="J://test/Users/liuyanzhao/Documents/uploads/";
        //创建年月日文件夹
        Calendar date = Calendar.getInstance();
        File dateDirs = new File(date.get(Calendar.YEAR)
                + File.separator + (date.get(Calendar.MONTH) + 1)//国外是0-11月，所以+1
                + File.separator + date.get(Calendar.DAY_OF_MONTH));
        //原始名称
        String originalFilename = file.getOriginalFilename();
        //新的文件名称
        //String newFileName = res + originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = CheckMakeUtil.RoNum(6)+ originalFilename.substring(originalFilename.lastIndexOf("."));

        //新文件
        File newFile = null;
        do {newFile = new File(rootPath + File.separator +
                    dateDirs + File.separator +
                    newFileName);
        } while (newFile.exists());

        //判断目标文件所在的目录是否存在
        if (!newFile.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
            newFile.getParentFile().mkdirs();
        }
        System.out.println(newFile);
        //将内存中的数据写入磁盘
        file.transferTo(newFile);
        //完整的url
        String fileUrl = "/blog/static/img/uploads/" + date.get(Calendar.YEAR) + "/"
                + (date.get(Calendar.MONTH) + 1) + "/"
                + date.get(Calendar.DAY_OF_MONTH) + "/"
                + newFileName;

        map.put("code", 0);//0表示成功，1失败
        map.put("msg", "上传成功");//提示消息
        map.put("data", map2);
        map2.put("src", fileUrl);//图片url
        map2.put("title", newFileName);//图片名称，这个会显示在输入框里
        map2.put("style","display:inline-block; height: auto; max-width:100%");
        //String result = new JSONObject(map).toString();
        HttpServletUtil.response(request, res, new JSONObject(map));
    }

}
