package com.longrise.community.service;

import com.longrise.community.dto.NotificationDTO;
import com.longrise.community.dto.PaginationDTO;
import com.longrise.community.enums.NotificationStatusEnum;
import com.longrise.community.enums.NotificationTypeEnum;
import com.longrise.community.exception.CustomizeErrorCode;
import com.longrise.community.exception.CustomizeException;
import com.longrise.community.mapper.NotificationMapper;
import com.longrise.community.mapper.UserMapper;
import com.longrise.community.model.Notification;
import com.longrise.community.model.NotificationExample;
import com.longrise.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Wangjiang
 * @create 2019-12-07 18:50
 */
@Service
public class NotificationService {

  @Autowired
  private NotificationMapper notificationMapper;
  @Autowired
  private UserMapper userMapper;
  /**
   * 获取指定用户的问题通知列表
   * @param userid
   * @param page
   * @param size
   * @return
   */
  public PaginationDTO getNotificationListByUserId(Long userid, Integer page, Integer size) {
    PaginationDTO paginationDTO = new PaginationDTO();
    //我的所有问题
    NotificationExample notificationExample = new NotificationExample();
    notificationExample.createCriteria().andReceiverEqualTo(userid);
    Integer totalCount = (int)notificationMapper.countByExample(notificationExample);
    paginationDTO.setPagination(totalCount,page,size);
    if(page > paginationDTO.getTotalPage()){
      page = paginationDTO.getTotalPage();
    }
    Integer offest = (page-1)*size;
    notificationExample.setOrderByClause("gmt_create desc");
    List<Notification> notificationLists = notificationMapper.selectByExampleWithRowbounds(notificationExample,new RowBounds(offest, size));
    if (notificationLists.size() == 0){
      return paginationDTO;
    }
    List<NotificationDTO> notificationDTOList = new ArrayList<>();
    for (Notification notification:notificationLists){
      NotificationDTO notificationDTO = new NotificationDTO();
      BeanUtils.copyProperties(notification,notificationDTO);
      notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
      notificationDTOList.add(notificationDTO);
    }

    paginationDTO.setData(notificationDTOList);
    return paginationDTO;

  }

  /**
   * 获取最新回复数量
   * @param id
   * @return
   */
  public Long unreadCount(Long id) {
    NotificationExample notificationExample = new NotificationExample();
    notificationExample.createCriteria().andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus())
        .andReceiverEqualTo(id);
    long l = notificationMapper.countByExample(notificationExample);
    return l;
  }

  /**
   * 修改回复为已读
   * @param id
   * @param user
   * @return
   */
  public NotificationDTO read(Long id, User user) {
    Notification notification = notificationMapper.selectByPrimaryKey(id);
    if (notification == null) {
      throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
    }
    if (!Objects.equals(notification.getReceiver(), user.getId())) {
      throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
    }
    //修改为已读
    notification.setStatus(NotificationStatusEnum.READ.getStatus());
    notificationMapper.updateByPrimaryKey(notification);
    NotificationDTO notificationDTO = new NotificationDTO();
    BeanUtils.copyProperties(notification,notificationDTO);
    notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
    return notificationDTO;
  }
}
