package com.eomcs.lms.dao;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import com.eomcs.lms.domain.Lesson;

public class LessonDao extends AbstractDao<Lesson> {
  
  public LessonDao(String filepath) throws Exception {
    super(filepath);
  }
  
  
  public void delete(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    int no = in.readInt();
    int index = indexOf(no);
    
    if (index == -1) {
      out.writeUTF("fail");
    } else { 
      list.remove(index);
      out.writeUTF("ok");
    }
    out.flush();
  }

  public void update(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    Lesson lesson = (Lesson) in.readObject();
    
    int index = indexOf(lesson.getNo());
    
    if (index == -1) {
      out.writeUTF("fail");
    } else { 
      list.set(index, lesson);
      out.writeUTF("ok");
    }
    out.flush();
  }

  public void list(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    out.writeUTF("ok");
    Lesson[] arr = new Lesson[list.size()];
    out.writeObject(list.toArray(arr));
    out.flush();
  }

  public void detail(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    int no = in.readInt();
    int index = indexOf(no);
    
    if (index == -1) {
      out.writeUTF("fail");
    } else { 
      out.writeUTF("ok");
      out.writeObject(list.get(index));
    }
    out.flush();
  }

  public void add(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    list.add((Lesson)in.readObject());
    
    out.writeUTF("ok");
    out.flush();
  }
  
  private int indexOf(int no) {
    for (int i = 0; i < list.size(); i++) {
      Lesson l = list.get(i);
      if (l.getNo() == no)
        return i;
    }
    return -1;
  } 
}
