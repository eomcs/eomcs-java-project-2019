package com.eomcs.lms.dao;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import com.eomcs.lms.domain.Member;

public class MemberDao {
  
  ArrayList<Member> members = new ArrayList<>();
  
  String filepath;
  
  public MemberDao(String filepath) throws Exception {
    this.filepath = filepath;
    loadData();
  }
  
  @SuppressWarnings("unchecked")
  public void loadData() throws Exception {
    ObjectInputStream in = null;
    
    try {
      
      File file = new File(filepath);
      if (!file.exists()) {
        file.createNewFile();
        return;
      }
      
      if (file.length() == 0) {
        return;
      }
      
      
      in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
      
      members = (ArrayList<Member>) in.readObject();
      
    } catch (Exception e) {
      throw e;
      
    } finally {
      try {in.close();} catch (Exception e) {}
    }
  }
  
  public void saveData() throws Exception {
    try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(
        new FileOutputStream(filepath)))) {
      
      out.writeObject(members);
      
    } catch (Exception e) {
      throw e;
    }
  }
  
  
  public void delete(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    int no = in.readInt();
    int index = indexOf(no);
    
    if (index == -1) {
      out.writeUTF("fail");
    } else { 
      members.remove(index);
      out.writeUTF("ok");
    }
    out.flush();
  }

  public void update(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    Member member = (Member) in.readObject();
    
    int index = indexOf(member.getNo());
    
    if (index == -1) {
      out.writeUTF("fail");
    } else { 
      members.set(index, member);
      out.writeUTF("ok");
    }
    out.flush();
  }

  public void list(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    out.writeUTF("ok");
    Member[] arr = new Member[members.size()];
    out.writeObject(members.toArray(arr));
    out.flush();
  }

  public void detail(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    int no = in.readInt();
    int index = indexOf(no);
    
    if (index == -1) {
      out.writeUTF("fail");
    } else { 
      out.writeUTF("ok");
      out.writeObject(members.get(index));
    }
    out.flush();
  }

  public void add(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    members.add((Member)in.readObject());
    
    out.writeUTF("ok");
    out.flush();
  }
  
  private int indexOf(int no) {
    for (int i = 0; i < members.size(); i++) {
      Member m = members.get(i);
      if (m.getNo() == no)
        return i;
    }
    return -1;
  }
  
}
