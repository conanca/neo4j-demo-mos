package com.dolplay.demo.mos.module;

import javax.servlet.ServletContext;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.dolplay.demo.mos.service.UserService;

@IocBean
@InjectName
@At("/user")
public class UserModule {
	@Inject
	private UserService userService;

	@At
	@Ok("jsp:jsp.friends_list")
	public ServletContext listFriends(@Param("id") int id, ServletContext context) {
		context.setAttribute("user", userService.view(id));
		context.setAttribute("friendsList", userService.listFriends(id));
		return context;
	}

	@At
	@Ok("jsp:jsp.friends_chain")
	public ServletContext viewChain(@Param("startId") int startId, @Param("endId") int endId, ServletContext context) {
		context.setAttribute("user", userService.view(startId));
		context.setAttribute("targetUser", userService.view(endId));
		context.setAttribute("chain", userService.findShortestFriendPath(startId, endId));
		return context;
	}
}
