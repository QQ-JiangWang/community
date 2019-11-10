package com.longrise.community.mapper;

import com.longrise.community.dto.QuestionDTO;
import com.longrise.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {
  @Insert("insert into question (title,description,tag,gmt_create,gmt_modified,creator) values(#{title},#{description},#{tag},#{gmtCreate},#{gmtModified},#{creator})")
  void create(Question question);
  @Select("select * from question limit #{page},#{size}")
  List<Question> getQuestionList(@Param("page") Integer page,
                                 @Param("size") Integer size);
  @Select("select count(1) from question")
  Integer getCount();
  @Select("select * from question where creator = #{userid} limit #{page},#{size}")
  List<Question> getQuestionListByUserId(@Param("userid")Integer userid,
                                         @Param("page")Integer offest,
                                         @Param("size")Integer size);
  @Select("select count(1) from question where creator = #{userid}")
  Integer getCountByUserId(@Param("userid") Integer userid);
  @Select("select * from question where id = #{id}")
  Question getQuestionInfo(@Param("id") Integer id);
}
