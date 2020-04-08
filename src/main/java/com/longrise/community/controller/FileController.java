package com.longrise.community.controller;

import com.longrise.community.dto.FileDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Wangjiang
 * @create 2019-12-07 22:09
 */
@Controller
public class FileController {
  @ResponseBody
  @RequestMapping(value = "/file/upload")
  public FileDTO fileUpload(){
    FileDTO fileDTO = new FileDTO();
    fileDTO.setSuccess(1);
    fileDTO.setMessage(null);
    fileDTO.setUrl("/images/ucdn.png");
    return fileDTO;
  }
}
