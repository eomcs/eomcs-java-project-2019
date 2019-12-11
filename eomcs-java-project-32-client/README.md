# eomcs-java-project-4.1-client

네트워크 API를 활용하여 애플리케이션 사이에 데이터 주고 받기

- 데이터 관리를 별도의 프로그램으로 분리하기
- 네트워크 API 사용법
- 클라이언트/서버 애플리케이션 아키텍처의 이해
- `Stateful` 통신 방식의 특징과 장단점 이해하기
- 상속의 일반화(generalization) 기법과 추상 클래스 활용법

## 프로젝트 - 수업관리 시스템  

### 과제: 사용자 UI 부분을 클라이언트로 만들라.

`App.java`에서 데이터 관리 부분을 제외한 사용자 UI 부분을 추출한다.

- `eomcs-java-project-client` 프로젝트로 분리한다.

#### ver 4.1.0

데이터 관리 부분을 제외한 나머지를 클라이언트로 만들라.

- Lesson.java, Member.java, Board.java
  - 테스트 할 때 객체의 필드 값을 확인할 수 있도록 toString()을 오버라이딩 한다.
- com.eomcs.lms.proxy
  - 서버쪽의 DAO와 통신을 담당할 Proxy 클래스를 둘 패키지를 생성한다.
- LessonDaoProxy.java
  - 서버의 `LessonDao`와 통신하는 부분을 캡슐화한다.
  - `Proxy` 디자인 패턴을 응용하여 클라이언트 측에서 서버의 `LessonDao` 객체를 대행하게 한다.
- MemberDaoProxy.java
  - 서버의 `MemberDao`와 통신하는 부분을 캡슐화한다.
  - `Proxy` 디자인 패턴을 응용하여 클라이언트 측에서 서버의 `MemberDao` 객체를 대행하게 한다.
- BoardDaoProxy.java
  - 서버의 `BoardDao`와 통신하는 부분을 캡슐화한다.
  - `Proxy` 디자인 패턴을 응용하여 클라이언트 측에서 서버의 `BoardDao` 객체를 대행하게 한다.  
- DataLoaderListener.java
  - 애플리케이션을 시작할 때 DAO Proxy를 준비한다.
  - 애플리케이션을 종료할 때 서버에 종료 명령을 보낸 후 소켓을 닫는다.
- XxxCommand.java
  - 생성자에서 List 대신 DAO Proxy를 받아 데이터를 처리한다.
- App.java
  - 파일에서 데이터를 로딩하는 코드를 제거한다.
  - 커맨드 객체의 변경에 맞춰 코드를 변경한다.

#### 실행 결과

서버 애플리케이션의 `ServerApp`을 먼저 실행한다.
```
수업 데이터를 로딩 성공!
회원 데이터를 로딩 성공!
게시글 데이터를 로딩 성공!
서버 시작!
.
.
.
클라이언트와 연결되었음.
```

클라이언트 애플리케이션의 `App`을 실행한다. 실행은 이전 버전과 같다.
```
명령> /lesson/list
  1, 과정1x           , 2019-01-02 ~ 2019-01-16, 1001
  2, 과정2            , 2019-02-01 ~ 2019-02-15, 1000

명령> /lesson/add
번호? 3
수업명? 과정3
설명? 설명입니다.
시작일? 2019-1-1
종료일? 2019-2-5
총수업시간? 120
일수업시간? 8
수업을 저장했습니다.

명령> /lesson/list
  1, 과정1x           , 2019-01-02 ~ 2019-01-16, 1001
  2, 과정2            , 2019-02-01 ~ 2019-02-15, 1000
  3, 과정3            , 2019-01-01 ~ 2019-02-05,  120

명령> 
.
.
.
```


## 실습 소스

- src/main/java/com/eomcs/lms/domain/Lesson.java 변경
- src/main/java/com/eomcs/lms/domain/Member.java 변경
- src/main/java/com/eomcs/lms/domain/Board.java 변경
- src/main/java/com/eomcs/lms/proxy 패키지 추가
- src/main/java/com/eomcs/lms/proxy/LessonDaoProxy 추가
- src/main/java/com/eomcs/lms/proxy/MemberDaoProxy 추가
- src/main/java/com/eomcs/lms/proxy/BoardDaoProxy 추가
- src/main/java/com/eomcs/lms/DataLoaderListener.java 변경 
- src/main/java/com/eomcs/lms/handler/XxxCommand.java 변경
- src/main/java/com/eomcs/lms/App.java 변경
