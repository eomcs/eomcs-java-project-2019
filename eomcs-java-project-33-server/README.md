# eomcs-java-project-4.2-server

동일한 자원으로 더 많은 클라이언트의 요청을 처리하는 방법

- `Stateless` 통신 방식으로 전환하기
- `Stateless` 통신 방식의 특징과 장단점 이해하기
   
## 프로젝트 - 수업관리 시스템  

### 과제: 통신 방식을 `Stateful`에서 `Stateless`로 바꿔라.

클라이언트와 연결되면 한 번의 요청을 처리한 후 즉시 연결을 끊는다.

#### ver 4.2.0

- ServerApp.java
    - 클라이언트와 연결된 후 여러 번 명령을 처리하는 로직을 한 번만 처리하도록 변경한다.

#### 실행 결과

먼저 `ServerApp`을 실행한다.
```
이전과 실행 결과는 같다.
```

## 실습 소스

- src/main/java/com/eomcs/lms/ServerApp.java 변경