package com.miaoshaproject.service;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.service.model.UserModel;

public interface UserService {
    //通过用户Id获取用户对象的方法
      UserModel getUserById(Integer id);
      void register(UserModel userModel) throws BusinessException;

    /**
     *
     * @param telphone:用户注册手机
     * @param password:用户加密后的密码
     * @throws BusinessException
     */
    UserModel validataLogin(String telphone,String encrptPassword) throws BusinessException;
}
