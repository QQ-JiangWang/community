package com.longrise.community.mapper;

import com.longrise.community.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;


@Mapper
public interface UserMapper {
  @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,avatar_url) values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
  void insert(User user);
  @Select("select * from user where token = #{token}")
  User findByToken(@Param("token") String token);
  @Select("select * from user where id = #{id}")
  User getUserById(@Param("id") Long creator);
  @Select("select * from user where account_id=#{accountId}")
  User getUserByAccountId(@Param("accountId") String accountId);
  @Update("update user set token=#{token},name=#{name},gmt_modified=#{gmtModified},avatar_url=#{avatarUrl} where account_id=#{accountId}")
  void update(User dbUser);
}
