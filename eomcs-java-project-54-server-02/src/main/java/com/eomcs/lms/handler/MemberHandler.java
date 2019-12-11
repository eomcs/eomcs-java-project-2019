package com.eomcs.lms.handler;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import com.eomcs.lms.dao.MemberDao;
import com.eomcs.lms.domain.Member;
import com.eomcs.stereotype.CommandMapping;

@Component
public class MemberHandler {

  MemberDao memberDao;

  public MemberHandler(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  @CommandMapping("/member/add")
  public void add(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    Member member = new Member();
    member.setName(request.getParameter("name"));
    member.setEmail(request.getParameter("email"));
    member.setPassword(request.getParameter("password"));
    member.setPhoto(request.getParameter("photo"));
    member.setTel(request.getParameter("tel"));

    memberDao.add(member);
    
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head><title>회원관리</title></head>");
    out.println("<body>");
    out.println("<h1>회원 추가</h1>");
    out.println("<p>회원을 저장했습니다.</p>");
    out.println("</body>");
    out.println("</html>");
    
    response.setHeader("Refresh", "1;url=list");
  }
  
  @CommandMapping("/member/delete")
  public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    int no = Integer.parseInt(request.getParameter("no"));
    
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head><title>회원관리</title></head>");
    out.println("<body>");
    out.println("<h1>회원 삭제</h1>");

    if (memberDao.delete(no) > 0) 
      out.println("<p>회원을 삭제했습니다.</p>");
    else 
      out.println("<p>해당 회원을 찾을 수 없습니다.</p>");
    
    out.println("</body>");
    out.println("</html>");
    
    response.setHeader("Refresh", "1;url=list");
  }
  
  @CommandMapping("/member/detail")
  public void detail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    int no = Integer.parseInt(request.getParameter("no"));

    Member member = memberDao.detail(no);

    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head><title>회원관리</title></head>");
    out.println("<body>");
    out.println("<h1>회원 상세 정보</h1>");

    if (member == null) { 
      out.println("<p>해당 회원을 찾을 수 없습니다.</p>");

    } else {
      out.println("<form action='/app/member/update' method='post'>");
      out.printf("<input type='hidden' name='no' value='%d'>\n", no);
      out.println("<table border='1'>");
      out.printf("<tr><th>이름</th>"
          + "<td><input type='text' name='name' value='%s'></td></tr>\n", 
          member.getName());
      out.printf("<tr><th>이메일</th>"
          + "<td><input type='text' name='email' value='%s'></td></tr>\n", 
          member.getEmail());
      out.println("<tr><th>암호</th><td><input type='password' name='password'></td></tr>\n");
      out.printf("<tr><th>사진</th>"
          + "<td><input type='text' name='photo' value='%s'></td></tr>\n", 
          member.getPhoto());
      out.printf("<tr><th>전화</th>"
          + "<td><input type='text' name='tel' value='%s'></td></tr>\n", 
          member.getTel());
      out.printf("<tr><th>가입일</th><td>%s</tr>\n", member.getRegisteredDate());
      out.println("<tr><th></th><td><button>변경</button>");
      out.printf("<a href='delete?no=%d'>삭제</a></td></tr>\n", no);
      out.println("</table>");
      out.println("</form>");
    }
    out.println("</body>");
    out.println("</html>");
  }
  
  @CommandMapping("/member/list")
  public void list(HttpServletRequest request, HttpServletResponse response) throws Exception {
    List<Member> members = memberDao.list();

    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head><title>회원관리</title></head>");
    out.println("<body>");
    out.println("<h1>회원 목록</h1>");

    out.println("<a href='/member/form.html'>새 회원</a>");
    out.println("<table border='1'>");
    out.println("<tr> <th>번호</th> <th>이름</th> <th>이메일</th> <th>전화</th> <th>가입일</th> </tr>");

    for (Member member : members) {
      out.println("<tr>");
      out.printf("<td>%d</td>\n", member.getNo());
      out.printf("<td><a href='detail?no=%d'>%s</a></td>\n", member.getNo(), member.getName());
      out.printf("<td>%s</td>\n", member.getEmail());
      out.printf("<td>%s</td>\n", member.getTel());
      out.printf("<td>%s</td>\n", member.getRegisteredDate());
      out.println("</tr>");
    }
    out.println("</table>");

    out.println("<p><a href='/member/search.html'>검색</a></p>");
    out.println("</body>");
    out.println("</html>");
    
  }
  
  @CommandMapping("/member/search")
  public void search(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    String keyword = request.getParameter("keyword");
    
    List<Member> members = memberDao.search(keyword);
    
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head><title>회원관리</title></head>");
    out.println("<body>");
    out.println("<h1>검색 결과</h1>");

    out.println("<table border='1'>");
    out.println("<tr> <th>번호</th> <th>이름</th> <th>이메일</th> <th>전화</th> <th>가입일</th> </tr>");

    for (Member member : members) {
      out.println("<tr>");
      out.printf("<td>%d</td>\n", member.getNo());
      out.printf("<td><a href='detail?no=%d'>%s</a></td>\n", member.getNo(), member.getName());
      out.printf("<td>%s</td>\n", member.getEmail());
      out.printf("<td>%s</td>\n", member.getTel());
      out.printf("<td>%s</td>\n", member.getRegisteredDate());
      out.println("</tr>");
    }
    out.println("</table>");
    out.println("</body>");
    out.println("</html>");
  }
  
  @CommandMapping("/member/update")
  public void update(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Member member = new Member();
    member.setNo(Integer.parseInt(request.getParameter("no")));
    member.setName(request.getParameter("name"));
    member.setEmail(request.getParameter("email"));
    member.setPassword(request.getParameter("password"));
    member.setPhoto(request.getParameter("photo"));
    member.setTel(request.getParameter("tel"));

    memberDao.update(member);
    
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head><title>회원관리</title></head>");
    out.println("<body>");
    out.println("<h1>회원 변경</h1>");
    out.println("<p>회원을 변경했습니다.</p>");
    out.println("</body>");
    out.println("</html>");
    
    response.setHeader("Refresh", "1;url=list");
  }
}
