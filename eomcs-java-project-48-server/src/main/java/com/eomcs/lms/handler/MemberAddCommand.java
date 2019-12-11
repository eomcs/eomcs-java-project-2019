package com.eomcs.lms.handler;
import java.io.BufferedReader;
import java.io.PrintWriter;
import com.eomcs.lms.dao.MemberDao;
import com.eomcs.lms.domain.Member;
import com.eomcs.stereotype.CommandMapping;
import com.eomcs.stereotype.Component;

@Component
public class MemberAddCommand {

  MemberDao memberDao;

  public MemberAddCommand(MemberDao memberDao) {
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
}
