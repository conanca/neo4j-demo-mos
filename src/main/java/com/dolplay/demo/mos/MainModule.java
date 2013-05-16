package com.dolplay.demo.mos;

import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.SetupBy;
import org.nutz.mvc.ioc.provider.AnnotationIocProvider;

@Modules(packages = { "com.dolplay.demo.mos" }, scanPackage = true)
@IocBy(type = AnnotationIocProvider.class, args = { "com.dolplay.demo.mos" })
@SetupBy(MvcSetup.class)
@Ok("json")
@Fail("json")
public class MainModule {

}
