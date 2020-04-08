package com.longrise.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO<T> {
  private List<T> data;
  //上一页
  private boolean showProvious;
  //首页
  private boolean showFirstPage;
  //下一页
  private boolean showNext;
  //尾页
  private boolean showEndPage;
  private Integer page;
  private List<Integer> pages = new ArrayList<>();
  private Integer totalPage;

  public void setPagination(Integer totalCount, Integer page, Integer size) {
    this.page = page;

    if( totalCount % size == 0){
      totalPage = totalCount / size;
    }else {
      totalPage = totalCount / size +1;
    }
    if(totalPage <= page){
      this.page = totalPage;
    }
    if(page<=0){
      this.page = 1;
    }
    if(this.page == 1){
      showProvious = false;
    }else {
      showProvious = true;
    }
    if(this.page == totalPage){
      showNext = false;
    }else {
      showNext = true;
    }
    pages.add(this.page);
    for(int i=1;i<4;i++){
      if(this.page-i >0){
        pages.add(0,this.page-i);
      }
      if(this.page +i <= totalPage){
        pages.add(this.page+i);
      }
    }
    this.showFirstPage = pages.contains(1) ? false:true;
    this.showEndPage = pages.contains(totalPage) ? false:true;
  }
}
