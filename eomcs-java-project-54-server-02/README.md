# eomcs-java-project-6.3-server

Web 기술 도입하기

- 웹 기술을 사용하여 서버를 구현하는 방법
- 웹 애플리케이션의 개념과 구동 원리
- 톰캣 서블릿 컨테이너를 사용하는 방법

## 프로젝트 - 수업관리 시스템  

클라이언트와 서버의 통신 부분에 웹 기술을 적용해보자.
웹 기술을 도입하면 전용 클라이언트가 필요 없다.
클라이언트는 웹 브라우저로 대체 된다.
서버 쪽 통신은 서블릿 컨테이너가 대신하기 때문에 네트워킹 프로그래밍을 할 필요가 없다.
즉 소켓 프로그래밍이나 멀티 스레드 프로그래밍을 할 필요가 없다.


### ver 6.3.0 - 자바 프로젝트를 웹 애플리케이션 프로젝트로 전환하라.

#### 1단계) 이클립스 자바 웹 프로젝트로 설정을 변경한다.

- src/main/webapp 디렉토리 생성
    - HTML, CSS, JavaScript, JSP 등 웹 자원을 둘 폴더는 생성한다.
- build.gradle
    - `eclipse` 대신에 `eclipse-wtp`와 `war` 플로그인을 추가한다.
    - 더이상 단독으로 실행하는 애플리케이션이 아니기 때문에 `application` 플러그인은 제거한다.
    - 또한 이와 관련된 속성도 제거한다.
    - `servlet-api` 라이브러리를 추가한다.
    - `$ gradle eclipse`를 실행하여 이클립스 설정 파일을 갱신한다.
    - 이클립스의 프로젝트를 갱신한다.

#### 2단계) 웹 애플리케이션 기본 웹 페이지를 생성한다.

- src/main/webapp/index.html
    - 환영 인사를 하는 기본 웹 페이지를 생성한다.

#### 3단계) 톰캣 서버를 설치하고 이클립스에 등록한다.

- Tomcat 서버 설치
    - http://tomcat.apache.org 에서 다운로드 한다.
    - 원하는 디렉토리에 압축 해제한다.
- 이클립스 환경 설정
    - `Server / Runtime Environments` 에 톰캣을 압축 해제한 홈 디렉토리를 등록한다.

#### 4단계) 웹 애플리케이션 테스트 할 톰캣 서버 실행 환경을 생성한다.

- 톰캣 실행 환경 설정
    - 이클립스 `Servers` 뷰에 톰캣 서버 실행 환경을 추가한다.

#### 5단계) 웹 애플리케이션을 톰캣 서버에 배치한다.

- 웹 애플리케이션을 톰캣 서버에 배치
    - 이클립스 `Servers` 뷰 / 톰캣 서버 실행 환경 / 웹 애플리케이션을 추가한다.

#### 6단계) 웹 애플리케이션 배치를 확인한다.

- 웹 애플리케이션 실행
    - 톰캣 실행 환경에서 서버를 시작한다.
- 웹 브라우저를 이용하여 서버에 접속
    - URL `http://localhost:8080/`으로 접속한다.

##### 실습 결과

index.html 에 작성한 문구를 웹 브라우저 화면에 출력한다.


### ver 6.3.1 - 기존의 명령 핸들러를 HTTP 요청을 처리하는 클래스로 바꿔라.

#### 1단계) 웹 애플리케이션이 시작될 때 핸들러와 DAO, MyBatis를 준비한다.

- ContextLoaderListener.java
    - 클래스를 `ServletContextListener` 인터페이스의 규칙에 맞춘다.
    - 즉 웹 애플리케이션이 시작하거나 종료할 때 특정 메서드가 호출된다.
- ApplicationContextListener.java
    - 이 인터페이스를 제거한다.

#### 2단계) App 클래스를 서블릿으로 전환한다.

- App.java
    - `javax.servlet.Servlet` 인터페이스의 규칙에 따라 변경한다.

#### 3단계) '/lesson/list' 명령 처리 핸들러를 새 호출 규칙에 따라 변경한다.

- list() 메서드의 파라미터를 변경한다.
  - 기존의 파라미터 대신에 `HttpServletRequest`와 `HttpServletResponse` 값을 받는 파라미터로 바꾼다.
  - Servlet 규칙에 따라 HTML 페이지를 출력한다.
  - HTML 태그를 사용하여 출력 화면을 만들어 웹 브라우저로 보낸다.

#### 4단계) '/lesson/detail' 명령 처리 핸들러를 새 호출 규칙에 따라 변경한다.

- list()
  - a 태그를 이용하여 제목에 detail 명령으로 링크를 건다.
- detail() 메서드의 파라미터를 변경한다.
  - 기존의 파라미터 대신에 `HttpServletRequest`와 `HttpServletResponse` 값을 받는 파라미터로 바꾼다.
  - Servlet 규칙에 따라 클라이언트가 보낸 no 값을 꺼낸다.
  - Servlet 규칙에 따라 HTML 페이지를 출력한다.


#### 5단계) '/lesson/add' 명령 처리 핸들러를 새 호출 규칙에 따라 변경한다.

- src/main/webapp/lesson/form.html
  - 수업 입력폼 페이지지를 만든다.
- list()
  - a 태그를 이용하여 form.html 페이지로 링크를 건다.
- add() 메서드의 파라미터를 변경한다.
  - 기존의 파라미터 대신에 `HttpServletRequest`와 `HttpServletResponse` 값을 받는 파라미터로 바꾼다.
  - Servlet 규칙에 따라 클라이언트가 보낸 값을 꺼낸다.
  - Servlet 규칙에 따라 HTML 페이지를 출력한다.

#### 6단계) '/lesson/update' 명령 처리 핸들러를 새 호출 규칙에 따라 변경한다.

- detail()
  - input 태그를 사용하여 값을 변경할 수 있는 페이지로 바꾼다.
- update() 메서드의 파라미터를 변경한다.
  - 기존의 파라미터 대신에 `HttpServletRequest`와 `HttpServletResponse` 값을 받는 파라미터로 바꾼다.
  - Servlet 규칙에 따라 클라이언트가 보낸 값을 꺼낸다.
  - Servlet 규칙에 따라 HTML 페이지를 출력한다.

#### 7단계) '/lesson/search' 명령 처리 핸들러를 새 호출 규칙에 따라 변경한다.

- src/main/webapp/lesson/search.html
  - 수업 검색폼 페이지지를 만든다.
- list()
  - a 태그를 이용하여 검색폼 페이지로 링크를 건다.
- search() 메서드의 파라미터를 변경한다.
  - 기존의 파라미터 대신에 `HttpServletRequest`와 `HttpServletResponse` 값을 받는 파라미터로 바꾼다.
  - Servlet 규칙에 따라 클라이언트가 보낸 값을 꺼낸다.
  - Servlet 규칙에 따라 HTML 페이지를 출력한다.


#### 8단계) '/lesson/delete' 명령 처리 핸들러를 새 호출 규칙에 따라 변경한다.

- detail()
  - a 태그를 이용하여 delete 명령으로 링크를 건다.
- delete() 메서드의 파라미터를 변경한다.
  - 기존의 파라미터 대신에 `HttpServletRequest`와 `HttpServletResponse` 값을 받는 파라미터로 바꾼다.
  - Servlet 규칙에 따라 클라이언트가 보낸 값을 꺼낸다.
  - Servlet 규칙에 따라 HTML 페이지를 출력한다.

#### 9단계) MemberHandler, BoardHandler, PhotoBoardHandler, AuthHandler 도 새 호출 규칙에 따라 변경한다.

각 명령 처리 핸들러에 대해 위의 3단계에서 8단계까지 작업을 반복 수행한다.


## 실습 소스

- com/eomcs/lms/ContextLoaderListener.java 변경
- com/eomcs/context/ApplicationContextListener.java 삭제
- com/eomcs/lms/App.java 변경
- com/eomcs/lms/handler/LessonHandler.java 변경
- src/main/webapp/lesson/form.html 추가
- src/main/webapp/lesson/search.html 추가
- com/eomcs/lms/handler/MemberHandler.java 변경
- src/main/webapp/member/form.html 추가
- src/main/webapp/member/search.html 추가
- com/eomcs/lms/handler/BoardHandler.java 변경
- src/main/webapp/board/form.html 추가
- com/eomcs/lms/handler/PhotoBoardHandler.java 변경
- src/main/webapp/photoboard/form.html 추가
- com/eomcs/lms/handler/AuthHandler.java 변경
- src/main/webapp/auth/form.html 추가
- src/main/webapp/index.html 변경
