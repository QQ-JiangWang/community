package com.longrise.community.mapper;

import com.longrise.community.dto.QuestionDTO;
import com.longrise.community.dto.QuestionQueryDTO;
import com.longrise.community.model.Question;

import java.util.List;

public interface QuestionExtMapper {
    int incView(Question question);
    int incCommentCount(Question question);
    List<QuestionDTO> selectRelated(Question question);
    int countByQuery(QuestionQueryDTO questionQueryDTO);
    List<Question> selectByQuery(QuestionQueryDTO questionQueryDTO);
}
