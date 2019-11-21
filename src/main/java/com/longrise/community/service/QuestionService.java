package com.longrise.community.service;

import com.longrise.community.dto.PaginationDTO;
import com.longrise.community.dto.QuestionDTO;
import com.longrise.community.mapper.QuestionMapper;
import com.longrise.community.mapper.UserMapper;
import com.longrise.community.model.Question;
import com.longrise.community.model.QuestionExample;
import com.longrise.community.model.User;
import com.longrise.community.model.UserExample;
import org.apache.ibatis.session.RowBounds;
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
    Integer totalCount = (int)questionMapper.countByExample(new QuestionExample());
    paginationDTO.setPagination(totalCount,page,size);
    if(page > paginationDTO.getTotalPage()){
      page = paginationDTO.getTotalPage();
    }
    Integer offest = (page-1)*size;
    List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(new QuestionExample(), new RowBounds(offest, size));
    List<QuestionDTO> questionDTOList = new ArrayList<>();

    if(questions != null && questions.size()>0){
      for(Question question:questions){
        User user = userMapper.selectByPrimaryKey(question.getCreator());
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
    QuestionExample questionExample = new QuestionExample();
    questionExample.createCriteria().andCreatorEqualTo(userid);
    Integer totalCount = (int)questionMapper.countByExample(questionExample);
    paginationDTO.setPagination(totalCount,page,size);
    if(page > paginationDTO.getTotalPage()){
      page = paginationDTO.getTotalPage();
    }
    Integer offest = (page-1)*size;
    QuestionExample questionExample1 = new QuestionExample();
    questionExample1.createCriteria().andCreatorEqualTo(userid);
    List<Question> questionLists = questionMapper.selectByExampleWithBLOBsWithRowbounds(questionExample1, new RowBounds(offest, size));
    List<QuestionDTO> questionDTOList = new ArrayList<>();

    if(questionLists != null && questionLists.size()>0){
      for(Question question:questionLists){
        User user = userMapper.selectByPrimaryKey(question.getCreator());
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
    Question question = questionMapper.selectByPrimaryKey(id);
    QuestionDTO questionDTO = new QuestionDTO();
    BeanUtils.copyProperties(question,questionDTO);
    User user = userMapper.selectByPrimaryKey(question.getCreator());
    questionDTO.setUser(user);
    return questionDTO;
  }

  public void createOrUpdate(Question question) {
    Long id = question.getId();
    if(id == null){
      question.setGmtCreate(System.currentTimeMillis());
      questionMapper.insert(question);
    }else{
      QuestionExample questionExample = new QuestionExample();
      questionExample.createCriteria().andIdEqualTo(id);
      question.setGmtModified(System.currentTimeMillis());
      questionMapper.updateByExampleSelective(question,questionExample);
    }
  }
}
