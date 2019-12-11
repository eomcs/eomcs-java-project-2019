package com.eomcs.lms.proxy;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import com.eomcs.lms.domain.Lesson;

public class LessonDaoProxy {

  ObjectInputStream in;
  ObjectOutputStream out;

  public LessonDaoProxy(ObjectInputStream in, ObjectOutputStream out) {
    this.in = in;
    this.out = out;
  }

  public int delete(int no) throws Exception {
    out.writeUTF("/lesson/delete");
    out.writeInt(no);
    out.flush();

    if (in.readUTF().equals("ok")) {
      return 1;
    } else {
      return 0;
    }    
  }

  public int update(Lesson lesson) throws Exception {
    out.writeUTF("/lesson/update");
    out.writeObject(lesson);
    out.flush();

    if (in.readUTF().equals("ok")) {
      return 1;
    } else {
      return 0;
    }
  }

  public int add(Lesson lesson) throws Exception {
    out.writeUTF("/lesson/add");
    out.writeObject(lesson);
    out.flush();

    if (in.readUTF().equals("ok")) {
      return 1;
    } else {
      return 0;
    }
  }

  public Lesson[] list() throws Exception {
    out.writeUTF("/lesson/list");
    out.flush();

    if (in.readUTF().equals("ok")) {
      return (Lesson[]) in.readObject();
    } else {
      return null;
    }
  }

  public Lesson detail(int no) throws Exception {
    out.writeUTF("/lesson/detail");
    out.writeInt(no);
    out.flush();

    if (in.readUTF().equals("ok")) {
      return (Lesson) in.readObject();
    } else {
      return null;
    }
  }

  public static void main(String[] args) throws Exception {
    try (Socket socket = new Socket("localhost", 8888);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());) {
      
      LessonDaoProxy lessonDao = new LessonDaoProxy(in, out);
      
      /*
      Lesson lesson = new Lesson();
      lesson.setNo(3);
      lesson.setTitle("자바 기초 과정");
      lesson.setContents("자바 기초 문법 강의입니다.");
      lesson.setStartDate(Date.valueOf("2019-1-1"));
      lesson.setEndDate(Date.valueOf("2019-2-15"));
      lesson.setTotalHours(80);
      lesson.setDayHours(8);
      
      lessonDao.add(lesson);
      */
      
      /*
      Lesson lesson = new Lesson();
      lesson.setNo(3);
      lesson.setTitle("자바 기초 과정x");
      lesson.setContents("자바 기초 문법 강의입니다.x");
      lesson.setStartDate(Date.valueOf("2019-1-3"));
      lesson.setEndDate(Date.valueOf("2019-2-18"));
      lesson.setTotalHours(81);
      lesson.setDayHours(9);
      
      lessonDao.update(lesson);
      */
      
      //System.out.println(lessonDao.detail(3));
      
      //lessonDao.delete(3);
      
      Lesson[] lessons = lessonDao.list();
      for (Lesson b : lessons) {
        System.out.println(b);
      }
    }
  }
}
