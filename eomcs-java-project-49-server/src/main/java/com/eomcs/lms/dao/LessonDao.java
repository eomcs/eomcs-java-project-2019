package com.eomcs.lms.dao;

import java.util.List;
import java.util.Map;
import com.eomcs.lms.domain.Lesson;

public interface LessonDao {

  public int delete(int no) throws Exception;
  public int update(Lesson lesson) throws Exception;
  public int add(Lesson lesson) throws Exception;
  public Lesson detail(int no) throws Exception;
  public List<Lesson> list(Map<String,Object> params) throws Exception;
}
