package com.longrise.community.service;

import com.longrise.community.dto.PaginationDTO;
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


  public PaginationDTO getQuestionList(Integer page, Integer size) {

    PaginationDTO paginationDTO = new PaginationDTO();
    Integer totalCount = questionMapper.getCount();
    paginationDTO.setPagination(totalCount,page,size);
    if(page > paginationDTO.getTotalPage()){
      page = paginationDTO.getTotalPage();
    }
    Integer offest = (page-1)*size;
    List<Question> questionLists = questionMapper.getQuestionList(offest,size);
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
    paginationDTO.setQuestionDTOList(questionDTOList);
    return paginationDTO;
  }

  public PaginationDTO getQuestionListByUserId(Long userid, Integer page, Integer size) {
    PaginationDTO paginationDTO = new PaginationDTO();
    //我的所有问题
    Integer totalCount = questionMapper.getCountByUserId(userid);
    paginationDTO.setPagination(totalCount,page,size);
    if(page > paginationDTO.getTotalPage()){
      page = paginationDTO.getTotalPage();
    }
    Integer offest = (page-1)*size;
    List<Question> questionLists = questionMapper.getQuestionListByUserId(userid,offest,size);
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
    paginationDTO.setQuestionDTOList(questionDTOList);
    return paginationDTO;

  }

  public QuestionDTO getQuestionInfo(Long id) {
    Question question = questionMapper.getQuestionInfo(id);
    QuestionDTO questionDTO = new QuestionDTO();
    BeanUtils.copyProperties(question,questionDTO);
    User user = userMapper.getUserById(question.getCreator());
    questionDTO.setUser(user);
    return questionDTO;
  }

  public void createOrUpdate(Question question) {
    Long id = question.getId();
    if(id == null){
      question.setGmtCreate(System.currentTimeMillis());
      questionMapper.create(question);
    }else{
      question.setGmtModified(System.currentTimeMillis());
      questionMapper.update(question);
    }
  }
}
