package top.lpepsi.vblog.controller.oss;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import top.lpepsi.vblog.dto.Response;

import top.lpepsi.vblog.utils.AliyunOssUtil;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * @program: v-blog
 * @description: 图片上传接口
 * @author: 林北
 * @create: 2020-02-15 11:48
 **/
@Controller
@RequestMapping("/oss")
public class AliyunOssController {

    @Autowired
    private AliyunOssUtil aliyunOssUtil;


    /**
     * 上传本地图片
     *
     * @param file
     * @return
     */
    @RequestMapping("/upload")
    @ResponseBody
    public Response upLoad(@RequestParam("file") MultipartFile file) {
        String filename = file.getOriginalFilename();
        Response response = new Response();
        System.out.println(filename);
        try {
            if (file != null) {
                if (!"".equals(filename.trim())) {
                    File newFile = new File(filename);
                    FileOutputStream outputStream = new FileOutputStream(newFile);
                    outputStream.write(file.getBytes());
                    outputStream.close();
                    file.transferTo(newFile);
                    String url = aliyunOssUtil.upLoad(newFile);
                    response = Response.success(url);
                    //上传图片后会在idea生成一个文件，上传成功后将其删除
                    newFile.delete();
                }
            }
        } catch (FileNotFoundException e) {
            response = Response.failure();
            e.printStackTrace();
        } catch (IOException e) {
            response = Response.failure();
            e.printStackTrace();
        }
        return response;
    }

}
