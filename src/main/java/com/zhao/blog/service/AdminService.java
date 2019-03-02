package com.zhao.blog.service;

import com.zhao.blog.dto.Page;
import com.zhao.blog.enums.PageEnum;
import com.zhao.blog.model.Blog;
import com.zhao.blog.model.Tag;
import com.zhao.blog.model.Type;
import com.zhao.blog.repository.BlogRepository;
import com.zhao.blog.repository.TagRepository;
import com.zhao.blog.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class AdminService {
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private TypeRepository typeRepository;

    public Page getBlogList(int pageNum, String name) {
        name = name.trim();
        long count = blogRepository.getBlogCount(name);
        Page page = new Page(PageEnum.adminPageSize.getPageSize(), count, pageNum);
        Pageable pageable = new PageRequest(page.getOffSet(), page.getOffSet()+page.getPageSize(), Sort.Direction.DESC, "blogName");
        org.springframework.data.domain.Page<Blog> blogPage = blogRepository.getBlogByBlogNameContains(name, pageable);
        List<Blog> blogList = blogPage.getContent();
        page.setResult(blogList);
        return page;
    }

    public void updateBlogImg(String blogImg, long blogId) {
        Blog blog = blogRepository.getBlogByBlogId(blogId);
        blog.setBlogImg(blogImg);
        blogRepository.save(blog);
    }

    public void delBlog(long blogId) {blogRepository.delete(blogId);}

    /**
     * 获取博客信息
     * @param blogId
     * @return
     */
    public HashMap<String, Object> getBlog(long blogId) {
        HashMap<String, Object> map = new HashMap<>();
        Blog blog = blogRepository.getBlogByBlogId(blogId);
        List<Tag> tagList = blog.getTags();
        map.put("blog", blog);
        map.put("tagList", tagList);
        return map;
    }

    /**
     * 修改博客
     * @param blog
     * @param blogTag
     * @return
     */
    public void updateBlog(Blog blog, String blogTag) {
        if(blogTag != "") {
            String[] tags = blogTag.split(",");
            List<Tag> tagList = new ArrayList<>();
            for(int i = 0; i < tags.length; i++){
                Tag tag = tagRepository.findOneByTagId(Long.parseLong(tags[i]));
                tagList.add(tag);
            }
            blog.setTags(tagList);
        }
        blogRepository.save(blog);
    }

    /**
     * 插入博客
     * @param blog
     * @param blogTag
     * @return
     */
    public long insertBlog(Blog blog, String blogTag) {
        if(blogTag != "") {
            String[] tags = blogTag.split(",");
            List<Tag> tagList = new ArrayList<>();
            for(int i = 0; i < tags.length; i++){
                Tag tag = tagRepository.findOneByTagId(Long.parseLong(tags[i]));
                tagList.add(tag);
            }
            blog.setTags(tagList);
        }
        blog.setBlogImg("/images/default_logo.jpg");
        blog = blogRepository.save(blog);

        return blog.getBlogId();
    }

    public Page getBlogTypeList(int pageNum) {
        long count = typeRepository.count();
        Page page = new Page(PageEnum.adminPageSize.getPageSize(), count, pageNum);
        Pageable pageable = new PageRequest(page.getOffSet(), page.getOffSet()+page.getPageSize(), Sort.Direction.DESC, "typeId");
        org.springframework.data.domain.Page<Type> typePage = typeRepository.findAll(pageable);
        List<Type> typeList = typePage.getContent();
        page.setResult(typeList);
        return page;
    }

    public void addBlogType(String typeName) {
        Type type = new Type();
        type.setTypeName(typeName);
        typeRepository.save(type);
    }

    public Page getBlogTagList(int pageNum) {
        long count = tagRepository.count();
        Page page = new Page(PageEnum.adminPageSize.getPageSize(), count, pageNum);
        Pageable pageable = new PageRequest(page.getOffSet(), page.getOffSet()+page.getPageSize(), Sort.Direction.DESC, "tagId");
        org.springframework.data.domain.Page<Tag> tagPage = tagRepository.findAll(pageable);
        List<Tag> tagList = tagPage.getContent();
        page.setResult(tagList);
        return page;
    }

    public void addBlogTag(String tagName) {
        Tag tag = new Tag();
        tag.setTagName(tagName);
        tagRepository.save(tag);
    }


}
