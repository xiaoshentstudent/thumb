package com.xiaoshen.thumbbackend.service;

import com.xiaoshen.thumbbackend.model.entity.Blog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoshen.thumbbackend.model.vo.BlogVO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
* @author 小申同学
* @description 针对表【blog】的数据库操作Service
* @createDate 2025-08-14 15:00:21
*/
public interface BlogService extends IService<Blog> {

	/**
	 * 根据 id 获取博客内容
	 *
	 * @param blogId 博客 id
	 * @param request http 请求
	 * @return 博客视图
	 */
	BlogVO getBlogVOById(long blogId, HttpServletRequest request);


	/**
     * 根据 博客列表 获取 博客视图列表
     *
     * @param blogList 博客列表
     * @param request http 请求
     * @return 博客视图列表
     */
	List<BlogVO> getBlogVOList(List<Blog> blogList, HttpServletRequest request);

}
