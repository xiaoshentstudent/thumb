package com.xiaoshen.thumbbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoshen.thumbbackend.constant.ThumbConstant;
import com.xiaoshen.thumbbackend.exception.BusinessException;
import com.xiaoshen.thumbbackend.exception.ErrorCode;
import com.xiaoshen.thumbbackend.model.dto.thumb.DoThumbRequest;
import com.xiaoshen.thumbbackend.model.entity.Blog;
import com.xiaoshen.thumbbackend.model.entity.Thumb;
import com.xiaoshen.thumbbackend.model.entity.User;
import com.xiaoshen.thumbbackend.service.BlogService;
import com.xiaoshen.thumbbackend.service.ThumbService;
import com.xiaoshen.thumbbackend.mapper.ThumbMapper;
import com.xiaoshen.thumbbackend.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

/**
* @author 小申同学
* @description 针对表【thumb】的数据库操作Service实现
* @createDate 2025-08-14 15:01:48
*/
@Service
@Slf4j
@RequiredArgsConstructor
public class ThumbServiceImpl extends ServiceImpl<ThumbMapper, Thumb>
    implements ThumbService{

	@Resource
	private UserService userService;

	@Resource
	private BlogService blogService;

	@Resource
	private TransactionTemplate transactionTemplate;

	@Override
	public Boolean doThumb(DoThumbRequest doThumbRequest, HttpServletRequest request) {
		// 检验参数
		if (doThumbRequest == null || doThumbRequest.getBlogId() == null) {
			throw new RuntimeException("参数错误");
		}
		User loginUser = userService.getLoginUser(request);
		// 加锁
		synchronized (loginUser.getId().toString().intern()) {
			// 编程式事务
			return transactionTemplate.execute(status -> {
				Long blogId = doThumbRequest.getBlogId();
				Boolean exists = this.hasThumb(blogId, loginUser.getId());
				if (exists) {
					throw new BusinessException(ErrorCode.OPERATION_ERROR,"用户已点赞");
				}
				boolean update = blogService.lambdaUpdate()
						.eq(Blog::getId,blogId)
						.setSql("thumbCount = thumbCount + 1")
						.update();
				Thumb thumb = new Thumb();
				thumb.setUserId(loginUser.getId());
				thumb.setBlogId(blogId);
				boolean success = update && this.save(thumb);

				// 点赞记录存入 Redis
				if (success) {
					redisTemplate.opsForHash().put(ThumbConstant.USER_THUMB_KEY_PREFIX + loginUser.getId().toString(), blogId.toString(), thumb.getId());
				}
				// 更新成功才执行
				return success;

			});
		}
	}

	@Override
	public Boolean undoThumb(DoThumbRequest doThumbRequest, HttpServletRequest request) {
		if (doThumbRequest == null || doThumbRequest.getBlogId() == null) {
			throw new RuntimeException("参数错误");
		}
		User loginUser = userService.getLoginUser(request);
		// 加锁
		synchronized (loginUser.getId().toString().intern()) {

			// 编程式事务
			return transactionTemplate.execute(status -> {
				Long blogId = doThumbRequest.getBlogId();
				Object thumbIdObj = redisTemplate.opsForHash().get(ThumbConstant.USER_THUMB_KEY_PREFIX + loginUser.getId().toString(), blogId.toString());
				if (thumbIdObj == null) {
					throw new RuntimeException("用户未点赞");
				}
				Long thumbId = Long.valueOf(thumbIdObj.toString());

				boolean update = blogService.lambdaUpdate()
						.eq(Blog::getId, blogId)
						.setSql("thumbCount = thumbCount - 1")
						.update();
				boolean success = update && this.removeById(thumbId);

				// 点赞记录从 Redis 删除
				if (success) {
					redisTemplate.opsForHash().delete(ThumbConstant.USER_THUMB_KEY_PREFIX + loginUser.getId(), blogId.toString());
				}
				return success;
			});
		}
	}

	private final RedisTemplate<String, Object> redisTemplate;

	@Override
	public Boolean hasThumb(Long blogId, Long userId) {
		return redisTemplate.opsForHash().hasKey(ThumbConstant.USER_THUMB_KEY_PREFIX + userId, blogId.toString());
	}


}




