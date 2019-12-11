# eomcs-java-project-4.5-client

데이터 관리를 전문 프로그램인 DBMS에게 맡기기

- 오픈 소스 DBMS `MariaDB`에 데이터베이스와 사용자 추가하는 방법
- 테이블 생성과 예제 데이터 입력하기
- JDBC API를 활용하여 DBMS에 데이터를 입력, 조회, 변경, 삭제하는 방법
- JDBC 프로그래밍 코드를 클래스로 캡슐화하기

## 프로젝트 - 수업관리 시스템  

### 과제: MariaDB에 애플리케이션에서 사용할 사용자와 데이터베이스를 추가한다.

MariaDB 에 연결하기 
> $ mysql -u 사용자아이디 -p
```
$ mysql -u root -p
Enter password: 암호입력
...

MariaDB [(none)]>
```

사용자 생성하기
> CREATE USER '사용자아이디'@'서버주소' IDENTIFIED BY '암호';

```
MariaDB [(none)]> CREATE USER 'eomcs'@'localhost' IDENTIFIED BY '1111';
```

데이터베이스 생성하기
> CREATE DATABASE 데이터베이스명 CHARACTER SET utf8 COLLATE utf8_general_ci;
```
MariaDB [(none)]> CREATE DATABASE eomcs
  CHARACTER SET utf8
  COLLATE utf8_general_ci;
```

사용자에게 DB 사용 권한을 부여하기
> GRANT ALL ON 데이터베이스명.* TO '사용자아이디'@'서버주소';
```
GRANT ALL ON eomcs.* TO 'eomcs'@'localhost';
```

MariaDB에 `eomcs` 사용자 아이디로 다시 접속하기
```
$ mysql -u eomcs -p
Enter password: 1111
...

MariaDB [(none)]>
```

`eomcs` 아이디로 사용할 수 있는 데이터베이스 목록 보기
```
MariaDB [(none)]> show databases;
+--------------------+
| Database           |
+--------------------+
| eomcs              |
| information_schema |
| test               |
+--------------------+
3 rows in set (0.000 sec)

MariaDB [(none)]> 
```



### 과제: 애플리케이션에서 사용할 테이블과 예제 데이터를 준비하라.

`eomcs` 아이디로 MariaDB에 접속한 후 기본으로 사용할 데이터베이스를 `eomcs`로 설정하기
```
MariaDB [(none)]> use eomcs;
...

Database changed
MariaDB [eomcs]> 
``` 

애플리케이션에서 사용할 테이블 생성하기. 
```
다음 파일의 내용을 복사하여 MariaDB 명령창에 붙여 넣고 실행한다.
eomcs-java-project    (Git 저장소)
    /eomcs-dbmodel    
        /model
            /ddl.sql  (테이블 정의 SQL 문이 들어 있는 파일)
```

생성된 테이블에 예제 데이터 입력하기. 
```
다음 파일의 내용을 복사하여 MariaDB 명령창에 붙여 넣고 실행한다.
eomcs-java-project    (Git 저장소)
    /eomcs-dbmodel    
        /model
            /data.sql  (INSERT SQL 문이 들어 있는 파일)
```

### 과제: 프로젝트에 `MariaDB` JDBC 드라이버를 추가하라.

- build.gradle
    - 의존 라이브러리에 MariaDB JDBC Driver 정보를 추가한다.
    - 예를 들면, `mvnrepository.com`에서 키워드 `mariadb jdbc`로 검색하면 **MariaDB Java Client** 라이브러리를 찾을 수 있다.
    - 최신 버전의 라이브러리 정보를 가져오면 된다.

build.gradle 파일
```
plugins {
    id 'java'
    id 'application'
    id 'eclipse'
}

tasks.withType(JavaCompile) {
    options.encoding = 'UTF-8'
    sourceCompatibility = '1.8'
    targetCompatibility = '1.8'
}

mainClassName = 'App'

dependencies {
    compile group: 'org.mariadb.jdbc', name: 'mariadb-java-client', version: '2.3.0'
    compile 'com.google.guava:guava:23.0'
    testCompile 'junit:junit:4.12'
}

repositories {
    jcenter()
}
```

`gradle`을 실행하여 이클립스 설정 파일을 갱신하기
```
$ gradle eclipse
```

이클립스 워크스페이스의 프로젝트를 갱신하기
> 이클립스에서도 프로젝트에 `Refresh`를 수행해야 라이브러리가 적용된다.

### 과제: 수업 데이터를 데이터베이스를 사용하여 관리하라.

- Lesson.java
    - DB의 `LESSON` 테이블에서 수업을 생성한 `회원 번호`가 필수 컬럼이다.
    - 이 값을 저장할 필드와 셋터/겟터를 추가한다.
    - toString()을 갱신한다.
- Board.java
    - DB의 `BOARD` 테이블에서 게시글을 등록한 `회원 번호`가 필수 컬럼이다.
    - 또한 어느 수업에 대한 게시글인지를 가리키는 `수업 번호`가 필수 컬럼이다.
    - 이 값을 저장할 필드와 셋터/겟터를 추가한다.
    - toString()을 갱신한다.
- `com.eomcs.lms.proxy` ==> `com.eomcs.lms.dao` 패키지 이름 변경
    - `proxy` 패키지명 대신에 데이터를 처리하는 객체를 두는 패키지라는 의미로 `dao` 라는 이름으로 변경한다.
- `LessonDaoProxy.java` ==> `LessonDao.java` 이름 변경
    - JDBC API를 사용하여 DB 서버에 데이터를 보관하고 꺼낸다.
    - 클래스 이름에서 `Proxy`를 뺀다.
- `MemberDaoProxy.java` ==> `MemberDao.java` 이름 변경
    - JDBC API를 사용하여 DB 서버에 데이터를 보관하고 꺼낸다.
    - 클래스 이름에서 `Proxy`를 뺀다.
- `BoardDaoProxy.java` ==> `BoardDao.java` 이름 변경
    - JDBC API를 사용하여 DB 서버에 데이터를 보관하고 꺼낸다.
    - 클래스 이름에서 `Proxy`를 뺀다.
- DataLoaderListener.java
    - 애플리케이션이 시작할 때 MariaDB 데이터베이스 서버에 연결한다.
    - 애플리케이션이 종료할 때 MariaDB 데이터베이스 서버와 연결을 끊는다.
    - DAO 객체를 생성할 때 Connection 객체를 파라미터로 제공한다.
- LessonListCommand.java
    - DAO의 리턴 값이 Lesson[] 배열에서 List 객체로 바뀌었다.
    - 그에 따라 이 클래스의 코드도 변경한다.
- LessonAddCommand.java
    - DB에서 수업 번호를 자동증가시키기 때문에 `번호` 입력 항목을 제외한다.
    - `강사` 번호가 필수 컬럼이기 때문에 입력 항목으로 추가한다.
- LessonDetailCommand.java
    - `강사` 번호 출력을 추가한다.
- LessonUpdateCommand.java
    - `강사` 번호를 변경할 수 있도록 변경 항목으로 추가한다.
- MemberListCommand.java
    - DAO의 리턴 값이 Member[] 배열에서 List 객체로 바뀌었다.
    - 그에 따라 이 클래스의 코드도 변경한다.
- MemberAddCommand.java
    - DB에서 수업 번호를 자동증가시키기 때문에 `번호` 입력 항목을 제외한다.
    - `등록일`은 DB에서 기본 값으로 현재 날짜가 자동으로 입력된다. 코드에서 제외한다.
- BoardListCommand.java
    - DAO의 리턴 값이 Board[] 배열에서 List 객체로 바뀌었다.
    - 그에 따라 이 클래스의 코드도 변경한다.
    - `작성자 번호`와 `수업 번호`도 출력한다.
 - BoardAddCommand.java
    - DB에서 수업 번호를 자동증가시키기 때문에 `번호` 입력 항목을 제외한다.
    - `등록일`과 `조회수`는 DB에 설정된 기본 값으로 자동 입력된다. 따라서 코드에서 제외한다.
    - `작성자`와 `수업`은 입력 항목으로 추가한다. 
- BoardDetailCommand.java
    - `조회수`, `작성자`, `수업`을 출력에 추가한다.  


#### 실행 결과

클라이언트 애플리케이션의 `App`을 실행한다. 실행은 이전 버전과 같다.
```
이전과 같다.
```


## 실습 소스

- src/main/java/com/eomcs/lms/domain/Lesson.java 변경
- src/main/java/com/eomcs/lms/domain/Board.java 변경
- src/main/java/com/eomcs/lms/proxy 이름 변경
    - `dao`로 패키지 이름 변경
- src/main/java/com/eomcs/lms/proxy/LessonDaoProxy.java 이름 변경
    - `LessonDao.java`로 이름 변경 및 내용 변경
- src/main/java/com/eomcs/lms/proxy/MemberDaoProxy.java 이름 변경
    - `MemberDao.java`로 이름 변경 및 내용 변경
- src/main/java/com/eomcs/lms/proxy/BoardDaoProxy.java 이름 변경
    - `BoardDao.java`로 이름 변경 및 내용 변경
- src/main/java/com/eomcs/lms/DataLoaderListener.java 변경
- src/main/java/com/eomcs/lms/handler/LessonListCommand.java 변경
- src/main/java/com/eomcs/lms/handler/LessonAddCommand.java 변경
- src/main/java/com/eomcs/lms/handler/LessonDetailCommand.java 변경
- src/main/java/com/eomcs/lms/handler/LessonUpdateCommand.java 변경
- src/main/java/com/eomcs/lms/handler/MemberListCommand.java 변경
- src/main/java/com/eomcs/lms/handler/MemberAddCommand.java 변경
- src/main/java/com/eomcs/lms/handler/BoardListCommand.java 변경
- src/main/java/com/eomcs/lms/handler/BoardAddCommand.java 변경
- src/main/java/com/eomcs/lms/handler/BoardDetailCommand.java 변경
