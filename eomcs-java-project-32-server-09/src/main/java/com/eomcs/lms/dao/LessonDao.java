package com.eomcs.lms.dao;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import com.eomcs.lms.domain.Lesson;

public class LessonDao {
  static ArrayList<Lesson> lessons = new ArrayList<>();
  
  public static void delete(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    int no = in.readInt();
    int index = indexOf(no);
    
    if (index == -1) {
      out.writeUTF("fail");
    } else { 
      lessons.remove(index);
      out.writeUTF("ok");
    }
    out.flush();
  }

  public static void update(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    Lesson lesson = (Lesson) in.readObject();
    
    int index = indexOf(lesson.getNo());
    
    if (index == -1) {
      out.writeUTF("fail");
    } else { 
      lessons.set(index, lesson);
      out.writeUTF("ok");
    }
    out.flush();
  }

  public static void list(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    out.writeUTF("ok");
    Lesson[] arr = new Lesson[lessons.size()];
    out.writeObject(lessons.toArray(arr));
    out.flush();
  }

  public static void detail(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    int no = in.readInt();
    int index = indexOf(no);
    
    if (index == -1) {
      out.writeUTF("fail");
    } else { 
      out.writeUTF("ok");
      out.writeObject(lessons.get(index));
    }
    out.flush();
  }

  public static void add(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    lessons.add((Lesson)in.readObject());
    
    out.writeUTF("ok");
    out.flush();
  }
  
  private static int indexOf(int no) {
    for (int i = 0; i < lessons.size(); i++) {
      Lesson l = lessons.get(i);
      if (l.getNo() == no)
        return i;
    }
    return -1;
  } 
}
