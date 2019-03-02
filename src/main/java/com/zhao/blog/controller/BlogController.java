package com.zhao.blog.controller;

import com.zhao.blog.cache.RedisCache;
import com.zhao.blog.dto.BlogResult;
import com.zhao.blog.model.Blog;
import com.zhao.blog.model.Tag;
import com.zhao.blog.model.Type;
import com.zhao.blog.service.BlogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping(value = "/blog")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @Resource
    private RedisCache redisCache;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void rightContent(Model model) {
        List<Tag> tagList = redisCache.getBlogTag();
        List<Type> typeList = redisCache.getBlogType();
        List<Blog> newBlogList = redisCache.getNewBlog();
        List<Blog> readBlogList = redisCache.getBlogByRead();
        model.addAttribute("tagList", tagList);
        model.addAttribute("typeList", typeList);
        model.addAttribute("newBlogList", newBlogList);
        model.addAttribute("readBlogList", readBlogList);
    }

    @GetMapping(value = "")
    public String goBlog(Model model) {
        long blogNum = redisCache.getBlogNum();
        long page = redisCache.getBlogPage(null);
        model.addAttribute("page", page);
        model.addAttribute("blogNum", Long.toString(blogNum));
        rightContent(model);
        return "/blog/blogList";
    }

    /**
     * 获取博客列表数据
     * @param page
     * @param blogType
     * @return
     */
    @PostMapping(value = "/list", produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public BlogResult getBlogList(@RequestParam(value="page",defaultValue="1",required=false)int page,
                                  @RequestParam(value="blogType",defaultValue="",required=false) String blogType) {
        BlogResult result = null;
        try {
            List<String> blogIdList = redisCache.getBlogIdList(page, blogType);
            if(blogIdList == null || blogIdList.isEmpty()){
                result = new BlogResult(false, "没有博客数据！");
                return result;
            }

            List<HashMap<String, Object>> blogInfoList = redisCache.getBlogInfoList(blogIdList);
            result = new BlogResult(true, blogInfoList);
        }catch (Exception e) {
            result = new BlogResult(false, e.getMessage());
            logger.error("getBlogList:" + e);
            e.printStackTrace();
        }finally {
            return  result;
        }
    }

    /**
     * 获取博客详情
     * @param model
     * @param blogId
     * @return
     */
    @GetMapping(value = "/info/{blogId}")
    public String goBlogInfo(Model model, @PathVariable("blogId") String blogId) {
        try {
            long blogNum = redisCache.getBlogNumById(blogId);
            Blog blogInfo = redisCache.getBlogInfo(blogId);
            model.addAttribute("blogNum", Long.toString(blogNum));
            model.addAttribute("blogInfo", blogInfo);
            rightContent(model);

        } catch (Exception e) {
            logger.error("goBlogInfo:" + e);
        }finally {
            return "blog/blogInfo";
        }
    }

    @GetMapping(value = "/{blogType}")
    public String goBlog(Model model, @PathVariable("blogType") String blogType) {
        try {
            long blogNum = redisCache.getBlogNum();
            long page = redisCache.getBlogPage(blogType);

            model.addAttribute("blogType", blogType);
            model.addAttribute("blogNum", blogNum);
            model.addAttribute("page", page);
            rightContent(model);
        }catch (Exception e) {
            logger.error("goBlog:" + e);
            e.printStackTrace();
        }finally {
            return "blog/blogList";
        }
    }

}
