package com.xiaoshen.thumbbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoshen.thumbbackend.constant.UserConstant;
import com.xiaoshen.thumbbackend.model.entity.User;
import com.xiaoshen.thumbbackend.service.UserService;
import com.xiaoshen.thumbbackend.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

/**
* @author 小申同学
* @description 针对表【user】的数据库操作Service实现
* @createDate 2025-08-14 15:02:16
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

	@Override
	public User getLoginUser(HttpServletRequest request) {
		return (User) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
	}


}




