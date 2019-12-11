# eomcs-java-project-4.3-server

여러 클라이언트의 요청을 동시에 처리하는 방법

- `Thread`를 활용하여 멀티 스레드 프로그래밍하는 방법
- 중첩 클래스의 활용

## 프로젝트 - 수업관리 시스템  

### 과제: 클라이언트 요청이 들어오면 별도의 스레드에서 처리하라.

#### ver 4.3.0

- RequestHandler 중첩 클래스 
    - Runnable 인터페이스를 구현한다.
    - 클라이언트 요청을 처리한다.
- ServerApp.java
    - 클라이언트와 연결된 후 여러 번 명령을 처리하는 로직을 한 번만 처리하도록 변경한다.

#### 실행 결과

먼저 `ServerApp`을 실행한다.
```
실행 결과는 이전 버전과 같다.
```

## 실습 소스

- src/main/java/com/eomcs/lms/ServerApp.java 변경