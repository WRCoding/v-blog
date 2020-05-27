package top.lpepsi.vblog.service.admin.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.lpepsi.vblog.constant.RedisKeyConstant;
import top.lpepsi.vblog.dao.AdminMapper;
import top.lpepsi.vblog.dao.BlogMapper;
import top.lpepsi.vblog.dto.Comment;
import top.lpepsi.vblog.dto.Detail;
import top.lpepsi.vblog.dto.Edit;
import top.lpepsi.vblog.dto.Response;
import top.lpepsi.vblog.jwt.JwtUserDetails;
import top.lpepsi.vblog.service.admin.AdminService;
import top.lpepsi.vblog.service.blog.BlogService;
import top.lpepsi.vblog.service.blog.impl.BlogServiceImpl;
import top.lpepsi.vblog.service.cache.RedisService;
import top.lpepsi.vblog.service.es.EsBlogService;
import top.lpepsi.vblog.service.es.impl.EsBlogServiceImpl;
import top.lpepsi.vblog.utils.DateUtil;
import top.lpepsi.vblog.utils.JwtTokenUtil;
import top.lpepsi.vblog.utils.RedisUtil;
import top.lpepsi.vblog.vdo.CommentDO;
import top.lpepsi.vblog.vdo.RecordDO;
import top.lpepsi.vblog.vdo.ResultCode;
import top.lpepsi.vblog.vdo.TagDO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @program: v-blog
 * @description: 管理
 * @author: 林北
 * @create: 2020-03-29 10:38
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class AdminServiceImpl implements AdminService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private EsBlogService esBlogService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private RedisUtil redisUtil;


    @Override
    public Response blogManager(String username, Integer pageNum) {
        PageHelper.startPage(pageNum, 7);
        List<Detail> list = adminMapper.blogManager(username);
        return Response.success(new PageInfo<>(list));
    }

    @Override
    public Response personalData(String username) {
        Integer commentNum = adminMapper.getCommentNum(username);
        Integer commentChileNum = adminMapper.getCommentChileNum(username);
        Integer blogNum = adminMapper.getBlogNum(username);
        Integer viewNum = adminMapper.getViewNum(username);
        HashMap<String, Integer> map = new HashMap<>((int) (2 / 0.75F + 1));
        map.put("commentNum", commentChileNum + commentNum);
        map.put("blogNum", blogNum);
        map.put("viewNum", viewNum);
        return Response.success(map);
    }

    @Override
    public Response editByArticleId(Integer articleId) {
        if (articleId < 0 || articleId == null) {
            return Response.customize(ResultCode.INVALID_ARGUMENT, "articleId无效");
        }
        Detail editBlog = redisService.getBlogFromRedis(String.valueOf(articleId));
        if (editBlog == null) {
            return Response.failure("文章不存在");
        }
        return Response.success(editBlog);
    }

    @Override
    public Response deleteBlog(Integer articleId) {
        Integer result = adminMapper.deleteBlog(articleId);
        Detail blog = (Detail) redisUtil.hashGet(RedisKeyConstant.BLOG, String.valueOf(articleId));
        List<TagDO> tags = blog.getTags();
        List<String> tagNameList = new ArrayList<>();
        tags.forEach(tagDO -> {
            tagNameList.add(tagDO.getTagName());
        });
        LOGGER.info("tagNameList: "+tagNameList);
        LOGGER.info("tagNameList.length:  "+tagNameList.size());
        if (result == 1){
            if (tagNameList.size() != 0){
                adminMapper.changTagNum(tagNameList);
                redisUtil.delete(RedisKeyConstant.TAGS);
                redisUtil.delete(RedisKeyConstant.TAG_BLOG);
            }
            esBlogService.delete(articleId);
            redisUtil.hashDelete(RedisKeyConstant.BLOG, String.valueOf(articleId));
            redisUtil.listRemove(RedisKeyConstant.BLOG_LIST, String.valueOf(articleId));
            redisUtil.zSetDelete(RedisKeyConstant.VIEW,String.valueOf(articleId));
            redisUtil.delete(RedisKeyConstant.ARCHIVE);
            redisUtil.delete(RedisKeyConstant.ARCHIVE_BLOG);

            return Response.success("删除成功");
        }
        return Response.failure("删除失败");
    }

    @Override
    public Response updateBlog(Edit edit) {
        if (edit == null) {
            LOGGER.error("edit为空");
            return Response.customize(ResultCode.INVALID_ARGUMENT, "edit为空");
        }
        try {
            int title = adminMapper.updateBlogTitle(edit);
            int content = adminMapper.updateBlogContent(edit);
            if (title == 1 || content == 1) {
                redisUtil.hashDelete(RedisKeyConstant.BLOG, String.valueOf(edit.getId()));
                redisUtil.hashPut(RedisKeyConstant.BLOG, String.valueOf(edit.getId()), blogMapper.findBlog(edit.getId()));
                return Response.success("更新成功");
            } else {
                return Response.failure("更新失败");
            }
        } catch (Exception e) {
            LOGGER.error("updateBlog异常： "+e.getMessage());
        }
        return Response.failure("更新失败");
    }

    @Override
    public Response getPersonalComment(String author,Integer pageNum) {
        if(author == null){
            LOGGER.error("getPersonalComment： author不能为空");
            return Response.customize(ResultCode.INVALID_ARGUMENT,"参数无效");
        }
        try {
            PageHelper.startPage(pageNum, 7);
            List<CommentDO> personalComment = adminMapper.getPersonalComment(author);
            for (CommentDO commentDO : personalComment) {
                if (commentDO.getChildComments() != null){
                    for (CommentDO aDo : commentDO.getChildComments()) {
                        aDo.setCreateTime(DateUtil.date2String(aDo.getCreateBy(), "yyyy-MM-dd HH:mm:ss"));
                    }
                }
                commentDO.setCreateTime(DateUtil.date2String(commentDO.getCreateBy(), "yyyy-MM-dd HH:mm:ss"));
            }
            PageInfo<CommentDO> pageInfo = new PageInfo<>(personalComment);
            return Response.success(pageInfo);
        }catch (Exception e){

        }
        return null;
    }

    @Override
    public Response deleteComment(Integer commentId) {
        Integer result = adminMapper.deleteComment(commentId);
        if (result == 1){
            return  Response.success("删除成功");
        }else {
            return Response.failure("评论删除失败");
        }
    }

    @Override
    public Response changPwd(String newPassword) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            Integer changPwd = adminMapper.changPwd(username, new BCryptPasswordEncoder().encode(newPassword));
            if (changPwd == 1){
                JwtTokenUtil.secret = new BCryptPasswordEncoder().encode(newPassword);
                return Response.success("修改成功");
            }else {
                LOGGER.error("changPwd:username: "+username+"修改密码错误");
                return Response.failure("修改失败");
            }
        }catch (Exception e){
            LOGGER.error("changPwd:username: "+username+"修改密码错误,exception: "+e.getMessage());
            return Response.failure("修改失败");
        }
    }

    @Override
    public void updateRecord() {
        List<String> userNameList = adminMapper.getUserName();
        List<RecordDO> recordDOList = new ArrayList<>();
        LOGGER.info("userNameList: "+userNameList);
        userNameList.forEach( userName -> {
            RecordDO recordDO = new RecordDO(userName, adminMapper.getBlogNum(userName));
            recordDOList.add(recordDO);
        });
        LOGGER.info("recordDOList: "+recordDOList);
        adminMapper.updateRecordNum(recordDOList);
    }

    @Override
    public void updateRecordView() {
        List<String> userNameList = adminMapper.getUserName();
        List<RecordDO> recordDOList = new ArrayList<>();
        LOGGER.info("userNameList: "+userNameList);
        userNameList.forEach( userName -> {
            RecordDO recordDO = new RecordDO(userName, adminMapper.getViewNum(userName));
            recordDOList.add(recordDO);
        });
        LOGGER.info("recordDOList: "+recordDOList);
        adminMapper.updateRecordView(recordDOList);
    }

    @Override
    public Response getRecord(String username, String key) {
        String tableName = "blog_record_"+key;
        List<RecordDO> recordList = adminMapper.getRecord(username, tableName);
        LOGGER.info("recordList: "+recordList);
        return Response.success(recordList);
    }

}
