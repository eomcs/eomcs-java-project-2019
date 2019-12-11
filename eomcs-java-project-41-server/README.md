# eomcs-java-project-5.0-server

커넥션 재사용을 위해 커넥션풀 적용하기

- 커넥션풀의 동작 원리와 사용해야 하는 이유

## 프로젝트 - 수업관리 시스템  

이전 버전에서는 클라이언트 요청에 대해 SQL 작업을 실행할 때마다 DB 커넥션을 만들어 사용했다. 작업이 끝난 후에는 사용한 DB 커넥션을 닫은 후 버렸다. 이 방식은 다음의 문제를 발생시킨다. 

SQL을 실행할 때마다 DB 커넥션을 생성하는데, DBMS에 연결하는 과정 중에서 사용자 인증 과정을 수행하기 때문에 매번 일정 시간이 소요된다는 문제가 발생한다. 또한 사용한 DB 커넥션은 버리기 때문에 가비지가 많이 발생한다.

이를 해결하기 위해 `Object pool` 디자인 패턴을 적용한 스레드풀처럼 DB 커넥션에 대해서도 이 디자인 패턴을 적용한 DB 커넥션풀을 만들어 사용할 것이다.

### ver 5.0.0 - DB 커넥션풀을 이용하여 커넥션 객체를 관리하라.

- ConnectionPool.java
    - 커넥션풀에서 꺼낸 DB 커넥션인 경우, `close()`를 호출했을 때 연결을 끊지 말고 그대로 커넥션풀에 반납해야 한다.  
    - 그러나 `TxConnection`과 `DataSource`가 상호 참조를 해서는 안되기 대문에 이처럼 별도의 인터페이스를 만들어 간접적으로 상호 참조한다.
- DataSource.java
    - `ConnectionPool`을 구현한다.
    - `returnConnection()`과 `close()`를 추가로 정의한다.
- TxConnection.java
    - `close()`가 호출되었을 때 연결을 끊지 말고 커넥션풀에 반납한다.
- DataLoaderListener.java
    - 애플리케이션을 종료할 때 `커넥션풀(DataSource)`에 있는 모든 커넥션을 닫는다.

#### 실행 결과

`eomcs-java-project-server` 프로젝트의 `App` 클래스를 실행한다.
```
DataLoaderListener.contextInitialized() 실행!
서버 실행!
클라이언트와 연결되었음.
스레드 생성됨!
스레드 실행... - pool-1-thread-1
명령어: /photoboard/list
[pool-1-thread-1] - DB 커넥션 생성
커넥션풀에 DB 커넥션을 반납
클라이언트와 연결 종료!
스레드 종료!
클라이언트와 연결되었음.
스레드 생성됨!
스레드 실행... - pool-1-thread-1
명령어: /photoboard/update
[pool-1-thread-1] - 커넥션풀에서 DB 커넥션을 꺼냄
커넥션풀에 DB 커넥션을 반납
[pool-1-thread-1] - 커넥션풀에서 DB 커넥션을 꺼냄
커넥션풀에 DB 커넥션을 반납
트랜잭션을 시작함.
[pool-1-thread-1] - 커넥션풀에서 DB 커넥션을 꺼냄
[pool-1-thread-1] - ThreadLocal <=== DB 커넥션풀
[pool-1-thread-1] - ThreadLocal의 DB 커넥션
[pool-1-thread-1] - ThreadLocal의 DB 커넥션
[pool-1-thread-1] - ThreadLocal의 DB 커넥션
[pool-1-thread-1] - ThreadLocal의 DB 커넥션
[pool-1-thread-1] - ThreadLocal의 DB 커넥션
[pool-1-thread-1] - ThreadLocal의 DB 커넥션
[pool-1-thread-1] - ThreadLocal의 DB 커넥션
commit()
[pool-1-thread-1] - ThreadLocal ===> DB 커넥션풀
커넥션풀에 DB 커넥션을 반납
클라이언트와 연결 종료!
스레드 종료!

```

`eomcs-java-project-client`프로젝트의 `ClientApp`을 실행한다.

```
명령> /photoboard/list
  1, 수업 오리엔테이션           , 2018-11-14, 0, 1
  2, 1차 과제 발표            , 2018-11-14, 0, 1
  3, null                , 2018-11-14, 0, 2
  4, 과제 발표회              , 2018-11-14, 0, 3
  7, test..ok            , 2018-11-15, 0, 1
  8, test...test...test  , 2018-11-15, 0, 1

명령> /photoboard/update
번호?
8
제목(test...test...test)?
okok
사진 파일:
> f1.png
> f2.png
> f3.png

사진은 일부만 변경할 수 없습니다.
전체를 새로 등록해야 합니다.
사진을 변경하시겠습니까?(y/N)
y
최소 한 개의 사진 파일을 등록해야 합니다.
파일명 입력 없이 그냥 엔터를 치면 파일 추가를 마칩니다.
사진 파일?
a1.png
사진 파일?
a2.png
사진 파일?
a3.png
사진 파일?
a4.png
사진 파일?

사진을 변경했습니다.

명령> 

```

## 실습 소스

- src/main/java/com/eomcs/sql/ConnectionPool.java 추가
- src/main/java/com/eomcs/sql/DataSource.java 변경
- src/main/java/com/eomcs/sql/TxConnection.java 변경
- src/main/java/com/eomcs/lms/DataLoaderListener.java 변경
