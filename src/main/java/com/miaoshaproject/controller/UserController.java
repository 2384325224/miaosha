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
import java.util.Random;

@Controller("user")
@RequestMapping("/user")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    //用户获取otp短信接口
    @RequestMapping("/getotp")
    @ResponseBody

    public CommonReturnType getOtp(@RequestParam(name = "telphone")String telphone){
        //需要按照一定的规则生成OTP验证码
        Random random = new Random();
        int randomInt = random.nextInt(99999);
        randomInt += 10000;//此时随机数取值[10000,109999]
        String otpCode = String.valueOf(randomInt);
        //将OTP验证码同对应用户的手机号关联,使用httpsession的方式绑定他的手机号与OTPCODE
        httpServletRequest.getSession().setAttribute(telphone,otpCode);
        //将OTP验证码通过短信通道发送给用户，省略
        System.out.println("telphone = "+telphone +"& otpCode = "+otpCode);

        return CommonReturnType.create(null);
    }

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
