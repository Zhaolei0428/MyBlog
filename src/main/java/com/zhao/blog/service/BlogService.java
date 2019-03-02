package com.zhao.blog.service;

import com.zhao.blog.model.Blog;
import com.zhao.blog.model.Tag;
import com.zhao.blog.model.Type;
import com.zhao.blog.repository.BlogRepository;
import com.zhao.blog.repository.TagRepository;
import com.zhao.blog.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Set;


@Service("blogService")
public class BlogService {
    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TypeRepository typeRepository;

    public List<Blog> getAllBlog(){
        return blogRepository.findAll();
    }

    public List<Long> getAllBlogId(String blogType) {
        if(blogType == null || blogType.isEmpty())
            return blogRepository.getAllBlogId();
        else
            return blogRepository.getAllBlogIdByBlogType(blogType);
    }

    public List<Tag> getAllBlogTag() {return tagRepository.findAll(); }

    public Blog getBlogByBlogId(Long blogId) {return blogRepository.getBlogByBlogId(blogId);}

    public List<Type> getAllBlogType() {return typeRepository.findAll(); }

    public List<Blog> getNewBlog() {return blogRepository.getBlogByNew();}


    public void updateBlogNum(List<HashMap<String, Object>> list)
    {
        for(HashMap<String, Object> map: list) {
            long blogId = (long)map.get("blogId");
            int browse = (int)map.get("browse");
            Blog blog = blogRepository.findOne(blogId);
            blog.setBrowse(browse);
            blogRepository.save(blog);
        }
    }
}
