# eomcs-java-project-5.4-server

DAO 구현체를 자동으로 만들기

- 자바에서 제공하는 Proxy 생성기를 활용하여 DAO 구현체를 만드는 방법
- `MyBatis`의 `SqlSession` 객체를 이용하여 DAO 구현체를 만드는 방법

## 프로젝트 - 수업관리 시스템  

자바에서 제공하는 Proxy 생성기를 이용하면 DAO 구현체를 자동 생성할 수 있어 비슷한 코드 반복적으로 작성해야 하는 번거로움을 피할 수 있다.

### ver 5.4.0 - 자바 프록시 생성기를 이용하여 DAO 구현체를 자동으로 생성한다.

#### 1단계) DAO 구현체 생성을 지원하는 클래스를 준비한다.
- DaoInvocationHandler.java
    - `Proxy` 객체의 메서드가 호출되었을 때 실제 이 객체가 작업을 수행한다.
- MybatisDaoFactory.java
    - 특정 DAO 인터페이스를 구현한 객체를 생성한다.

#### 2단계) `BoardDao`에 적용한다.
- BoardDao.java
    - 클래스를 인터페이스로 전환한다.
- BoardMapper.xml
    - 매퍼의 네임스페이스 이름을 인터페이스 이름으로 변경한다.
- DataLoaderListener.java
    - `MybatisDaoFactory` 객체를 이용하여 `BoardDAO` 객체를 준비한다.

##### 실습 결과

`eomcs-java-project-client`프로젝트의 `ClientApp`을 실행한다.
```
이전과 같다.
```

#### 3단계) `MemberDao`에 적용한다.
- MemberDao.java
    - 클래스를 인터페이스로 전환한다.
    - 기존의 `detail(String,String)` 메서드를 SQL 매퍼에 정의된 SQL 문 형식과 같게 한다.
    - 즉 `detailByEmailPassword(Map)` 으로 변경한다.
- MemberMapper.xml
    - 매퍼의 네임스페이스 이름을 인터페이스 이름으로 변경한다.
- LoginCommand.java
    - `MemberDao` 인터페이스의 메서드 변경에 맞춰 호출 코드를 변경한다.
- DataLoaderListener.java
    - `MybatisDaoFactory` 객체를 이용하여 `MemberDAO` 객체를 준비한다.

##### 실습 결과

`eomcs-java-project-client`프로젝트의 `ClientApp`을 실행한다.
```
이전과 같다.
```

#### 4단계) `LessonDao`에 적용한다.
- LessonDao.java
    - 클래스를 인터페이스로 전환한다.
    - 기존의 `list()` 메서드에 `search(Map)`의 기능을 합친다.
    - `search(Map)` 메서드는 제거한다.
- LessonMapper.xml
    - 매퍼의 네임스페이스 이름을 인터페이스 이름으로 변경한다.
- LessonListCommand.java
    - LessonDao의 `list(Map)`를 호출하도록 코드를 변경한다.
- LessonSearchCommand.java
    - LessonDao의 `list(Map)`를 호출하도록 코드를 변경한다.    
- DataLoaderListener.java
    - `MybatisDaoFactory` 객체를 이용하여 `LessonDAO` 객체를 준비한다.

##### 실습 결과

`eomcs-java-project-client`프로젝트의 `ClientApp`을 실행한다.
```
이전과 같다.
```

#### 5단계) `PhotoBoardDao`와 `PhotoFileDao`에 적용한다.
- PhotoBoardDao.java
    - 클래스를 인터페이스로 전환한다.
- PhotoFileDao.java
    - 클래스를 인터페이스로 전환한다.    
- PhotoBoardMapper.xml
    - 매퍼의 네임스페이스 이름을 인터페이스 이름으로 변경한다.
- PhotoFileMapper.xml
    - 매퍼의 네임스페이스 이름을 인터페이스 이름으로 변경한다.
- DataLoaderListener.java
    - `MybatisDaoFactory` 객체를 이용하여 `PhotoBoardDAO`, `PhotoFileDao` 객체를 준비한다.

##### 실습 결과

`eomcs-java-project-client`프로젝트의 `ClientApp`을 실행한다.
```
이전과 같다.
```

## 실습 소스

- com/eomcs/sql/DaoInvocationHandler.java 추가
- com/eomcs/sql/MybatisDaoFactory.java 추가
- com/eomcs/lms/dao/BoardDao.java 변경
- com/eomcs/lms/dao/MemberDao.java 변경
- com/eomcs/lms/dao/LessonDao.java 변경
- com/eomcs/lms/dao/PhotoBoardDao.java 변경
- com/eomcs/lms/dao/PhotoFileDao.java 변경
- com/eomcs/lms/mapper/BoardMapper.xml 변경
- com/eomcs/lms/mapper/MemberMapper.xml 변경
- com/eomcs/lms/mapper/LessonMapper.xml 변경
- com/eomcs/lms/mapper/PhotoBoardMapper.xml 변경
- com/eomcs/lms/mapper/PhotoFileMapper.xml 변경
- com/eomcs/lms/handler/LoginCommand.java 변경
- com/eomcs/lms/handler/LessonListCommand.java 변경
- com/eomcs/lms/handler/LessonSearchCommand.java 변경
- com/eomcs/lms/DataLoaderListener 변경
