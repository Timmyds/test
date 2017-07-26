package com.fxb.world.util.bean;

public class JsonResult {
    private int code = 0;
    private String msg = "";
    private Object data = new Object();
    
    public JsonResult(){
    	
    }
    
    public JsonResult(int code,String msg,Object data){
    	this.code = code;
    	this.msg  = msg;
    	this.data = data;
    }
    
    public JsonResult(String result,Object data){
    	this.code = Integer.valueOf(result.split(":")[0]);
    	this.msg  = result.split(":")[1];
    	this.data = data;
    }
    
    
	public int getCode() {
		return code;
	}
	public String getMsg() {
		return msg;
	}
	public Object getData() {
		return data;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	
}