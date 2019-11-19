package com.longrise.community.service;

import com.longrise.community.mapper.UserMapper;
import com.longrise.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  @Autowired
  private UserMapper userMapper;
  public void createOrUpdate(User user) {

    User dbUser = userMapper.getUserByAccountId(user.getAccountId());
    if(dbUser == null){
      user.setGmtCreate(System.currentTimeMillis());
      userMapper.insert(user);
    }else{
      dbUser.setGmtModified(System.currentTimeMillis());
      dbUser.setToken(user.getToken());
      dbUser.setAvatarUrl(user.getAvatarUrl());
      dbUser.setName(user.getName());
      userMapper.update(dbUser);
    }
  }
}
