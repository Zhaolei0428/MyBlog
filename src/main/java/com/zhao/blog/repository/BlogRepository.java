package com.zhao.blog.repository;

import com.zhao.blog.model.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    @Query(value = "select b.blogId from Blog b")
    List<Long> getAllBlogId();

    @Query(value = "select b.blogId from Blog b where b.blogType = ?1")
    List<Long> getAllBlogIdByBlogType(String blogType);

    Blog getBlogByBlogId(long blogId);

    @Query(value = "select b from Blog b ORDER BY b.blogId DESC, 5")
    List<Blog> getBlogByNew();

    @Query(value = "select count(b) from Blog b where b.blogName like CONCAT('%', ?1, '%') ")
    long getBlogCount(String name);

    Page<Blog> getBlogByBlogNameContains(String name, Pageable pageable);
}
