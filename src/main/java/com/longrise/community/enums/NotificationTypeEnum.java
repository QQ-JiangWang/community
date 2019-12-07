package com.longrise.community.enums;

/**
 * @author Wangjiang
 * @create 2019-12-07 18:20
 */
public enum NotificationTypeEnum {
  REPLY_QUESTION(1, "回复了问题"),
  REPLY_COMMENT(2, "回复了评论");
  private int type;
  private String name;


  public int getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  NotificationTypeEnum(int status, String name) {
    this.type = status;
    this.name = name;
  }

  public static String nameOfType(int type) {
    for (NotificationTypeEnum notificationTypeEnum : NotificationTypeEnum.values()) {
      if (notificationTypeEnum.getType() == type) {
        return notificationTypeEnum.getName();
      }
    }
    return "";
  }
}
