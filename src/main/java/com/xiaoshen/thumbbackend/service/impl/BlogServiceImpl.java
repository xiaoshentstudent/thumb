package com.xiaoshen.thumbbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoshen.thumbbackend.model.entity.Blog;
import com.xiaoshen.thumbbackend.service.BlogService;
import com.xiaoshen.thumbbackend.mapper.BlogMapper;
import org.springframework.stereotype.Service;

/**
* @author 小申同学
* @description 针对表【blog】的数据库操作Service实现
* @createDate 2025-08-14 15:00:21
*/
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog>
    implements BlogService{

}




