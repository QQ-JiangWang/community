package com.longrise.community.enums;

/**
 * @author Wangjiang
 * @create 2019-12-07 18:20
 */
public enum NotificationStatusEnum {
  UNREAD(0), READ(1);
  ;
  private int status;
  NotificationStatusEnum(Integer status){
    this.status = status;
  }

  public int getStatus() {
    return status;
  }}
