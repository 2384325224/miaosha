package com.miaoshaproject.controller;

import com.miaoshaproject.service.UserService;
import com.miaoshaproject.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.miaoshaproject.controller.viewobject.UserVO;

@Controller("user")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

   @RequestMapping("/get")
    @ResponseBody
    public UserVO getUser(@RequestParam(name="id") Integer id){
        //调用service服务获取对应id的用户对象并返回给前端
        UserModel userModel = userService.getUserById(id);
        //userModel = null;
        //userModel.setEncrptPassword("123");
        //将核心领域模型用户对象转化为可供UI使用的viewobject
        return  convertFromodel(userModel);
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
