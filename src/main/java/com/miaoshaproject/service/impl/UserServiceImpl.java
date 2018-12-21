package com.miaoshaproject.service.impl;


import com.miaoshaproject.dao.UserDOMapper;
import com.miaoshaproject.dao.UserpasswordDOMapper;
import com.miaoshaproject.dataobject.UserDO;
import com.miaoshaproject.dataobject.UserpasswordDO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.service.UserService;
import com.miaoshaproject.service.model.UserModel;
import com.miaoshaproject.validator.ValidationResult;
import com.miaoshaproject.validator.ValidatorImpl;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.BeanUtils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service

public class UserServiceImpl implements UserService {

    @Autowired
    private UserDOMapper userDOMapper;
    @Autowired
    private UserpasswordDOMapper userpasswordDOMapper;

    @Autowired
   private ValidatorImpl validatorImpl;

    @Override
    public  UserModel getUserById(Integer id){
      UserDO userDO=  userDOMapper.selectByPrimaryKey(id);
      if (userDO == null){
          return null;
      }
      UserpasswordDO userpasswordDO =userpasswordDOMapper.selectByUserId(userDO.getId());
      return convertFromDataObject(userDO,userpasswordDO);
    }

    @Override
    @Transactional
    public void register(UserModel userModel) throws BusinessException {
        if (userModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        ValidationResult result = validatorImpl.validate(userModel);
        if (result.isHasErrors()){
            throw  new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
       /*if (StringUtils.isEmpty(userModel.getName())
             || userModel.getGender() == null
              || userModel.getAge() == null
              || StringUtils.isEmpty(userModel.getTelphone())){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }*/

        //实现model-》dataobject方法
        UserDO userDO = convertFromModel(userModel);


        try {
            userDOMapper.insertSelective(userDO);

        } catch (DuplicateKeyException ex) {
           throw  new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"手机号已重复注册");
        }

        UserpasswordDO userpasswordDO = convertPasswordFromMode(userModel,userDO);
        userpasswordDOMapper.insertSelective(userpasswordDO);
        return;
    }

    @Override
    public UserModel validataLogin(String telphone, String encrptPassword) throws BusinessException {
        //通过用户的手机获取用户信息
         UserDO userDO = userDOMapper.selectByTelphone(telphone);
         if (userDO == null){
             throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
         }
         UserpasswordDO userpasswordDO = userpasswordDOMapper.selectByUserId(userDO.getId());
         UserModel userModel = convertFromDataObject(userDO,userpasswordDO);

        //比对用户信息内加密的密码是否和传输进来的密码相比配
        if (!StringUtils.equals(encrptPassword,userModel.getEncrptPassword())){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        return userModel;
    }

    private UserpasswordDO convertPasswordFromMode(UserModel userModel,UserDO userDO){
        if (userModel == null){
            return null;
        }
        UserpasswordDO userpasswordDO = new UserpasswordDO();
        userpasswordDO.setEncrptPassword(userModel.getEncrptPassword());
        userpasswordDO.setUserid(userDO.getId());
        return userpasswordDO;
    }
    private UserDO convertFromModel(UserModel userModel){
        if (userModel == null){
            return null;
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel,userDO);
        return  userDO;
    }

    private UserModel convertFromDataObject(UserDO userDO, UserpasswordDO userpasswordDO){
        if(userDO == null){
            return null;
        }
        UserModel userModel =new UserModel();

        BeanUtils.copyProperties(userDO,userModel);
        if (userpasswordDO !=null){
            userModel.setEncrptPassword(userpasswordDO.getEncrptPassword());
        }
        return userModel;
    }
}
