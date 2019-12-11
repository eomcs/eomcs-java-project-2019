# eomcs-java-project-5.2-server

DB 프로그래밍 더 쉽고 간단히 하는 방법

- MyBatis SQL 맵퍼 프레임워크의 특징과 사용법


## 프로젝트 - 수업관리 시스템  

퍼시스턴스 프레임워크인 `MyBatis`를 사용하여 JDBC 프로그래밍의 번거로움에서 벗어나자. MyBatis는 자바 소스코드에서 SQL을 분리하여 관리한다. 자바 코드와 SQL이 분리되어 있어서 코드를 읽기 쉽고 유지보수하기 편한다. 또한 JDBC 프로그래밍 코드를 캡슐화하였기 때문에 유사한 코드를 반복 작성할 필요가 없다.

### ver 5.2.0 - `LessonDAO`에 `MyBatis` 퍼시스턴스 프레임워크를 적용하라.

#### 1단계) 프로젝트에 MyBatis 라이브러리를 추가한다.

- build.gradle   
    - 의존 라이브러리 블록에서 `mybatis` 라이브러리를 등록한다.
- gradle을 이용하여 eclipse 설정 파일을 갱신한다.
    - `$ gradle eclipse`
- 이클립스에서 프로젝트를 갱신한다.

#### 2단계) `MyBatis` 설정 파일을 준비한다.

- com/eomcs/lms/conf/mybatis-config.xml
    - `MyBatis` 설정 파일이다.
    - DBMS 서버의 접속 정보를 갖고 있는 jdbc.properties 파일의 경로를 등록한다.
    - DBMS 서버 정보를 설정한다.
    - DB 커넥션 풀을 설정한다.
- com/eomcs/lms/conf/jdbc.properties
    - `MyBatis` 설정 파일에서 참고할 DBMS 접속 정보를 등록한다.

#### 3단계) `LessonDao` 클래스의 list() 메서드에서 사용할 SQL을 분리한다.

- com/eomcs/lms/mapper/LessonMapper.xml (LessonMapper.xml.01)
    - `LessonDao`의 list()에서 사용할 SQL을 이 파일에 둔다.
- mybatis-config.xml
    - 설정 파일에 SQL 매퍼 파일 `LessonMapper.xml`을 등록한다.
  
#### 4단계) MyBatis 관련 자바 객체를 준비한다.

- DataLoaderListener.java
    - `SqlSessionFactory` 객체를 생성한다.
    - `LessonDao`에 `DataSource` 대신에 `SqlSessionFactory`를 주입한다.
  
#### 5단계) LessonDao를 변경하여 list()에 MyBatis를 적용한다.

- LessonDao.java
    - 생성자에서 `DataSource`를 받는 대신에 `SqlSessionFactory`를 받는다.
    - 일단 DataSource 필드는 그대로 두어 코드에서 오류가 발생하지 않게 한다.
    - `list()`에 MyBatis를 적용한다.

##### 실습 결과

`eomcs-java-project-server` 프로젝트의 `App` 클래스를 실행한다.
```
이전과 같다.
```

`eomcs-java-project-client`프로젝트의 `ClientApp`을 실행한다.
```
명령> /lesson/list
  0, 자바 기초 프로그래밍    ,       null ~       null,    0
  0, 자바 고급 프로그래밍    ,       null ~       null,    0
  0, 자바 웹 프로그래밍     ,       null ~       null,    0
  0, IoT 프로그래밍      ,       null ~       null,    0
  0, C# 프로그래밍       ,       null ~       null,    0

명령> 
```

#### 6단계) 출력 결과에서 번호, 시작일, 종료일, 총수업시간 값이 null인 것을 해결한다.

Mybatis는 DB에서 가져온 결과를 자바 객체에 넣을 때 컬럼명과 일치하는 객체의 프로퍼티를 찾는다. 컬럼명과 일치하는 프로퍼티를 찾지 못하면 해당 컬럼의 값은 객체에 넣을 수 없다. 그래서 컬럼명과 프로퍼티명을 일치시켜야 한다.

- LessonMapper.xml
    - 객체의 프로퍼티명과 같은 이름으로 컬럼에 별명을 붙인다.

##### 실습 결과

`eomcs-java-project-client`프로젝트의 `ClientApp`을 실행한다.
```
명령> /lesson/list
  1, 자바 기초 프로그래밍    , 2019-01-02 ~ 2019-01-15,   80
  2, 자바 고급 프로그래밍    , 2019-01-14 ~ 2019-01-25,   80
  3, 자바 웹 프로그래밍     , 2019-01-21 ~ 2019-02-01,   80
  4, IoT 프로그래밍      , 2019-02-04 ~ 2019-03-04,   80
  5, C# 프로그래밍       , 2019-02-04 ~ 2019-02-15,   40

명령> 
```

#### 7단계) LessonDao의 detail()에 MyBatis를 적용한다.

- LessonDao.java
    - `detail()`에 MyBatis를 적용한다.
- LessonMapper.xml
    - LessonDao의 `detail()`에서 사용하는 SQL을 추출하여 이 파일로 옮긴다.

##### 실습 결과

`eomcs-java-project-client`프로젝트의 `ClientApp`을 실행한다.
```
명령> /lesson/detail
번호?
5
수업명: C# 프로그래밍
설명: C# 기초 프로그래밍 과정
기간: 2019-02-04 ~ 2019-02-15
총수업시간: 40
일수업시간: 4
강사: 2

명령> 
```

#### 8단계) LessonDao의 add()에 MyBatis를 적용한다.

- LessonDao.java
    - `add()`에 MyBatis를 적용한다.
- LessonMapper.xml
    - LessonDao의 `add()`에서 사용하는 SQL을 추출하여 이 파일로 옮긴다.

##### 실습 결과

`eomcs-java-project-client`프로젝트의 `ClientApp`을 실행한다.
```
명령> /lesson/add
수업명?
java programming
설명?
java 
시작일?
2019-1-1
종료일?
2019-2-2
총수업시간?
100
일수업시간?
8
강사?
1
수업을 저장했습니다.

명령> /lesson/list
  1, 자바 기초 프로그래밍    , 2019-01-02 ~ 2019-01-15,   80
  2, 자바 고급 프로그래밍    , 2019-01-14 ~ 2019-01-25,   80
  3, 자바 웹 프로그래밍     , 2019-01-21 ~ 2019-02-01,   80
  4, IoT 프로그래밍      , 2019-02-04 ~ 2019-03-04,   80
  5, C# 프로그래밍       , 2019-02-04 ~ 2019-02-15,   40

명령> 
```

SqlSession은 자동 커밋이 false로 되어 있다. 그래서 insert를 성공적으로 실행했음에도 불구하고 실제 DB에는 반영되지 않았다. 해결 방법은 insert, udpate, delete을 수행한 후에는 commit()을 호출해야 한다. 또는 SqlSession 객체를 만들 때 Auto Commit을 true로 설정하면 된다.

#### 9단계) insert를 수행한 후 commit 수행한다.

- LessonDao.java
    - `add()`에서 `insert` 후에 `commit()`을 호출한다.

##### 실습 결과

`eomcs-java-project-client`프로젝트의 `ClientApp`을 실행한다.
```
명령> /lesson/add
수업명?
java programming
설명?
java
시작일?
2019-1-1
종료일?
2019-2-2
총수업시간?
100
일수업시간?
8
강사?
1
수업을 저장했습니다.

명령> /lesson/list
  1, 자바 기초 프로그래밍    , 2019-01-02 ~ 2019-01-15,   80
  2, 자바 고급 프로그래밍    , 2019-01-14 ~ 2019-01-25,   80
  3, 자바 웹 프로그래밍     , 2019-01-21 ~ 2019-02-01,   80
  4, IoT 프로그래밍      , 2019-02-04 ~ 2019-03-04,   80
  5, C# 프로그래밍       , 2019-02-04 ~ 2019-02-15,   40
 10, java programming, 2019-01-01 ~ 2019-02-02,  100

명령> 
```

#### 10단계) LessonDao의 update(), delete()에 MyBatis를 적용한다.

- LessonDao.java
    - `udpate()`, `delete()`에도 MyBatis를 적용한다.
- LessonMapper.xml
    - LessonDao의 `update()`, `delete()`에서 사용하는 SQL을 추출하여 이 파일에 둔다.

##### 실습 결과

`eomcs-java-project-client`프로젝트의 `ClientApp`을 실행한다.
```
명령> /lesson/update
번호?
10
수업명(java programming)?
java basic programming
설명(java)?
자바 기초 프로그래밍 교육 과정
시작일(2019-01-01)?
2019-1-1
종료일(2019-02-02)?
2019-2-2
총수업시간(100)?
200
일수업시간(8)?
8
강사(1)?
2
수업을 변경했습니다.

명령> /lesson/detail
번호?
10
수업명: java basic programming
설명: 자바 기초 프로그래밍 교육 과정
기간: 2019-01-01 ~ 2019-02-02
총수업시간: 200
일수업시간: 8
강사: 2

명령> /lesson/list
  1, 자바 기초 프로그래밍    , 2019-01-02 ~ 2019-01-15,   80
  2, 자바 고급 프로그래밍    , 2019-01-14 ~ 2019-01-25,   80
  3, 자바 웹 프로그래밍     , 2019-01-21 ~ 2019-02-01,   80
  4, IoT 프로그래밍      , 2019-02-04 ~ 2019-03-04,   80
  5, C# 프로그래밍       , 2019-02-04 ~ 2019-02-15,   40
 10, java basic programming, 2019-01-01 ~ 2019-02-02,  200

명령> /lesson/delete
번호?
10
수업을 삭제했습니다.

명령> /lesson/list
  1, 자바 기초 프로그래밍    , 2019-01-02 ~ 2019-01-15,   80
  2, 자바 고급 프로그래밍    , 2019-01-14 ~ 2019-01-25,   80
  3, 자바 웹 프로그래밍     , 2019-01-21 ~ 2019-02-01,   80
  4, IoT 프로그래밍      , 2019-02-04 ~ 2019-03-04,   80
  5, C# 프로그래밍       , 2019-02-04 ~ 2019-02-15,   40

명령> 
```

### ver 5.2.1 - `MemberDao`에 `MyBatis` 퍼시스턴스 프레임워크를 적용하라.

#### 1단계) `MemberDao` 클래스에서 SQL을 분리한다.

- com/eomcs/lms/mapper/MemberMapper.xml
    - `MemberDao`에서 사용하는 SQL을 이 파일로 옮긴다.
- mybatis-config.xml
    - MyBatis 설정 파일에 SQL 매퍼 파일 `MemberMapper.xml`을 등록한다.

#### 2단계) MemberDao에 MyBatis를 적용한다.

- MemberDao.java
    - JDBC API 대신 MyBatis를 적용한다.
- DataLoaderListener.java
    - `MemberDao`에 `DataSource` 대신에 `SqlSessionFactory`를 주입한다.

##### 실습 결과

`eomcs-java-project-client`프로젝트의 `ClientApp`을 실행한다.
```
이전과 같다.
```

### ver 5.2.2 - `BoardDao`에 `MyBatis` 퍼시스턴스 프레임워크를 적용하라.

#### 1단계) `BoardDao` 클래스에서 SQL을 분리한다.

- com/eomcs/lms/mapper/BoardMapper.xml
    - `BoardDao`에서 사용하는 SQL을 이 파일로 옮긴다.
- mybatis-config.xml
    - MyBatis 설정 파일에 SQL 매퍼 파일 `BoardMapper.xml`을 등록한다.

#### 2단계) BoardDao MyBatis를 적용한다.

- BoardDao.java
    - JDBC API 대신 MyBatis를 적용한다.
- DataLoaderListener.java
    - `BoardDao`에 `DataSource` 대신에 `SqlSessionFactory`를 주입한다.

##### 실습 결과

`eomcs-java-project-client`프로젝트의 `ClientApp`을 실행한다.
```
이전과 같다.
```

### ver 5.2.3 - `PhotoBoardDao`와 `PhotoFileDao`에 `MyBatis` 퍼시스턴스 프레임워크를 적용하라.

#### 1단계) `PhotoBoardDao` 클래스와 `PhotoFileDao`에서 SQL을 분리한다.

- com/eomcs/lms/mapper/PhotoBoardMapper.xml
    - `PhotoBoardDao`에서 사용하는 SQL을 이 파일로 옮긴다.
- com/eomcs/lms/mapper/PhotoFileMapper.xml
    - `PhotoFileDao`에서 사용하는 SQL을 이 파일로 옮긴다.    
- mybatis-config.xml
    - MyBatis 설정 파일에 SQL 매퍼 파일 `PhotoBoardMapper.xml`과 `PhotoFileMapper.xml`을 등록한다.

#### 2단계) PhotoBoardDao와 PhotoFileDao에 MyBatis를 적용한다.

- PhotoBoardDao.java
    - JDBC API 대신 MyBatis를 적용한다.
- PhotoFileDao.java
    - JDBC API 대신 MyBatis를 적용한다.
- DataLoaderListener.java
    - `PhotoBoardDao`와 `PhotoFileDao`에 `DataSource` 대신에 `SqlSessionFactory`를 주입한다.

##### 실습 결과

`eomcs-java-project-client`프로젝트의 `ClientApp`을 실행한다.
```
명령> /photoboard/add
제목?
test...100
수업?
1
최소 한 개의 사진 파일을 등록해야 합니다.
파일명 입력 없이 그냥 엔터를 치면 파일 추가를 마칩니다.
사진 파일?
a.png
사진 파일?
c.png
사진 파일?
012345678900123456789001234567890012345678900123456789001234567890012345678900123456789001234567890012345678900123456789001234567890012345678900123456789001234567890012345678900123456789001234567890012345678900123456789001234567890012345678900123456789001234567890012345678900123456789001234567890012345678900123456789001234567890
사진 파일?

org.apache.ibatis.exceptions.PersistenceException: 
### Error updating database.  Cause: java.sql.SQLDataException: (conn=18) Data too long for column 'PATH' at row 1
### The error may involve PhotoFileMapper.add-Inline
### The error occurred while setting parameters
### SQL: INSERT INTO PHO_FILE(PATH,PNO)      VALUES(?,?)
### Cause: java.sql.SQLDataException: (conn=18) Data too long for column 'PATH' at row 1 : 
### Error updating database.  Cause: java.sql.SQLDataException: (conn=18) Data too long for column 'PATH' at row 1
### The error may involve PhotoFileMapper.add-Inline
### The error occurred while setting parameters
### SQL: INSERT INTO PHO_FILE(PATH,PNO)      VALUES(?,?)
### Cause: java.sql.SQLDataException: (conn=18) Data too long for column 'PATH' at row 1

명령> /photoboard/list
  1, 수업 오리엔테이션           , 2018-11-14, 0, 1
  2, 1차 과제 발표            , 2018-11-14, 0, 1
  3, null                , 2018-11-14, 0, 2
  4, 과제 발표회              , 2018-11-14, 0, 3
  6, 발표2                 , 2018-11-14, 0, 1
  8, test1               , 2018-11-15, 0, 2
 21, okok                , 2018-11-19, 0, 1
 22, test...100          , 2018-11-19, 0, 1

명령> /photoboard/detail
번호?
22
제목: test...100
작성일: 2018-11-19
조회수: 0
수업: 1
사진 파일:
> a.png
> c.png

```

파일 등록 도중에 파일명이 너무 길어 입력 실패했는데도 이전 입력은 그대로 유지된다. MyBatis에 대해 트랜잭션이 적용되지 않았기 때문이다. 이전에 작성한 트랜잭션 관리 코드는 MyBatis와 관계가 없다.


### ver 5.2.4 - `MyBatis`의 트랜잭션을 관리하라.

이전에 Connection 객체의 대행자를 만들어 트랜잭션을 간접적으로 관리했듯이, `Proxy` 디자인 패턴을 적용하여 SqlSession과 SqlSessionFactory의 대행자를 만들어 처리한다.

#### 1단계) SqlSession에 트랜잭션 관련 기능을 변경하기 위해 대행자 객체를 만든다.

- SqlSessionProxy.java
    - `SqlSession`의 대행자이다.
    - 트랜잭션에 소속되어 작업하는 경우에는 close()를 호출하더라도 작업을 종료하지 않도록 오버라이딩 한다.
    - `commit()/commit(boolean)`, `rollback()/rollback(boolean)` 메서드가 호출되면 트랜잭션을 종료하도록 오버라이딩 한다.
    - 생성자에서 트랜잭션 상태인지 아닌지를 지정하도록 한다.

#### 2단계) SqlSessionProxy 객체를 리턴하도록 SqlSessionFactory 객체의 대행자 객체를 만든다.
- SqlSessionFactoryProxy.java
    - 기존의 DataSource 클래스와 비슷하게 트랜잭션과 관련된 메서드를 추가한다.
    - `beginTransaction()`과 `endTransaction()` 메서드를 정의한다.

#### 3단계) 기존의 트랜잭션 관리 기능을 변경한다.
- TransactionManager.java
    - 기존에는 DataSource 객체를 다뤄서 트랜잭션을 관리했다.
    - DataSource 대신에 SqlSessionFactoryProxy를 사용해서 트랜잭션을 관리한다.
- DataSource.java
    - MyBatis에서 따로 커넥션풀을 사용하기 때문에 제거한다.
- TxConnection.java
    - MyBatis의 커넥션풀에서 커넥션 객체를 관리하기 때문에 제거한다. 
- ConnectionPool.java
    - MyBatis를 적용하여 필요없어서 제거한다.
- DataLoaderListener.java
    - MyBatis의 원래 SqlSessionFactory 대신에 현재 프로젝트에 맞게 트랜잭션 관리 기능을 최적화한 SqlSessionFactoryProxy 객체를 Dao에 주입한다.

#### 4단계) DAO 객체에서 작업을 완료한 후 commit()/rollback()을 호출하지 않는다. 

- PhotoBoardDao.java
    - insert()/update()/delete() 후에 commit()/rollback()을 호출하지 않는다.
- photoFileDao.java
    - isnert()/update()/delete() 후에 commit()/rollback()을 호출하지 않는다.
- BoardDao.java
    - 마찬가지로 데이터 변경 후에 commit()/rollback()을 호출하지 않는다.
    - 트랜잭션을 적용하지 않은 경우에는 자동 commit()이다. 
- MemberDao.java
    - 마찬가지로 데이터 변경 후에 commit()/rollback()을 호출하지 않는다.
    - 트랜잭션을 적용하지 않은 경우에는 자동 commit()이다. 
- LessonDao.java
    - 마찬가지로 데이터 변경 후에 commit()/rollback()을 호출하지 않는다.
    - 트랜잭션을 적용하지 않은 경우에는 자동 commit()이다. 

##### 실습 결과

`eomcs-java-project-client`프로젝트의 `ClientApp`을 실행한다.
```
명령> /photoboard/list
  1, 수업 오리엔테이션           , 2018-11-14, 0, 1
  2, 1차 과제 발표            , 2018-11-14, 0, 1
  3, null                , 2018-11-14, 0, 2
  4, 과제 발표회              , 2018-11-14, 0, 3
  6, 발표2                 , 2018-11-14, 0, 1
  8, test1               , 2018-11-15, 0, 2
 21, okok                , 2018-11-19, 0, 1
 22, test...100          , 2018-11-19, 0, 1
 23, test...200          , 2018-11-19, 0, 1
 24, test...300          , 2018-11-19, 0, 1
 명령> /photoboard/add
제목?
test...400
수업?
1
최소 한 개의 사진 파일을 등록해야 합니다.
파일명 입력 없이 그냥 엔터를 치면 파일 추가를 마칩니다.
사진 파일?
x.png
사진 파일?
y.png
사진 파일?
z.png
사진 파일?
012345678900123456789001234567890012345678900123456789001234567890012345678900123456789001234567890012345678900123456789001234567890012345678900123456789001234567890012345678900123456789001234567890012345678900123456789001234567890012345678900123456789001234567890012345678900123456789001234567890012345678900123456789001234567890
사진 파일?

org.apache.ibatis.exceptions.PersistenceException: 
### Error updating database.  Cause: java.sql.SQLDataException: (conn=23) Data too long for column 'PATH' at row 1
### The error may involve PhotoFileMapper.add-Inline
### The error occurred while setting parameters
### SQL: INSERT INTO PHO_FILE(PATH,PNO)      VALUES(?,?)
### Cause: java.sql.SQLDataException: (conn=23) Data too long for column 'PATH' at row 1 : 
### Error updating database.  Cause: java.sql.SQLDataException: (conn=23) Data too long for column 'PATH' at row 1
### The error may involve PhotoFileMapper.add-Inline
### The error occurred while setting parameters
### SQL: INSERT INTO PHO_FILE(PATH,PNO)      VALUES(?,?)
### Cause: java.sql.SQLDataException: (conn=23) Data too long for column 'PATH' at row 1

명령> /photoboard/list
  1, 수업 오리엔테이션           , 2018-11-14, 0, 1
  2, 1차 과제 발표            , 2018-11-14, 0, 1
  3, null                , 2018-11-14, 0, 2
  4, 과제 발표회              , 2018-11-14, 0, 3
  6, 발표2                 , 2018-11-14, 0, 1
  8, test1               , 2018-11-15, 0, 2
 21, okok                , 2018-11-19, 0, 1
 22, test...100          , 2018-11-19, 0, 1
 23, test...200          , 2018-11-19, 0, 1
 24, test...300          , 2018-11-19, 0, 1

명령> 
```

이제 이전과 달리 사진 파일을 입력하다가 오류가 발생하면 이전에 입력한 내용까지 최소된다.

## 실습 소스

- com/eomcs/sql/SqlSessionProxy.java 추가
- com/eomcs/sql/SqlSessionFactoryProxy.java 추가
- com/eomcs/sql/TransactionManager.java 변경
- com/eomcs/sql/DataSource.java 삭제
- com/eomcs/sql/TxConnection.java 삭제
- com/eomcs/lms/DataLoaderListener.java 변경
- com/eomcs/lms/dao/PhotoBoardDao.java 변경
- com/eomcs/lms/dao/PhotoFileDao.java 변경
- com/eomcs/lms/dao/BoardDao.java 변경
- com/eomcs/lms/dao/MemberDao.java 변경
- com/eomcs/lms/dao/LessonDao.java 변경
