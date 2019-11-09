package com.longrise.community.service;

import com.longrise.community.dto.QuestionDTO;
import com.longrise.community.mapper.QuestionMapper;
import com.longrise.community.mapper.UserMapper;
import com.longrise.community.model.Question;
import com.longrise.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
  @Autowired
  private QuestionMapper questionMapper;
  @Autowired
  private UserMapper userMapper;


  public List<QuestionDTO> getQuestionList() {
    List<Question> questionLists = questionMapper.getQuestionList();
    List<QuestionDTO> questionDTOList = new ArrayList<>();
    if(questionLists != null && questionLists.size()>0){
      for(Question question:questionLists){
        User user = userMapper.getUserById(question.getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(user);
        questionDTOList.add(questionDTO);
      }
    }
    return questionDTOList;
  }
}
