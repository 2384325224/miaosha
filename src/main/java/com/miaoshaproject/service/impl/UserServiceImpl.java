package com.miaoshaproject.service.impl;


import com.miaoshaproject.dao.UserDOMapper;
import com.miaoshaproject.dao.UserpasswordDOMapper;
import com.miaoshaproject.dataobject.UserDO;
import com.miaoshaproject.dataobject.UserpasswordDO;
import com.miaoshaproject.service.UserService;
import com.miaoshaproject.service.model.UserModel;
import org.springframework.beans.BeanUtils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDOMapper userDOMapper;
    @Autowired
    private UserpasswordDOMapper userpasswordDOMapper;
    @Override
    public  UserModel getUserById(Integer id){
      UserDO userDO=  userDOMapper.selectByPrimaryKey(id);
      if (userDO == null){
          return null;
      }
      UserpasswordDO userpasswordDO =userpasswordDOMapper.selectByUserId(userDO.getId());
      return convertFromDataObject(userDO,userpasswordDO);
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
