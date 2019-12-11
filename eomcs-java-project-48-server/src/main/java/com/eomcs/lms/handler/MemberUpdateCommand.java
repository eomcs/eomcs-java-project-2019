package com.eomcs.lms.handler;
import java.io.BufferedReader;
import java.io.PrintWriter;
import com.eomcs.lms.dao.MemberDao;
import com.eomcs.lms.domain.Member;
import com.eomcs.stereotype.CommandMapping;
import com.eomcs.stereotype.Component;

@Component
public class MemberUpdateCommand {

  MemberDao memberDao;

  public MemberUpdateCommand(MemberDao memberDao) {
    this.memberDao = memberDao;
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
