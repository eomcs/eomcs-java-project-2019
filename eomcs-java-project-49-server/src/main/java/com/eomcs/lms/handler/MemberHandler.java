package com.eomcs.lms.handler;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;
import com.eomcs.lms.dao.MemberDao;
import com.eomcs.lms.domain.Member;
import com.eomcs.stereotype.CommandMapping;
import com.eomcs.stereotype.Component;

@Component
public class MemberHandler {

  MemberDao memberDao;

  public MemberHandler(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  @CommandMapping("/member/add")
  public void add(BufferedReader in, PrintWriter out) {
    try {
      Member member = new Member();

      out.print("이름?\n!{}!\n"); out.flush();
      member.setName(in.readLine());

      out.print("이메일?\n!{}!\n"); out.flush();
      member.setEmail(in.readLine());

      out.print("암호?\n!{}!\n"); out.flush();
      member.setPassword(in.readLine());

      out.print("사진?\n!{}!\n"); out.flush();
      member.setPhoto(in.readLine());

      out.print("전화?\n!{}!\n"); out.flush();
      member.setTel(in.readLine());

      memberDao.add(member);
      out.println("회원을 저장했습니다.");

    } catch (Exception e) {
      out.printf("%s : %s\n", e.toString(), e.getMessage());
    }
  }
  
  @CommandMapping("/member/delete")
  public void delete(BufferedReader in, PrintWriter out) {
    try {
      out.print("번호?\n!{}!\n"); out.flush();
      int no = Integer.parseInt(in.readLine());

      if (memberDao.delete(no) > 0) 
        out.println("회원을 삭제했습니다.");
      else 
        out.println("해당 회원을 찾을 수 없습니다.");

    } catch (Exception e) {
      out.printf("%s : %s\n", e.toString(), e.getMessage());
    }
  }
  
  @CommandMapping("/member/detail")
  public void detail(BufferedReader in, PrintWriter out) {
    try {
      out.print("번호?\n!{}!\n"); out.flush();
      int no = Integer.parseInt(in.readLine());

      Member member = memberDao.detail(no);
      if (member == null) { 
        out.println("해당 회원을 찾을 수 없습니다.");
        return;
      }

      out.printf("이름: %s\n", member.getName());
      out.printf("이메일: %s\n", member.getEmail());
      out.printf("암호: %s\n", member.getPassword());
      out.printf("사진: %s\n", member.getPhoto());
      out.printf("전화: %s\n", member.getTel());
      out.printf("가입일: %s\n", member.getRegisteredDate());

    } catch (Exception e) {
      out.printf("%s : %s\n", e.toString(), e.getMessage());
    }
  }
  
  @CommandMapping("/member/list")
  public void list(BufferedReader in, PrintWriter out) {
    try {
      List<Member> members = memberDao.list();
      if (members == null) { 
        out.println("서버에서 데이터를 가져오는데 오류 발생!");
        return;
      }
      
      for (Member member : members) {
        out.printf("%3d, %-4s, %-20s, %-15s, %s\n", 
            member.getNo(), member.getName(), 
            member.getEmail(), member.getTel(), member.getRegisteredDate());
      }
      
    } catch (Exception e) {
      out.printf("%s : %s\n", e.toString(), e.getMessage());
    }
  }
  
  @CommandMapping("/member/search")
  public void search(BufferedReader in, PrintWriter out) {
    try {
      out.print("검색어?\n!{}!\n");
      out.flush();
      String keyword = in.readLine();
      
      List<Member> members = memberDao.search(keyword);
      if (members == null) { 
        out.println("서버에서 데이터를 가져오는데 오류 발생!");
        return;
      }
      
      out.printf("검색 결과: %d 건\n", members.size());
      
      for (Member member : members) {
        out.printf("%3d, %-4s, %-20s, %-15s, %s\n", 
            member.getNo(), member.getName(), 
            member.getEmail(), member.getTel(), member.getRegisteredDate());
      }
      
    } catch (Exception e) {
      out.printf("%s : %s\n", e.toString(), e.getMessage());
    }
  }
  
  @CommandMapping("/member/update")
  public void update(BufferedReader in, PrintWriter out) {
    try {
      out.print("번호?\n!{}!\n"); out.flush();
      int no = Integer.parseInt(in.readLine());

      Member oldValue = memberDao.detail(no);
      if (oldValue == null) { 
        out.println("해당 회원을 찾을 수 없습니다.");
        return;
      } 

      Member member = new Member();
      member.setNo(no);
      
      out.printf("이름(%s)?\n!{}!\n", oldValue.getName()); out.flush();
      String input = in.readLine();
      if (input.length() > 0) 
        member.setName(input);

      out.printf("이메일(%s)?\n!{}!\n", oldValue.getEmail()); out.flush();
      if ((input = in.readLine()).length() > 0)
        member.setEmail(input);

      out.print("암호(********)?\n!{}!\n"); out.flush();
      if ((input = in.readLine()).length() > 0)
        member.setPassword(input);

      out.printf("사진(%s)?\n!{}!\n", oldValue.getPhoto()); out.flush();
      if ((input = in.readLine()).length() > 0)
        member.setPhoto(input);

      out.printf("전화(%s)?\n!{}!\n", oldValue.getTel()); out.flush();
      if ((input = in.readLine()).length() > 0)
        member.setTel(input);

      memberDao.update(member);
      out.println("회원을 변경했습니다.");

    } catch (Exception e) {
      out.printf("%s : %s\n", e.toString(), e.getMessage());
    }
  }
}
