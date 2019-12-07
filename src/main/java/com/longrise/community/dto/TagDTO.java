package com.longrise.community.dto;

import lombok.Data;

import java.util.List;

/**
 * @author Wangjiang
 * @create 2019-12-07 14:41
 */
@Data
public class TagDTO {
  private String categoryName;
  private List<String> tags;
}
