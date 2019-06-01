package com.fangyuan.result;

import lombok.Data;

@Data
public class Result<T> {
	
	private int code;
	private String msg;
	private T data;
	
	/**
	 *  成功时候的调用，返回数据
	 * */
	public static  <T> Result<T> success(T data){
		return new Result<T>(data);
	}
	/**
	 *  成功时候的调用， 不返回数据
	 * */
	public static  <T> Result<T> success(){
		return new Result<T>();
	}

	/**
	 *  失败时候的调用
	 * */
	public static  <T> Result<T> failure(CodeMsg codeMsg){
		return new Result<T>(codeMsg);
	}
	
	private Result(T data) {
		this.data = data;
	}
	
	private Result(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	private Result(CodeMsg codeMsg) {
		if(codeMsg != null) {
			this.code = codeMsg.getCode();
			this.msg = codeMsg.getMsg();
		}
	}

	private Result() {
		this.code = 0;
	}
}
