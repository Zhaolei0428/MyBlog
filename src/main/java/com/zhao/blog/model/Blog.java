package com.zhao.blog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "blog")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long blogId;					//主键ID

    private String blogName;			//博客名称

    private String blogImg;				//博客图片

    private String introduction;		//博客介绍

    private String content;				//博客内容

    private Date time;					//发布时间

    private int browse;					//浏览数量

    private int praise;					//赞的数量

    private String blogType;			//博客类型

    private String reprintedUrl;		//转载Url

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "blog_tag", joinColumns = @JoinColumn(name = "blogId"),
            inverseJoinColumns = @JoinColumn(name = "tagId"))
    private List<Tag> tags;

}
