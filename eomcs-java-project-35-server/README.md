# eomcs-java-project-4.4-server

스레드풀을 이용하여 스레드를 재사용하기

- 스레드 생성과 수거 비용을 줄이는 방법
- 자바에서 제공하는 클래스를 이용하여 스레드풀을 만들고 사용하기
- `Object pool` 디자인 패턴의 용도와 이점 이해하기 

## 프로젝트 - 수업관리 시스템  

### 과제: 스레드풀을 적용하라.


#### ver 4.4.0

- ServerApp.java
    - 스레드풀을 준비한다.
    - 클라이언트가 연결되면 요청을 처리할 `RequestHandler`를 만들어 스레드풀에 실행을 맡긴다.
    - shutdown() 메서드가 호출되면 스레드풀을 해제하고 시스템을 종료한다.
- RequestHandler 중첩 클래스
    - shutdown 명령 처리를 추가한다.

#### 실행 결과

먼저 `ServerApp`을 실행한다.
```
실행 결과는 이전 버전과 같다.
```

## 실습 소스

- src/main/java/com/eomcs/lms/ServerApp.java 변경