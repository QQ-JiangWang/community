package com.longrise.community.dto;

import com.longrise.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
  private User user;
  /**
   *
   * This field was generated by MyBatis Generator.
   * This field corresponds to the database column QUESTION.ID
   *
   * @mbg.generated Thu Sep 05 06:46:42 CST 2019
   */
  private Long id;

  /**
   *
   * This field was generated by MyBatis Generator.
   * This field corresponds to the database column QUESTION.TITLE
   *
   * @mbg.generated Thu Sep 05 06:46:42 CST 2019
   */
  private String title;

  /**
   *
   * This field was generated by MyBatis Generator.
   * This field corresponds to the database column QUESTION.GMT_CREATE
   *
   * @mbg.generated Thu Sep 05 06:46:42 CST 2019
   */
  private Long gmtCreate;

  /**
   *
   * This field was generated by MyBatis Generator.
   * This field corresponds to the database column QUESTION.GMT_MODIFIED
   *
   * @mbg.generated Thu Sep 05 06:46:42 CST 2019
   */
  private Long gmtModified;

  /**
   *
   * This field was generated by MyBatis Generator.
   * This field corresponds to the database column QUESTION.CREATOR
   *
   * @mbg.generated Thu Sep 05 06:46:42 CST 2019
   */
  private Long creator;

  /**
   *
   * This field was generated by MyBatis Generator.
   * This field corresponds to the database column QUESTION.COMMENT_COUNT
   *
   * @mbg.generated Thu Sep 05 06:46:42 CST 2019
   */
  private Integer commentCount;

  /**
   *
   * This field was generated by MyBatis Generator.
   * This field corresponds to the database column QUESTION.VIEW_COUNT
   *
   * @mbg.generated Thu Sep 05 06:46:42 CST 2019
   */
  private Integer viewCount;

  /**
   *
   * This field was generated by MyBatis Generator.
   * This field corresponds to the database column QUESTION.LIKE_COUNT
   *
   * @mbg.generated Thu Sep 05 06:46:42 CST 2019
   */
  private Integer likeCount;

  /**
   *
   * This field was generated by MyBatis Generator.
   * This field corresponds to the database column QUESTION.TAG
   *
   * @mbg.generated Thu Sep 05 06:46:42 CST 2019
   */
  private String tag;

  /**
   *
   * This field was generated by MyBatis Generator.
   * This field corresponds to the database column QUESTION.DESCRIPTION
   *
   * @mbg.generated Thu Sep 05 06:46:42 CST 2019
   */
  private String description;
}
