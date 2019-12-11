package com.eomcs.lms.dao;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import com.eomcs.lms.domain.Member;

public class MemberDao extends AbstractDao<Member> {
  
  public MemberDao(String filepath) throws Exception {
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
    Member member = (Member) in.readObject();
    
    int index = indexOf(member.getNo());
    
    if (index == -1) {
      out.writeUTF("fail");
    } else { 
      list.set(index, member);
      out.writeUTF("ok");
    }
    out.flush();
  }

  public void list(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    out.writeUTF("ok");
    Member[] arr = new Member[list.size()];
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
    list.add((Member)in.readObject());
    
    out.writeUTF("ok");
    out.flush();
  }
  
  private int indexOf(int no) {
    for (int i = 0; i < list.size(); i++) {
      Member m = list.get(i);
      if (m.getNo() == no)
        return i;
    }
    return -1;
  }
  
}
