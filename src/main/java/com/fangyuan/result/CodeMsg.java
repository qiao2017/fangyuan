package com.fangyuan.result;

import lombok.Data;

@Data
public class CodeMsg {
	
	private int code;
	private String msg;
	
	//通用的错误码
	public static CodeMsg SUCCESS = new CodeMsg(0, "success");
	public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
	public static CodeMsg BIND_ERROR = new CodeMsg(500101, "%s");
	public static CodeMsg PARAM_ERROR = new CodeMsg(500102, "参数格式错误");
	public static CodeMsg PARAM_MISSING_ERROR = new CodeMsg(500103, "参数%s缺失或类型错误");
	//登录模块 5002XX
	public static CodeMsg SESSION_NOTFOUND = new CodeMsg(500210, "Session不存在，请登录");
	public static CodeMsg SESSION_ERROR = new CodeMsg(500210, "Session已失效，请重新登陆");
	public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500211, "登录密码不能为空");
	public static CodeMsg MOBILE_EMPTY = new CodeMsg(500212, "手机号不能为空");
	public static CodeMsg MOBILE_ERROR = new CodeMsg(500213, "手机号格式错误");
	public static CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500214, "手机号不存在");
	public static CodeMsg PASSWORD_ERROR = new CodeMsg(500215, "用户名或密码错误");

	// 注册模块
	public static CodeMsg REGISTER_ERROR = new CodeMsg(500300, "用户名%s已被注册");

	// 房屋模块
	public static CodeMsg ROOM_ERROR = new CodeMsg(600100, "%s");

	
	private CodeMsg() {
	}
			
	private CodeMsg( int code,String msg ) {
		this.code = code;
		this.msg = msg;
	}
	
	public CodeMsg fillArgs(Object... args) {
		int code = this.code;
		String message = String.format(this.msg, args);
		return new CodeMsg(code, message);
	}

	
}
