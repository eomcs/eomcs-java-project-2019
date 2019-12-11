# eomcs-java-project-4.8-server

여러 스레드가 동시에 같은 커넥션을 사용했을 때 발생되는 문제와 해결책

- 여러 스레드가 동시에 같은 DB 커넥션을 사용할 때 발생하는 문제
- DB 커넥션 생성기 활용하여 각각의 SQL 작업을 별개의 DB 커넥션으로 실행하기

## 프로젝트 - 수업관리 시스템  

클라이언트 요청이 들어오면 서버는 스레드풀에 작업을 맡긴다. 스레드풀은 큐에 들어있는 작업을 순차적으로 꺼내 스레드에게  할당한다. 스레드는 자신이 맡은 작업을 처리한다. 

만약 여러 스레드 중 하나가 데이터 변경 작업을 완료한 후에 서버에 `commit` 명령을 보낸다면, 같은 커넥션으로 작업 중인 다른 스레드의  데이터 변경 작업들도 그대로 `commit` 된다. 이것이 커넥션을 동시에 사용할 때의 문제점이다.

### ver 4.8.0 - DB 커넥션을 공유하지 말라. 작업할 때마다 새 DB 커넥션을 사용하라.

- DataSource.java 
    - `com.eomcs.lms.sql` 패키지를 생성한다.
    - 이 패키지에 `DataSource` 클래스를 정의한다.
    - DB 커넥션을 만들어주는 역할을 한다.
- BoardDao.java
    - 생성자에서 DB 커넥션을 받는 대신에 DB 커넥션을 만들어주는 `DataSource` 객체를 받는다.
    - 메서드에서 SQL을 실행할 때 DataSource에서 DB 커넥션을 얻어서 사용한다.
- LessonDao.java
    - 생성자에서 DB 커넥션을 받는 대신에 DB 커넥션을 만들어주는 `DataSource` 객체를 받는다.
    - 메서드에서 SQL을 실행할 때 DataSource에서 DB 커넥션을 얻어서 사용한다.
- MemberDao.java
    - 생성자에서 DB 커넥션을 받는 대신에 DB 커넥션을 만들어주는 `DataSource` 객체를 받는다.
    - 메서드에서 SQL을 실행할 때 DataSource에서 DB 커넥션을 얻어서 사용한다.
- PhotoBoardDao.java
    - 생성자에서 DB 커넥션을 받는 대신에 DB 커넥션을 만들어주는 `DataSource` 객체를 받는다.
    - 메서드에서 SQL을 실행할 때 DataSource에서 DB 커넥션을 얻어서 사용한다.
- PhotoFileDao.java
    - 생성자에서 DB 커넥션을 받는 대신에 DB 커넥션을 만들어주는 `DataSource` 객체를 받는다.
    - 메서드에서 SQL을 실행할 때 DataSource에서 DB 커넥션을 얻어서 사용한다.    
- DataLoaderListener.java
    - DB 커넥션 스태틱 필드는 제거한다.
    - DB 커넥션을 닫는 코드도 제거한다.
    - DB 커넥션을 만드는 대신 `DataSource` 객체를 만들어 DAO에 주입한다.
- PhotoBoardAddCommand.java
    - 일단 트랜잭션 처리 코드를 제거한다.
- PhotoBoardUpdateCommand.java
    - 일단 트랜잭션 처리 코드를 제거한다.
- PhotoBoardDeleteCommand.java
    - 일단 트랜잭션 처리 코드를 제거한다.    

#### 실행 결과

`eomcs-java-project-server` 프로젝트의 `App` 클래스를 실행한다.
```
DataLoaderListener.contextInitialized() 실행!
서버 실행!
클라이언트와 연결되었음.
스레드 생성됨!
스레드 실행... - pool-1-thread-1
명령어: /photoboard/list
새 DB 커넥션을 생성한다.
클라이언트와 연결 종료!
스레드 종료!
클라이언트와 연결되었음.
스레드 생성됨!
스레드 실행... - pool-1-thread-1
명령어: /photoboard/add
새 DB 커넥션을 생성한다.
새 DB 커넥션을 생성한다.
새 DB 커넥션을 생성한다.
새 DB 커넥션을 생성한다.
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

명령> /photoboard/add
제목?
test..ok
수업?
1
최소 한 개의 사진 파일을 등록해야 합니다.
파일명 입력 없이 그냥 엔터를 치면 파일 추가를 마칩니다.
사진 파일?
a1.jpeg
사진 파일?
a2.jpeg
사진 파일?
a3.jpeg
사진 파일?

사진을 저장했습니다.

명령> 
```

`/photoboard/add` 명령을 처리할 때 서버의 출력 결과를 보면 `insert` 할 때마다 DB 커넥션을 새로 만들어 사용한다. 문제는 같은 스레드에서 작업하는데도 불구하고 `insert` 를 실행할 때마다 다른 커넥션을 사용하기 때문에 같은 트랜잭션으로 묶을 수 없다는 것이다. 그래서 다음과 같이 사진 게시글을 등록하는 중에 사진 파일 입력에서 오류가 발생하더라도 이전에 입력된 값을 그대로 유지하는 상황이 재발되었다.

```
명령> /photoboard/add
제목?
test...100
수업?
1
최소 한 개의 사진 파일을 등록해야 합니다.
파일명 입력 없이 그냥 엔터를 치면 파일 추가를 마칩니다.
사진 파일?
a.jpeg
사진 파일?
b.jpeg
사진 파일?
012345678900123456789001234567890012345678900123456789001234567890012345678900123456789001234567890012345678900123456789001234567890012345678900123456789001234567890012345678900123456789001234567890012345678900123456789001234567890012345678900123456789001234567890012345678900123456789001234567890012345678900123456789001234567890
사진 파일?

java.sql.SQLDataException: (conn=16) Data too long for column 'PATH' at row 1 : (conn=16) Data too long for column 'PATH' at row 1

명령> /photoboard/list
  1, 수업 오리엔테이션           , 2018-11-14, 0, 1
  2, 1차 과제 발표            , 2018-11-14, 0, 1
  3, null                , 2018-11-14, 0, 2
  4, 과제 발표회              , 2018-11-14, 0, 3
  7, test..ok            , 2018-11-15, 0, 1
  8, test...100          , 2018-11-15, 0, 1

명령> /photoboard/detail
번호?
8
제목: test...100
작성일: 2018-11-15
조회수: 0
수업: 1
사진 파일:
> a.jpeg
> b.jpeg

명령> 
```


## 실습 소스

- src/main/java/com/eomcs/sql/DataSource.java 추가
- src/main/java/com/eomcs/lms/dao/BoardDao.java 변경
- src/main/java/com/eomcs/lms/dao/LessonDao.java 변경
- src/main/java/com/eomcs/lms/dao/MemberDao.java 변경
- src/main/java/com/eomcs/lms/dao/PhotoBoardDao.java 변경
- src/main/java/com/eomcs/lms/dao/PhotoFileDao.java 변경
- src/main/java/com/eomcs/lms/DataLoaderListener.java 변경
- src/main/java/com/eomcs/lms/handler/PhotoBoardAddCommand.java 변경
- src/main/java/com/eomcs/lms/handler/PhotoBoardUpdateCommand.java 변경
- src/main/java/com/eomcs/lms/handler/PhotoBoardDeleteCommand.java 변경
