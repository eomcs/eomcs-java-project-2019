# eomcs-java-project-6.0-server

Spring IoC 컨테이너와 MyBatis 연동하기

- MyBatis를 Spring IoC 컨테이너와 연동하는 방법


## 프로젝트 - 수업관리 시스템  

MyBatis를 Spring IoC 컨테이너와 연동하면 좀 더 편리하게 MyBatis를 다룰 수 있다.

### ver 6.0.0 - MyBatis를 Spring IoC 컨테이너와 연동하라.

#### 1단계) MyBatis-Spring 연동 라이브러리를 가져온다.

- MyBatis-Spring 연동 라이브러리 정보 찾기
    - `mvnrepository.com`에서 `mybatis spring` 이름으로 검색하면 라이브러리 정보를 알 수 있다.
- 빌드 설정 파일에 의존 라이브러리 정보 추가하기
    - `build.gradle` 파일에 라이브러리 정보를 추가한다.
    - 예) `compile group: 'org.mybatis', name: 'mybatis-spring', version: '1.3.2'`
- 라이브러리 파일을 프로젝트로 가져오기
    - Gradle 빌드 도구를 사용하여 라이브러리를 가져온다.
    - `$ gradle eclipse` 명령을 실행하면, 의존 라이브러리를 가져온다. 
    - 또한 Eclipse의 CLASSPATH 정보를 갱신한다.
    - 명령어를 실행한 후 이클립스에서 프로젝트를 갱신해야 한다.

#### 2단계) Spring IoC 설정 클래스에서 DataSource 객체를 준비한다.

- DataSource 구현체를 준비하기 위한 라이브러리를 가져온다.
    - Apache `common-dbcp2` 라이브러리를 가져온다.
    - 설정하는 방법은 위의 MyBatis-Spring 연동 라이브러리와 같다.
- jdbc.properties
    - 다른 프로퍼티 이름과 구분하기 위해 jdbc 관련 프로퍼티들은 이름 앞에 접두사 `jdbc.`를 붙인다. 
- AppConfig.java
    - 클래스 선언부에 `@PropertySource` 애노테이션을 붙여 `jdbc.properties` 파일의 내용을 로딩한다.
    - 프로퍼티 파일의 내용을 읽을 때 사용할 `Environment` 객체를 주입 받는다.
    - MyBatis에서 사용할 `DataSource` 객체를 생성한다.

#### 3단계) MyBatis-Spring 연동 라이브러리를 사용하여 SqlSessionFactory를 생성한다.

- AppConfig.java
    - MyBatis-Spring 라이브러리에서 제공하는 `SqlSessionFactoryBean` 클래스를 이용하여 `SqlSessionFactory` 객체를 만든다.

#### 4단계) Spring IoC 컨테이너 기반 트랜잭션 관리자를 준비한다.

- Spring에서 사용할 트랜잭션 관련 라이브러리를 가져온다.
    - `spring-jdbc` 라이브러리를 가져온다.
    - 설정하는 방법은 위의 MyBatis-Spring 연동 라이브러리와 같다.
- AppConfig.java
    - 트랜잭션을 관리할 객체를 생성한다.
    - 기존에 이 프로젝트에서 만든 트랜잭션 관리자는 제거한다.
    - 클래스 선언부에 `@EnableTransactionManagement` 애노테이셔을 붙여 트랜잭션 관리자를 활성화시킨다.

#### 5단계) MyBatis-Spring 연동 라이브러리에서 제공하는 DAO 자동 생성기를 준비한다.

- AppConfig.java
    - 클래스 선언부에 `@MapperScan` 애노테이션을 붙인다.
    - `@MapperScan` 애노테이션에 DAO 인터페이스가 들어 있는 패키지를 알려준다.
    - 기존의 DAO 생성 메서드를 제거한다.
- com.eomcs.sql 패키지 제거
    - DAO 생성과 관련된 클래스와 트랜잭션 관리와 관련된 클래스를 삭제한다.
    - `DaoInvocationHandler`, `MybatisDaoFactory`, `SqlSessionFactoryProxy`, `SqlSessionProxy`, `TransactionManager` 클래스를 삭제한다.
- SQL 매퍼 파일의 namespace 속성 값을 DAO 인터페이스의 전체 이름과 같게 맞춘다.
    - 예) `<mapper namespace="com.eomcs.lms.dao.BoardDao">`
- mybatis-config.xml
    - MyBatis 객체는 Spring IoC 컨테이너에서 관리하기 때문에 더이상 MyBatis 설정 파일이 필요 없다.
    - 삭제한다.


#### 6단계) PhotoBoardHandler 클래스는 Spring에서 제공하는 트랜잭션 관리자를 사용한다.

- PhotoBoardHandler.java
    - 기존의 트랜잭션 관리자 클래스는 삭제되었다. Spring의 트랜잭션 관리자로 교체한다.


##### 실습 결과

서버 시작 후 `eomcs-java-project-client`프로젝트의 `ClientApp`을 실행한다.
```
이전과 같다.
```

## 실습 소스

- build.gradle 변경
- com/eomcs/lms/conf/jdbc.properties 변경
- com/eomcs/lms/AppConfig.java 변경 
- com/eomcs/sql 패키지 삭제
    - DaoInvocationHandler.java 삭제
    - TransactionManager.java 삭제
    - SqlSessionProxy.java 삭제
    - SqlSessionFactoryProxy.java 삭제
    - MybatisDaoFactory.java 삭제
- com/eomcs/lms/mapper/BoardMapper.xml 변경
- com/eomcs/lms/mapper/MemberMapper.xml 변경
- com/eomcs/lms/mapper/LessonMapper.xml 변경
- com/eomcs/lms/mapper/PhotoBoardMapper.xml 변경
- com/eomcs/lms/mapper/PhotoFileMapper.xml 변경
- com/eomcs/lms/conf/mybatis-config.xml 삭제
- com/eomcs/lms/handler/PhotoBoardHandler.java 변경