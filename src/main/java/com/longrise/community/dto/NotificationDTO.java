package com.longrise.community.dto;

import lombok.Data;

/**
 * @author Wangjiang
 * @create 2019-12-07 18:58
 */
@Data
public class NotificationDTO {
  private Long id;
  private Long notifier;
  private String notifierName;
  private Long outerId;
  private String outerTitle;
  private Integer type;
  private String typeName;
  private Long gmtCreate;
  private Integer status;
}
