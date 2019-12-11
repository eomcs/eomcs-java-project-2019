package com.eomcs.lms.handler;
import java.util.Scanner;
import com.eomcs.lms.proxy.MemberDaoProxy;

public class MemberDeleteCommand implements Command {
  
  Scanner keyboard;
  MemberDaoProxy memberDao;

  public MemberDeleteCommand(Scanner keyboard, MemberDaoProxy memberDao) {
    this.keyboard = keyboard;
    this.memberDao = memberDao;
  }
  
  @Override
  public void execute() {
    System.out.print("번호? ");
    int no = Integer.parseInt(keyboard.nextLine());

    try {
      if (memberDao.delete(no) > 0) 
        System.out.println("회원을 삭제했습니다.");
      else 
        System.out.println("해당 회원을 찾을 수 없습니다.");
      
    } catch (Exception e) {
      System.out.printf("%s : %s\n", e.toString(), e.getMessage());
    }
  }
}
