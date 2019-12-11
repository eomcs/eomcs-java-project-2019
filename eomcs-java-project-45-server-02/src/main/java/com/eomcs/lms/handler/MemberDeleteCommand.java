package com.eomcs.lms.handler;
import java.io.BufferedReader;
import java.io.PrintWriter;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.eomcs.lms.dao.MemberDao;

public class MemberDeleteCommand implements Command {

  SqlSessionFactory sqlSessionFactory;

  public MemberDeleteCommand(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void execute(BufferedReader in, PrintWriter out) {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      out.print("번호?\n!{}!\n"); out.flush();
      int no = Integer.parseInt(in.readLine());

      MemberDao memberDao = sqlSession.getMapper(MemberDao.class); 
      if (memberDao.delete(no) > 0) 
        out.println("회원을 삭제했습니다.");
      else 
        out.println("해당 회원을 찾을 수 없습니다.");

      sqlSession.commit();
      
    } catch (Exception e) {
      out.printf("%s : %s\n", e.toString(), e.getMessage());
    }
  }
}
