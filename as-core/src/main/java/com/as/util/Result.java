package com.as.util;

public class Result<T> {
	private boolean success;
	private T data;
	private String error;
	public Result( String error) {
		super();
		this.success = false;
		this.error = error;
	}
	public Result(boolean success, T data) {
		super();
		this.success = success;
		this.data = data;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
}
