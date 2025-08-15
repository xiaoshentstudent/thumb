package com.xiaoshen.thumbbackend.service;

import com.xiaoshen.thumbbackend.model.dto.thumb.DoThumbRequest;
import com.xiaoshen.thumbbackend.model.entity.Thumb;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author 小申同学
* @description 针对表【thumb】的数据库操作Service
* @createDate 2025-08-14 15:01:48
*/
public interface ThumbService extends IService<Thumb> {
	/**
	 * 点赞
	 * @param doThumbRequest 请求
	 * @param request 请求
	 * @return {@link Boolean }
	 */
	Boolean doThumb(DoThumbRequest doThumbRequest, HttpServletRequest request);


	/**
	 * 取消点赞
	 *
	 * @param doThumbRequest 请求
	 * @param request 请求
	 * @return {@link Boolean }
	 */
	Boolean undoThumb(DoThumbRequest doThumbRequest,HttpServletRequest request);

	/**
	 * 是否点赞
	 *
	 * @param blogId 博客id
	 * @param userId 用户id
	 * @return {@link Boolean }
	 */
	Boolean hasThumb(Long blogId, Long userId);


}
