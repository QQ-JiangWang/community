package com.longrise.community.controller;

import com.longrise.community.cache.TagCache;
import com.longrise.community.dto.QuestionDTO;
import com.longrise.community.model.Question;
import com.longrise.community.model.User;
import com.longrise.community.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PubilshController {

  @Autowired
  private QuestionService questionService;

  /**
   * 编辑问题
   * @param id
   * @param model
   * @return
   */
  @GetMapping("/publish/{id}")
  public String edit(@PathVariable(name="id") Long id,
                     Model model){
    QuestionDTO question = questionService.getQuestionInfo(id);
    model.addAttribute("description",question.getDescription());
    model.addAttribute("tag",question.getTag());
    model.addAttribute("title",question.getTitle());
    model.addAttribute("id",question.getId());
    model.addAttribute("tags", TagCache.get());
    return "publish";
  }
  @GetMapping("/publish")
  public String publish(Model model){
    model.addAttribute("tags", TagCache.get());
    return "publish";
  }

  /**
   * 提交问题
   * @param title
   * @param description
   * @param tag
   * @param id
   * @param request
   * @param model
   * @return
   */
  @PostMapping("/publish")
  public String doPublish(@RequestParam(value = "title", required = false) String title,
                       @RequestParam(value = "description", required = false) String description,
                       @RequestParam(value = "tag", required = false) String tag,
                       @RequestParam(value = "id", required = false) Long id,
                       HttpServletRequest request,
                       Model model){
    model.addAttribute("title", title);
    model.addAttribute("description", description);
    model.addAttribute("tag", tag);
    model.addAttribute("tags", TagCache.get());
    if (title == null || "".equals(title)) {
      model.addAttribute("error", "标题不能为空");
      return "publish";
    }
    if (description == null || "".equals(description)) {
      model.addAttribute("error", "问题补充不能为空");
      return "publish";
    }
    if (tag == null || "".equals(tag)) {
      model.addAttribute("error", "标签不能为空");
      return "publish";
    }
    String noTag = TagCache.filterInvalid(tag);
    if (!StringUtils.isBlank(noTag)) {
      model.addAttribute("error", "非法标签："+noTag);
      return "publish";
    }

    User user = (User) request.getSession().getAttribute("user");
    if (user == null) {
      model.addAttribute("error", "用户未登录");
      return "publish";
    }

    Question question = new Question();
    question.setTitle(title);
    question.setDescription(description);
    question.setTag(tag);
    question.setCreator(user.getId());
    question.setId(id);
    questionService.createOrUpdate(question);
    return "redirect:/";
  }
}
