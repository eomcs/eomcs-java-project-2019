package com.eomcs.lms.handler;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import com.eomcs.lms.dao.MemberDao;
import com.eomcs.lms.domain.Member;
import com.eomcs.stereotype.CommandMapping;

@Component
public class AuthHandler {

  MemberDao memberDao;

  public AuthHandler(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  @CommandMapping("/auth/login")
  public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    Map<String,Object> params = new HashMap<>();
    params.put("email", request.getParameter("email"));
    params.put("password", request.getParameter("password"));

    Member member = memberDao.detailByEmailPassword(params);
    
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head><title>로그인</title></head>");
    out.println("<body>");
    out.println("<h1>로그인</h1>");
    if (member != null)
      out.printf("<p>%s님 환영합니다!</p>\n", member.getName());
    else
      out.println("<p>이메일 또는 암호가 맞지 않습니다!</p>");
    out.println("</body>");
    out.println("</html>");
    
    response.setHeader("Refresh", "1;url=../board/list");
  }
}
