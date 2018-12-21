package com.miaoshaproject.error;

public enum EmBusinessError implements CommonError{
    //通用错误类型00001
    PARAMETER_VALIDATION_ERROR(10001,"参数不合法"),
    UNKNOWN_ERROR(10002,"未知错误哦"),
    //10001开头为用户信息相关错误定义
    USER_NOT_EXIST(20001,"用户不存在"),
    //这个错误不能提示太明确，如果明确提示手机号不存在可能会被异常攻击
    USER_LOGIN_FAIL(20002,"用户手机号或密码不正确"),
    ;
    private EmBusinessError(int errCode,String ErrMsg){

        this.errCode = errCode;
        this.errMsg = ErrMsg;
    }
    private int errCode;
    private String errMsg;

    @Override
    public int getErrCode(){
        return this.errCode;
    }

    @Override
    public String getErrMsg(){
        return this.errMsg;

    }

    @Override
    public CommonError setErrMsg(String errMsg){
        this.errMsg = errMsg;
        return this;
    }
}
