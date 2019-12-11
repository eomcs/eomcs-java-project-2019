package com.eomcs.lms;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import com.eomcs.lms.domain.Board;

public class BoardDao {
  static ArrayList<Board> boards = new ArrayList<>();
  
  public  static void delete(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    int no = in.readInt();
    int index = indexOf(no);
    
    if (index == -1) {
      out.writeUTF("fail");
    } else { 
      boards.remove(index);
      out.writeUTF("ok");
    }
    out.flush();
  }

  public  static void update(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    Board board = (Board) in.readObject();
    
    int index = indexOf(board.getNo());
    
    if (index == -1) {
      out.writeUTF("fail");
    } else { 
      boards.set(index, board);
      out.writeUTF("ok");
    }
    out.flush();
  }

  public  static void list(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    out.writeUTF("ok");
    Board[] arr = new Board[boards.size()];
    out.writeObject(boards.toArray(arr));
    out.flush();
  }

  public  static void detail(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    int no = in.readInt();
    int index = indexOf(no);
    
    if (index == -1) {
      out.writeUTF("fail");
    } else { 
      out.writeUTF("ok");
      out.writeObject(boards.get(index));
    }
    out.flush();
  }

  public  static void add(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    boards.add((Board)in.readObject());
    
    out.writeUTF("ok");
    out.flush();
  }
  
  private static int indexOf(int no) {
    for (int i = 0; i < boards.size(); i++) {
      Board l = boards.get(i);
      if (l.getNo() == no)
        return i;
    }
    return -1;
  }  
}
