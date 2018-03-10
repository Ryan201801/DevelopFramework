package com.ryan.application;

import com.ryan.dao.UserDao;
import com.ryan.exception.InternalServerException;
import com.ryan.model.system.Components;
import com.ryan.model.system.PhoenixErrorCode;
import com.ryan.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserProcess {

    @Autowired(required=true)
    private UserDao userDao;
    /**
     * @Description: 获取所有用户。
     * @author Ryan
     * @date 2018/1/29 15:38
     */
    public List<User> getAll() {
        return (List) userDao.findAll();
    }

    /**
     * @Description: 删除单一用户
     * @author Ryan
     * @date 2018/1/29 16:21
     */
    public boolean deleteUser(int userid){
        boolean flag = false;
        try {
            userDao.delete(userid);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  flag;
    }

    /**
     * @Description: 更新用户
     * @author Ryan
     * @date 2018/1/29 15:50
     */
    public User updateUser(User user){
        User userOK;
        try {
            userOK = userDao.save(user);
        }catch (Exception e){
            throw new InternalServerException(PhoenixErrorCode.DATABASE_OPT_ERROR,Components.PHOENIX);
        }
        return userOK;
    }


}
