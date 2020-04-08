package com.longrise.community.dto;

import lombok.Data;

/**
 * @author Wangjiang
 * @create 2019-12-08 16:57
 */
@Data
public class QuestionQueryDTO {
  private String search;
  private Integer page;
  private Integer size;
}
