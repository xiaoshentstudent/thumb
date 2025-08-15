package com.xiaoshen.thumbbackend.controller;

import com.xiaoshen.thumbbackend.common.BaseResponse;
import com.xiaoshen.thumbbackend.common.ResultUtils;
import com.xiaoshen.thumbbackend.model.vo.BlogVO;
import com.xiaoshen.thumbbackend.service.BlogService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.xiaoshen.thumbbackend.model.entity.Blog;

import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogController {


	@Resource
	private BlogService blogService;

	@GetMapping("/get")
	public BaseResponse<BlogVO> get(long blogId, HttpServletRequest request) {
		BlogVO blogVO = blogService.getBlogVOById(blogId, request);
		return ResultUtils.success(blogVO);
	}

	@GetMapping("/list")
	public BaseResponse<List<BlogVO>> list(HttpServletRequest request) {
		List<Blog> blogList = blogService.list();
		List<BlogVO> blogVOList = blogService.getBlogVOList(blogList, request);
		return ResultUtils.success(blogVOList);
	}

}
