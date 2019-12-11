package com.eomcs.lms.dao;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import com.eomcs.lms.domain.Member;

public class MemberDao {
  static ArrayList<Member> members = new ArrayList<>();
  
  public static void delete(ObjectInputStream in, ObjectOutputStream out) throws Exception {
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

  public static void update(ObjectInputStream in, ObjectOutputStream out) throws Exception {
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

  public static void list(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    out.writeUTF("ok");
    Member[] arr = new Member[members.size()];
    out.writeObject(members.toArray(arr));
    out.flush();
  }

  public static void detail(ObjectInputStream in, ObjectOutputStream out) throws Exception {
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

  public static void add(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    members.add((Member)in.readObject());
    
    out.writeUTF("ok");
    out.flush();
  }
  
  private static int indexOf(int no) {
    for (int i = 0; i < members.size(); i++) {
      Member m = members.get(i);
      if (m.getNo() == no)
        return i;
    }
    return -1;
  }
  
}
