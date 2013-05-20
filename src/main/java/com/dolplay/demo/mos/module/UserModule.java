package com.dolplay.demo.mos.module;

import java.util.List;

import javax.servlet.ServletContext;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.dolplay.demo.mos.domain.User;
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
		List<User> chain = userService.findShortestFriendPath(startId, endId);
		context.setAttribute("chain", chain);
		context.setAttribute("length", chain.size() - 2);
		return context;
	}
}
