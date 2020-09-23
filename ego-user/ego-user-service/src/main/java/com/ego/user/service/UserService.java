package com.ego.user.service;

import com.ego.user.mapper.UserMapper;
import com.ego.user.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import utils.CodecUtils;
import utils.NumberUtils;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author YangYao
 * @date 2020/9/20 15:56
 * @Description
 */
@Service
public class UserService {
    @Resource
    private UserMapper userMapper;
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private final String prefix="user:sms:code:";

    public Boolean check(String data, Integer type) {
        User user = new User();
        if (1==type){
            //查询数据库
            user.setUsername(data);
        }else if (2==type){
            user.setPhone(data);
        }else {
            return null;
        }
        List<User> users = userMapper.select(user);
        if (users==null||users.size()==0){
            return false;
        }
        return true;
    }

    /**
     * 发送验证码短信
     * @param phone
     * @return
     */
    public Boolean sendMessage(String phone) {
        boolean result=false;
        String code = NumberUtils.generateCode(6);
        //通知短信微服务发送短信
        Map<String,String> map=new Hashtable<>();
        map.put("phone",phone);
        map.put("code",code);
        amqpTemplate.convertAndSend("sms.verify.code",map);

        //将验证码保存到redis中，冒号层次结构
        stringRedisTemplate.opsForValue().set(prefix+phone,code,5, TimeUnit.MINUTES);
        result=true;
        return result;
    }

    public Boolean register(User user, String code) {
        Boolean result=false;
        //校验验证码
        if (StringUtils.isNotEmpty(code)){
            String key=prefix + user.getPhone();
            String oldCode = stringRedisTemplate.opsForValue().get(key);
            if (StringUtils.isNotEmpty(oldCode)&&oldCode.equals(code)){
                //保存到数据库
                user.setCreated(new Date());
                //密码加密
                user.setPassword(CodecUtils.passwordBcryptEncode(user.getUsername(),user.getPassword()));
                userMapper.insertSelective(user);
                result=true;

                //删除redis中验证码
                stringRedisTemplate.delete(key);
            }
        }
        return result;
    }

    public User queryUser(String username, String password) {
// 查询
        User record = new User();
        record.setUsername(username);
        User user = this.userMapper.selectOne(record);
// 校验用户名
        if (user == null) {
            return null;
        }
// 校验密码
        if (!CodecUtils.passwordConfirm(username+password,user.getPassword())) {
            return null;
        }
// 用户名密码都正确
        return user;
    }
}
