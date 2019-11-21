package com.longrise.community.service;

import com.longrise.community.mapper.UserMapper;
import com.longrise.community.model.User;
import com.longrise.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
  @Autowired
  private UserMapper userMapper;
  public void createOrUpdate(User user) {
    UserExample userExample = new UserExample();
    userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
    List<User> dbUser = userMapper.selectByExample(userExample);
    if(dbUser.size() == 0){
      user.setGmtCreate(System.currentTimeMillis());
      userMapper.insert(user);
    }else{
      User user1 = new User();
      user1.setGmtModified(System.currentTimeMillis());
      user1.setToken(user.getToken());
      user1.setAvatarUrl(user.getAvatarUrl());
      user1.setName(user.getName());
      userExample = new UserExample();
      userExample.createCriteria().andIdEqualTo(dbUser.get(0).getId());
      userMapper.updateByExampleSelective(user1,userExample);
    }
  }
}
