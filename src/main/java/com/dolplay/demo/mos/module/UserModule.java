package com.dolplay.demo.mos.module;

import java.util.List;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.dolplay.demo.mos.domain.Friend;
import com.dolplay.demo.mos.service.UserService;

@IocBean
@InjectName
@At("/user")
public class UserModule {
	@Inject
	private UserService userService;

	@At
	@Ok("jsp:jsp.friends_list")
	public List<Friend> listFriends(@Param("id") int id) {
		return userService.listFriends(id);
	}
}
