package com.eomcs.lms;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import com.eomcs.context.ApplicationContextListener;
import com.eomcs.context.CommandMappingHandlerMapping;
import com.eomcs.context.CommandMappingHandlerMapping.HandlerMethod;

public class App {

  private static final Logger logger = LogManager.getLogger(App.class);
  
  // Observer를 보관할 저장소
  ArrayList<ApplicationContextListener> applicationContextListeners = new ArrayList<>();

  // Observer와 서로 공유할 값을 저장하는 보관소
  Map<String,Object> context = new HashMap<>();

  // 서버 포트 번호
  int port;
  ServerSocket serverSocket;

  // IoC 컨테이너
  ApplicationContext iocContainer;
  
  // @CommandMapping 메서드 맵
  CommandMappingHandlerMapping handlerMapping;

  //스레드 풀
  ExecutorService executorService = Executors.newCachedThreadPool();
  
  public App(int port) {
    this.port = port;
  }

  public void service() throws Exception {
    // 서비스를 실행하기 전에 등록된 모든 Observer를 호출하여 시작을 알린다.
    for (ApplicationContextListener listener : applicationContextListeners) {
      listener.contextInitialized(context);
    }

    // ContextLoaderListner가 준비한 IoC 컨테이너 꺼내기
    iocContainer = (ApplicationContext) context.get("iocContainer");
    
    // ContextLoaderListner가 준비한 @CommanMapping 메서드 맵 꺼내기
    handlerMapping = (CommandMappingHandlerMapping) context.get("handlerMapping");
    
    // 클라이언트와 연결할 준비하기
    serverSocket = new ServerSocket(port);
    logger.info("서버 실행!");

    while (true) {
      try {
        // 스레드풀에 작업을 전달한다.
        executorService.submit(new RequestHandler(serverSocket.accept()));
      } catch (Exception e) {
        // 클라이언트와 연결하다거 오류가 발생한 경우 무시한다.
      }
    }
    
  }
  
  private void shutdown() {
    try {
      // 스레드풀의 자원을 해제한다.
      executorService.shutdown();
      
      // 서비스를 종료하기 전에 등록된 모든 Observer를 호출하여 종료를 알린다.
      for (ApplicationContextListener listener : applicationContextListeners) {
        listener.contextDestroyed(context);
      }
  
      serverSocket.close();
      logger.info("서버 종료!");
    } catch (Exception e) {
      // 종료 시에 발생하는 예외는 무시한다.
    } finally {
      System.exit(0);
    }
  }

  // Observer를 등록하는 메서드
  private void addApplicationContextListener(ApplicationContextListener listener) {
    applicationContextListeners.add(listener);
  }

  public static void main(String[] args) throws Exception {
    App app = new App(8888);

    // App 객체에 Observer를 등록한다.
    app.addApplicationContextListener(new ContextLoaderListener());

    // App 을 실행한다.
    app.service();
  }

  class RequestHandler implements Runnable {

    Socket socket;

    public RequestHandler(Socket socket) {
      logger.info("클라이언트와 연결되었음.");
      logger.debug("스레드 생성됨!");
      this.socket = socket;
    }

    @Override
    public void run() {
      logger.debug("스레드 실행...");
      try (Socket socket = this.socket;
          BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
          PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()))) {

        String command = in.readLine();
        logger.debug("명령어: " + command);
        
        // 사용자가 입력한 명령을 처리할 핸들러를 찾는다.
        HandlerMethod commandHandler = (HandlerMethod) handlerMapping.getHandlerMethod(command);

        if (commandHandler != null) {
          try {
            commandHandler.method.invoke(commandHandler.bean, in, out);
          } catch (Exception e) {
            out.println("명령어 실행 중 오류 발생 : " + e.toString());
            logger.warn("명령어 실행 중 오류 발생", e);
          }
        } else if (command.equals("shutdown")) {
          shutdown();
          return;

        } else {
          out.println("실행할 수 없는 명령입니다.");
        }

        // 서버의 응답이 끝났음을 알린다. 
        out.println("\n!end!");
        out.flush();

      } catch (Exception e) {
        logger.warn("클라이언트와 통신중 오류 발생", e);
      } finally {
        logger.info("클라이언트와 연결 종료!");
        
        // 다른 요청이 들어 왔을 때 스레드풀에서 다른 스레드를 통해 처리하는지 확인하기 위해 
        // 일부로 시간을 지연시킴.
        //try {Thread.sleep(5000);} catch (Exception e) {}
        
        logger.debug("스레드 종료!");
      }
    }
  }
}
