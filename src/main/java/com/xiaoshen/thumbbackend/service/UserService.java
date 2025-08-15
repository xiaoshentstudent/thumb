package com.xiaoshen.thumbbackend.service;

import com.xiaoshen.thumbbackend.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author 小申同学
* @description 针对表【user】的数据库操作Service
* @createDate 2025-08-14 15:02:16
*/
public interface UserService extends IService<User> {

	/**
	 * 获取当前登录用户
	 *
	 * @param request 请求
	 * @return 用户信息
	 */
	public User getLoginUser(HttpServletRequest request);

}
