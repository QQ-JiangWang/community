package com.longrise.community.cache;

import com.longrise.community.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Wangjiang
 * @create 2019-12-07 14:43
 */
public class TagCache {

  public static List<TagDTO> get(){
    List<TagDTO> tagDTOS = new ArrayList<>();
    TagDTO program = new TagDTO();
    program.setCategoryName("开发语言");
    program.setTags(Arrays.asList("javascript", "php", "css", "html", "html5", "java", "node.js", "python", "c++", "c", "golang", "objective-c", "typescript", "shell", "swift", "c#", "sass", "ruby", "bash", "less", "asp.net", "lua", "scala", "coffeescript", "actionscript", "rust", "erlang", "perl"));
    tagDTOS.add(program);

    TagDTO framework = new TagDTO();
    framework.setCategoryName("平台框架");
    framework.setTags(Arrays.asList("laravel", "spring", "express", "django", "flask", "yii", "ruby-on-rails", "tornado", "koa", "struts"));
    tagDTOS.add(framework);


    TagDTO server = new TagDTO();
    server.setCategoryName("服务器");
    server.setTags(Arrays.asList("linux", "nginx", "docker", "apache", "ubuntu", "centos", "缓存 tomcat", "负载均衡", "unix", "hadoop", "windows-server"));
    tagDTOS.add(server);

    TagDTO db = new TagDTO();
    db.setCategoryName("数据库");
    db.setTags(Arrays.asList("mysql", "redis", "mongodb", "sql", "oracle", "nosql memcached", "sqlserver", "postgresql", "sqlite"));
    tagDTOS.add(db);

    TagDTO tool = new TagDTO();
    tool.setCategoryName("开发工具");
    tool.setTags(Arrays.asList("git", "github", "visual-studio-code", "vim", "sublime-text", "xcode intellij-idea", "eclipse", "maven", "ide", "svn", "visual-studio", "atom emacs", "textmate", "hg"));
    tagDTOS.add(tool);
    return tagDTOS;
  }

  /**
   * 检验标签是否有不合法的，有则获取出来
   * @param tags
   * @return
   */
  public static String filterInvalid(String tags) {
    String[] split = tags.split(",");
    List<TagDTO> tagDTOS = TagCache.get();
    //将所有标签转换为一个集合
    List<String> tagList = tagDTOS.stream().flatMap(tagDTO -> tagDTO.getTags().stream()).collect(Collectors.toList());
    String collect = Arrays.stream(split).filter(tag -> (StringUtils.isBlank(tag) || !tagList.contains(tag))).collect(Collectors.joining(","));
    return collect;
  }
}
