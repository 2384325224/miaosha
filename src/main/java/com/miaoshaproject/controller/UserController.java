package com.miaoshaproject.controller;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.respnose.CommonReturnType;
import com.miaoshaproject.service.UserService;
import com.miaoshaproject.service.model.UserModel;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.miaoshaproject.controller.viewobject.UserVO;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller("user")
@RequestMapping("/user")
@CrossOrigin(allowCredentials="true",allowedHeaders = "*")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    //用户注册接口
    @RequestMapping(value = "/register",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType register(@RequestParam(name="telphone")String telphone,

                                     @RequestParam(name="otpCode")String otpCode,
                                     @RequestParam(name = "name")String name,
                                     @RequestParam(name="gender")Byte gender,
                                     @RequestParam(name="age")Integer age,
                                     @RequestParam(name = "password") String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //验证手机号和对应的otpcode相符合
        String inSessionOtpCode = (String) this.httpServletRequest.getSession().getAttribute(telphone);
         if(!com.alibaba.druid.util.StringUtils.equals(otpCode,inSessionOtpCode)){
             throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"短信验证不符合");
         }
         //用户的注册流程
          UserModel userModel = new UserModel();
         userModel.setName(name);
         userModel.setGender(new Byte(String.valueOf(gender.intValue())));
         userModel.setAge(age);
         userModel.setTelphone(telphone);
         userModel.setRegisterMode("byphone");
         userModel.setEncrptPassword(this.EncodeByMd5(password));
         userService.register(userModel);
         return CommonReturnType.create(null);
    }

    public String EncodeByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder =new BASE64Encoder();

        //加密字符串
        String newstr = base64Encoder.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;

    }

    //用户获取otp短信接口
    @RequestMapping(value = "/getotp",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
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
