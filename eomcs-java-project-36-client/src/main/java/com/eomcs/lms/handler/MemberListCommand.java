package com.eomcs.lms.handler;
import java.util.List;
import java.util.Scanner;
import com.eomcs.lms.dao.MemberDao;
import com.eomcs.lms.domain.Member;

public class MemberListCommand implements Command {
  
  Scanner keyboard;
  MemberDao memberDao;

  public MemberListCommand(Scanner keyboard, MemberDao memberDao) {
    this.keyboard = keyboard;
    this.memberDao = memberDao;
  }
  
  @Override
  public void execute() {
    try {
      List<Member> members = memberDao.list();
      if (members == null) { 
        System.out.println("서버에서 데이터를 가져오는데 오류 발생!");
        return;
      }
      
      for (Member member : members) {
        System.out.printf("%3d, %-4s, %-20s, %-15s, %s\n", 
            member.getNo(), member.getName(), 
            member.getEmail(), member.getTel(), member.getRegisteredDate());
      }
      
    } catch (Exception e) {
      System.out.printf("%s : %s\n", e.toString(), e.getMessage());
    }
  }
}
