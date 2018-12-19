package com.miaoshaproject.error;

public enum EmBusinessError implements CommonError{
    //通用错误类型00001
    PARAMETER_VALIDATION_ERROR(000001,"参数不合法"),
    //10001开头为用户信息相关错误定义
    USER_NOT_EXIST(10001,"用户不存在"),
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
