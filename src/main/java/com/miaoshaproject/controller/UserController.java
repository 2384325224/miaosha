package com.miaoshaproject.controller;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.respnose.CommonReturnType;
import com.miaoshaproject.service.UserService;
import com.miaoshaproject.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.miaoshaproject.controller.viewobject.UserVO;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller("user")
@RequestMapping("/user")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

   @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name="id") Integer id) throws BusinessException {
        //调用service服务获取对应id的用户对象并返回给前端
        UserModel userModel = userService.getUserById(id);
        //userModel = null;
        //userModel.setEncrptPassword("123");
       //若获取的对应用户信息不存在
       if (userModel == null){
          // userModel.setEncrptPassword("123");
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
           //throw new Exception();
       }
        //将核心领域模型用户对象转化为可供UI使用的viewobject
       UserVO userVO = convertFromodel(userModel);
        return  CommonReturnType.create(userVO);
    }


    private  UserVO convertFromodel(UserModel userModel){
       if (userModel == null){
           return  null;
       }
       UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel,userVO);
       return userVO;
    }

}
