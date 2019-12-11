# eomcs-java-project-4.2-client

동일한 자원으로 더 많은 클라이언트의 요청을 처리하는 방법

- `Stateless` 통신 방식으로 전환하기
- `Stateless` 통신 방식의 특징과 장단점 이해하기
 
## 프로젝트 - 수업관리 시스템  

### 과제: `Stateful`에서 `Stateless`로 바꿔라.

서버에 연결하면 한 번만 요청하고 서버의 응답을 받은 후 즉시 연결을 끊는다.

#### ver 4.2.0

- LessonDaoProxy.java
  - 생성자의 파라미터를 서버와 포트 번호를 받도록 변경한다.
  - 명령을 요청할 때 마다 서버에 연결하고, 서버로부터 응답을 받으면 즉시 연결을 끊는다.
- MemberDaoProxy.java
  - 생성자의 파라미터를 서버와 포트 번호를 받도록 변경한다.
  - 명령을 요청할 때 마다 서버에 연결하고, 서버로부터 응답을 받으면 즉시 연결을 끊는다.
- BoardDaoProxy.java
  - 생성자의 파라미터를 서버와 포트 번호를 받도록 변경한다.
  - 명령을 요청할 때 마다 서버에 연결하고, 서버로부터 응답을 받으면 즉시 연결을 끊는다.
- DataLoaderListener.java
  - 애플리케이션을 시작할 때 DAO Proxy를 준비한다.
  - DAO Proxy 객체를 생성할 때 서버 주소와 포트 번호를 넘긴다.
  - 애플리케이션을 종료할 때 

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
클라이언트와 연결되었음.    <=== 요청이 들어올 때 마다 출력됨
클라이언트와 연결을 끊었음. <=== 응답 하면 즉시 출력됨
```

클라이언트 애플리케이션의 `App`을 실행한다. 실행은 이전 버전과 같다.
```
이전과 같다.
```


## 실습 소스

- src/main/java/com/eomcs/lms/proxy/LessonDaoProxy 변경
- src/main/java/com/eomcs/lms/proxy/MemberDaoProxy 변경
- src/main/java/com/eomcs/lms/proxy/BoardDaoProxy 변경 
- src/main/java/com/eomcs/lms/DataLoaderListener.java 변경 
