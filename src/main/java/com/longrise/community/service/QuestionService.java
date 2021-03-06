package com.longrise.community.service;

import com.longrise.community.dto.PaginationDTO;
import com.longrise.community.dto.QuestionDTO;
import com.longrise.community.dto.QuestionQueryDTO;
import com.longrise.community.exception.CustomizeErrorCode;
import com.longrise.community.exception.CustomizeException;
import com.longrise.community.mapper.QuestionExtMapper;
import com.longrise.community.mapper.QuestionMapper;
import com.longrise.community.mapper.UserMapper;
import com.longrise.community.model.Question;
import com.longrise.community.model.QuestionExample;
import com.longrise.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {
  @Autowired
  private QuestionMapper questionMapper;
  @Autowired
  private UserMapper userMapper;
  @Autowired
  private QuestionExtMapper questionExtMapper;

  /**
   * 获取所有问题列表
   * @param page
   * @param size
   * @return
   */
  public PaginationDTO getQuestionList(String search,Integer page, Integer size) {
    if (StringUtils.isNoneBlank(search)){
      String[] tags = StringUtils.split(search, " ");
      search = Arrays
          .stream(tags)
          .filter(StringUtils::isNotBlank)
          .map(t -> t.replace("+", "").replace("*", "").replace("?", ""))
          .filter(StringUtils::isNotBlank)
          .collect(Collectors.joining("|"));
    }

    PaginationDTO paginationDTO = new PaginationDTO();
    QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
    questionQueryDTO.setSearch(search);
    Integer totalCount =questionExtMapper.countByQuery(questionQueryDTO);
    paginationDTO.setPagination(totalCount,page,size);
    if(page > paginationDTO.getTotalPage()){
      page = paginationDTO.getTotalPage();
    }
    Integer offest = 0;
    if (page != 0){
       offest = (page-1)*size;
    }

    questionQueryDTO.setPage(offest);
    questionQueryDTO.setSize(size);
    List<Question> questions = questionExtMapper.selectByQuery(questionQueryDTO);
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
    paginationDTO.setData(questionDTOList);
    return paginationDTO;
  }

  /**
   * 获取指定用户的问题列表
   * @param userid
   * @param page
   * @param size
   * @return
   */
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
    questionExample1.setOrderByClause("gmt_create desc");
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
    paginationDTO.setData(questionDTOList);
    return paginationDTO;

  }

  /**
   * 获取指定id的问题信息
   * @param id
   * @return
   */
  public QuestionDTO getQuestionInfo(Long id) {
    Question question = questionMapper.selectByPrimaryKey(id);
    if (question == null){
      throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
    }
    QuestionDTO questionDTO = new QuestionDTO();
    BeanUtils.copyProperties(question,questionDTO);
    User user = userMapper.selectByPrimaryKey(question.getCreator());
    questionDTO.setUser(user);
    return questionDTO;
  }

  /**
   * 创建或者修改问题
   * @param question
   */
  public void createOrUpdate(Question question) {
    Long id = question.getId();
    if(id == null){
      question.setGmtCreate(System.currentTimeMillis());
      question.setViewCount(0);
      question.setCommentCount(0);
      question.setLikeCount(0);
      questionMapper.insert(question);
    }else{
      QuestionExample questionExample = new QuestionExample();
      questionExample.createCriteria().andIdEqualTo(id);
      question.setGmtModified(System.currentTimeMillis());
      int updata =questionMapper.updateByExampleSelective(question,questionExample);
      if(updata == 0){
        throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
      }
    }
  }

  /**
   * 增加问题阅读数
   * @param id
   */
  public void incView(Long id) {
    Question question = new Question();
    question.setId(id);
    question.setViewCount(1);
    questionExtMapper.incView(question);
  }
  /**
   * 获取相关信息
   * @param question
   * @return
   */
  public List<QuestionDTO> selectRelated(QuestionDTO question) {
    if (StringUtils.isBlank(question.getTag())){
      return new ArrayList<>();
    }
    String tag = question.getTag().replace(",","|");
    Question dbQuestion = new Question();
    dbQuestion.setId(question.getId());
    dbQuestion.setTag(tag);
    List<QuestionDTO> questionDTOS = questionExtMapper.selectRelated(dbQuestion);
    return questionDTOS;
  }

}
