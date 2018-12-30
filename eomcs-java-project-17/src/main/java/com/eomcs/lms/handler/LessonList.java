package com.eomcs.lms.handler;

import java.util.Arrays;
import com.eomcs.lms.domain.Lesson;

public class LessonList {
  static final int DEFAULT_CAPACITY = 10;
  Lesson[] list;
  int size = 0;
  
  public LessonList() {
    list  = new Lesson[DEFAULT_CAPACITY];
  }
  
  public LessonList(int initialCapacity) {
    if (initialCapacity > DEFAULT_CAPACITY) 
      list = new Lesson[initialCapacity];
    else
      list = new Lesson[DEFAULT_CAPACITY];
  }
  
  public Lesson[] toArray() {
    return Arrays.copyOf(list, size); 
    /*
    Lesson[] list = new Lesson[size];
    
    for (int i = 0; i < size; i++) {
      list[i] = lessons[i];
    }
    return list;
    */
    
  }
  
  public void add(Lesson lesson) {
    if (size >= list.length) {
      int oldCapacity = list.length;
      int newCapacity = oldCapacity + (oldCapacity >> 1);
      list = Arrays.copyOf(list, newCapacity);
    }
    
    list[size++] = lesson;
  }
}
