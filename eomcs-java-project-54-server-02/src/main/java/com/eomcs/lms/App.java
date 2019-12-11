package com.eomcs.lms;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import com.eomcs.context.CommandMappingHandlerMapping;
import com.eomcs.context.CommandMappingHandlerMapping.HandlerMethod;

@SuppressWarnings("serial")
@WebServlet("/app/*")
public class App extends HttpServlet {

  private static final Logger logger = LogManager.getLogger(App.class);

  // IoC 컨테이너
  ApplicationContext iocContainer;

  // @CommandMapping 메서드 맵
  CommandMappingHandlerMapping handlerMapping;

  @Override
  public void init() throws ServletException {
    ServletContext context = this.getServletContext();

    // ContextLoaderListner가 준비한 IoC 컨테이너 꺼내기
    iocContainer = (ApplicationContext) context.getAttribute("iocContainer");

    // ContextLoaderListner가 준비한 @CommanMapping 메서드 맵 꺼내기
    handlerMapping = (CommandMappingHandlerMapping) context.getAttribute("handlerMapping");
  }

   @Override
  public void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    logger.info("클라이언트 요청이 들어왔음.");
    
    try {
      // 입력되는 한글 데이터의 문자표를 지정한다.
      request.setCharacterEncoding("UTF-8");
      
      // 출력하는 콘텐츠의 MIME 타입과 문자표를 지정한다.
      response.setContentType("text/html;charset=UTF-8");
      
      String command = request.getPathInfo();
      logger.debug("명령어: " + command);

      // 사용자가 입력한 명령을 처리할 핸들러를 찾는다.
      HandlerMethod commandHandler = (HandlerMethod) handlerMapping.getHandlerMethod(command);

      if (commandHandler != null) {
        commandHandler.method.invoke(commandHandler.bean, request, response);
        
      } else {
        logger.error("실행할 수 없는 명령입니다.");
        throw new ServletException("실행할 수 없는 명령입니다.");
      }
    } catch (Exception e) {
      logger.error("클라이언트 요청 처리 중 오류 발생!", e);
      throw new ServletException("클라이언트 요청 처리 중 오류 발생!", e);
    }
  }
}
