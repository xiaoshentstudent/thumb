package com.xiaoshen.thumbbackend.controller;

import com.xiaoshen.thumbbackend.common.BaseResponse;
import com.xiaoshen.thumbbackend.common.ResultUtils;
import com.xiaoshen.thumbbackend.constant.UserConstant;
import com.xiaoshen.thumbbackend.model.entity.User;
import com.xiaoshen.thumbbackend.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController {

	// todo 后期自行改造
	@Resource
	private UserService userService;

	@GetMapping("/login")
	public BaseResponse<User> login(long userId, HttpServletRequest request){
		User user = userService.getById(userId);
		request.getSession().setAttribute(UserConstant.USER_LOGIN_STATE,user);
		return ResultUtils.success(user);
	}

	@GetMapping("/get/login")
	public BaseResponse<User> getLoginUser(HttpServletRequest request) {
		User loginUser = (User) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
		return ResultUtils.success(loginUser);
	}



}
