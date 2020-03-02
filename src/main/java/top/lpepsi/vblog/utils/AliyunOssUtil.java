package top.lpepsi.vblog.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import top.lpepsi.vblog.constant.AliyunOssConfigConstant;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @program: v-blog
 * @description: OSS工具类
 * @author: 林北
 * @create: 2020-02-14 09:53
 **/
@Component
public class AliyunOssUtil {
    private static  final Logger LOGGER = LoggerFactory.getLogger(AliyunOssUtil.class);
    private static String bucketName = AliyunOssConfigConstant.BUCKE_NAME;
    private static String endPoint = AliyunOssConfigConstant.END_POINT;
    private static String accessKeyId = AliyunOssConfigConstant.ACCESSKEY_ID;
    private static String accessKeySecret = AliyunOssConfigConstant.ACCESSKEY_SECRET;
    private static String fileHost = AliyunOssConfigConstant.FILE_HOST;
    private String fileUrl;

    public  String upLoad(File file){
        boolean isImage = true;
        try {
            Image image = ImageIO.read(file);
            isImage = image == null?false:true;
        }catch (Exception e){
            e.printStackTrace();
        }
        LOGGER.info("----文件上传开始----"+file.getName());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = format.format(new Date());

        if(file == null){
            return null;
        }

        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);;
        try {
            if(!ossClient.doesBucketExist(bucketName)){
                ossClient.createBucket(bucketName);
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                ossClient.createBucket(createBucketRequest);
            }
            //设置文件路径
            fileUrl = fileHost + "/" + (dateStr + "/" + UUID.randomUUID().toString().replace("-", "")+"-"+file.getName());
            //如果是图片
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileUrl, file);
            if(isImage){
                fileUrl = "https://" + bucketName + "." + endPoint + "/" + fileUrl;
            }else {
                fileUrl = "非图片，不可预览。文件路径为：" + fileUrl;
            }
            //上传文件
            PutObjectResult putObjectResult = ossClient.putObject(putObjectRequest);
            //设置公开读权限
            ossClient.setBucketAcl(bucketName,CannedAccessControlList.PublicRead);
            if(putObjectResult != null){
                LOGGER.info("-----OSS文件上传成功,URL:-----"+fileUrl);
                return fileUrl;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(ossClient != null){
                ossClient.shutdown();
            }
        }
        return null;
    }
}
