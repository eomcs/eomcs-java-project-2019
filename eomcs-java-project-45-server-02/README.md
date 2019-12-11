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

### ver 5.4.1 - MyBatis의 SqlSession을 이용하여 DAO 구현체를 자동으로 생성한다.

MyBatis에서도 DAO 인터페이스를 구현한 객체를 만들어 줄 수 있다.

#### 1단계) XxxCommand 객체를 만들 때 사용할 SqlSessionFactory를 맵에 보관한다.

- DataLoaderListener.java
    - `contextInitialized()`에서 `SqlSessionFactory` 객체를 맵에 보관한다.

#### 2단계) BoardXxxxCommand 클래스에 SqlSessionFactory를 주입한다.

- BoardListCommand.java
    - 생성자는 DAO 객체 대신에 SqlSessionFactory를 받는다.
    - 명령어를 처리할 때마다 SqlSession 객체에서 Dao 객체를 만들어 사용한다.
- BoardAddCommand.java
    - 생성자는 DAO 객체 대신에 SqlSessionFactory를 받는다.
    - 명령어를 처리할 때마다 SqlSession 객체에서 Dao 객체를 만들어 사용한다.
    - commit()/rollback()을 직접 호출한다.
- BoardDetailCommand.java
    - 생성자는 DAO 객체 대신에 SqlSessionFactory를 받는다.
    - 명령어를 처리할 때마다 SqlSession 객체에서 Dao 객체를 만들어 사용한다.
- BoardUpdateCommand.java
    - 생성자는 DAO 객체 대신에 SqlSessionFactory를 받는다.
    - 명령어를 처리할 때마다 SqlSession 객체에서 Dao 객체를 만들어 사용한다.
    - commit()/rollback()을 직접 호출한다.
- BoardDeleteCommand.java
    - 생성자는 DAO 객체 대신에 SqlSessionFactory를 받는다.
    - 명령어를 처리할 때마다 SqlSession 객체에서 Dao 객체를 만들어 사용한다.
    - commit()/rollback()을 직접 호출한다.
- BoardMapper.xml
    - 네이스페이스 이름을 `BoardDao` 인터페이스의 전체 이름(패키지명 + 인터페이스명)으로 변경한다.
    - SqlSession 객체를 통해 DAO 구현체를 만들 때는 매퍼의 네임스페이스 이름을 반드시 인터페이스의 전체 이름와 같아야 한다.
- App.java
    - `BoardXxxxCommand` 객체를 생성할 때 생성자에 Dao 객체 대신에 SqlSessionFactory 객체를 전달한다.

##### 실습 결과

`eomcs-java-project-client`프로젝트의 `ClientApp`을 실행한다.
```
이전과 같다.
```

#### 3단계) MemberXxxxCommand 클래스에 SqlSessionFactory를 주입한다.

- MemberListCommand.java
    - 생성자는 DAO 객체 대신에 SqlSessionFactory를 받는다.
    - 명령어를 처리할 때마다 SqlSession 객체에서 Dao 객체를 만들어 사용한다.
- MemberSearchCommand.java
    - 생성자는 DAO 객체 대신에 SqlSessionFactory를 받는다.
    - 명령어를 처리할 때마다 SqlSession 객체에서 Dao 객체를 만들어 사용한다.
- MemberAddCommand.java
    - 생성자는 DAO 객체 대신에 SqlSessionFactory를 받는다.
    - 명령어를 처리할 때마다 SqlSession 객체에서 Dao 객체를 만들어 사용한다.
    - commit()/rollback()을 직접 호출한다.
- MemberDetailCommand.java
    - 생성자는 DAO 객체 대신에 SqlSessionFactory를 받는다.
    - 명령어를 처리할 때마다 SqlSession 객체에서 Dao 객체를 만들어 사용한다.
- MemberUpdateCommand.java
    - 생성자는 DAO 객체 대신에 SqlSessionFactory를 받는다.
    - 명령어를 처리할 때마다 SqlSession 객체에서 Dao 객체를 만들어 사용한다.
    - commit()/rollback()을 직접 호출한다.
- MemberDeleteCommand.java
    - 생성자는 DAO 객체 대신에 SqlSessionFactory를 받는다.
    - 명령어를 처리할 때마다 SqlSession 객체에서 Dao 객체를 만들어 사용한다.
    - commit()/rollback()을 직접 호출한다.
- LoginCommand.java
    - 생성자는 DAO 객체 대신에 SqlSessionFactory를 받는다.
    - 명령어를 처리할 때마다 SqlSession 객체에서 Dao 객체를 만들어 사용한다.
- MemberMapper.xml
    - 네이스페이스 이름을 `MemberDao` 인터페이스의 전체 이름(패키지명 + 인터페이스명)으로 변경한다.
    - SqlSession 객체를 통해 DAO 구현체를 만들 때는 매퍼의 네임스페이스 이름을 반드시 인터페이스의 전체 이름와 같아야 한다.
- App.java
    - `MemberXxxxCommand` 객체를 생성할 때 생성자에 Dao 객체 대신에 SqlSessionFactory 객체를 전달한다.

##### 실습 결과

`eomcs-java-project-client`프로젝트의 `ClientApp`을 실행한다.
```
이전과 같다.
```

#### 4단계) LessonXxxxCommand 클래스에 SqlSessionFactory를 주입한다.

- LessonListCommand.java
    - 생성자는 DAO 객체 대신에 SqlSessionFactory를 받는다.
    - 명령어를 처리할 때마다 SqlSession 객체에서 Dao 객체를 만들어 사용한다.
- LessonSearchCommand.java
    - 생성자는 DAO 객체 대신에 SqlSessionFactory를 받는다.
    - 명령어를 처리할 때마다 SqlSession 객체에서 Dao 객체를 만들어 사용한다.
- LessonAddCommand.java
    - 생성자는 DAO 객체 대신에 SqlSessionFactory를 받는다.
    - 명령어를 처리할 때마다 SqlSession 객체에서 Dao 객체를 만들어 사용한다.
    - commit()/rollback()을 직접 호출한다.
- LessonDetailCommand.java
    - 생성자는 DAO 객체 대신에 SqlSessionFactory를 받는다.
    - 명령어를 처리할 때마다 SqlSession 객체에서 Dao 객체를 만들어 사용한다.
- LessonUpdateCommand.java
    - 생성자는 DAO 객체 대신에 SqlSessionFactory를 받는다.
    - 명령어를 처리할 때마다 SqlSession 객체에서 Dao 객체를 만들어 사용한다.
    - commit()/rollback()을 직접 호출한다.
- LessonDeleteCommand.java
    - 생성자는 DAO 객체 대신에 SqlSessionFactory를 받는다.
    - 명령어를 처리할 때마다 SqlSession 객체에서 Dao 객체를 만들어 사용한다.
    - commit()/rollback()을 직접 호출한다.
- LessonMapper.xml
    - 네이스페이스 이름을 `LessonDao` 인터페이스의 전체 이름(패키지명 + 인터페이스명)으로 변경한다.
    - SqlSession 객체를 통해 DAO 구현체를 만들 때는 매퍼의 네임스페이스 이름을 반드시 인터페이스의 전체 이름와 같아야 한다.
- App.java
    - `LessonXxxxCommand` 객체를 생성할 때 생성자에 Dao 객체 대신에 SqlSessionFactory 객체를 전달한다.

##### 실습 결과

`eomcs-java-project-client`프로젝트의 `ClientApp`을 실행한다.
```
이전과 같다.
```

#### 5단계) PhotoBoardXxxxCommand 클래스에 SqlSessionFactory를 주입한다.

- PhotoBoardListCommand.java
    - 생성자는 DAO 객체 대신에 SqlSessionFactory를 받는다.
    - 명령어를 처리할 때마다 SqlSession 객체에서 Dao 객체를 만들어 사용한다.
- PhotoBoardAddCommand.java
    - 생성자는 DAO 객체 대신에 SqlSessionFactory를 받는다.
    - 명령어를 처리할 때마다 SqlSession 객체에서 Dao 객체를 만들어 사용한다.
    - commit()/rollback()을 직접 호출한다.
- PhotoBoardDetailCommand.java
    - 생성자는 DAO 객체 대신에 SqlSessionFactory를 받는다.
    - 명령어를 처리할 때마다 SqlSession 객체에서 Dao 객체를 만들어 사용한다.
- PhotoBoardDetail2Command.java
    - 생성자는 DAO 객체 대신에 SqlSessionFactory를 받는다.
    - 명령어를 처리할 때마다 SqlSession 객체에서 Dao 객체를 만들어 사용한다.
- PhotoBoardUpdateCommand.java
    - 생성자는 DAO 객체 대신에 SqlSessionFactory를 받는다.
    - 명령어를 처리할 때마다 SqlSession 객체에서 Dao 객체를 만들어 사용한다.
    - commit()/rollback()을 직접 호출한다.
- PhotoBoardDeleteCommand.java
    - 생성자는 DAO 객체 대신에 SqlSessionFactory를 받는다.
    - 명령어를 처리할 때마다 SqlSession 객체에서 Dao 객체를 만들어 사용한다.
    - commit()/rollback()을 직접 호출한다.
- PhotoBoardMapper.xml
    - 네이스페이스 이름을 `PhotoBoardDao` 인터페이스의 전체 이름(패키지명 + 인터페이스명)으로 변경한다.
    - SqlSession 객체를 통해 DAO 구현체를 만들 때는 매퍼의 네임스페이스 이름을 반드시 인터페이스의 전체 이름와 같아야 한다.
- PhotoFileMapper.xml
    - 네이스페이스 이름을 `PhotoFileDao` 인터페이스의 전체 이름(패키지명 + 인터페이스명)으로 변경한다.
    - SqlSession 객체를 통해 DAO 구현체를 만들 때는 매퍼의 네임스페이스 이름을 반드시 인터페이스의 전체 이름와 같아야 한다.
- App.java
    - `PhotoBoardXxxxCommand` 객체를 생성할 때 생성자에 Dao 객체 대신에 SqlSessionFactory 객체를 전달한다.

##### 실습 결과

`eomcs-java-project-client`프로젝트의 `ClientApp`을 실행한다.
```
이전과 같다.
```

## 실습 소스

- com/eomcs/lms/DataLoaderListener 변경
- com/eomcs/lms/handler/BoardAddCommand.java 변경
- com/eomcs/lms/handler/BoardListCommand.java 변경
- com/eomcs/lms/handler/BoardDetailCommand.java 변경
- com/eomcs/lms/handler/BoardUpdateCommand.java 변경
- com/eomcs/lms/handler/BoardDeleteCommand.java 변경
- com/eomcs/lms/mapper/BoardMapper.xml 변경
- com/eomcs/lms/handler/MemberAddCommand.java 변경
- com/eomcs/lms/handler/MemberListCommand.java 변경
- com/eomcs/lms/handler/MemberSearchCommand.java 변경
- com/eomcs/lms/handler/MemberDetailCommand.java 변경
- com/eomcs/lms/handler/MemberUpdateCommand.java 변경
- com/eomcs/lms/handler/MemberDeleteCommand.java 변경
- com/eomcs/lms/mapper/MemberMapper.xml 변경
- com/eomcs/lms/handler/LessonAddCommand.java 변경
- com/eomcs/lms/handler/LessonListCommand.java 변경
- com/eomcs/lms/handler/LessonSearchCommand.java 변경
- com/eomcs/lms/handler/LessonDetailCommand.java 변경
- com/eomcs/lms/handler/LessonUpdateCommand.java 변경
- com/eomcs/lms/handler/LessonDeleteCommand.java 변경
- com/eomcs/lms/mapper/LessonMapper.xml 변경
- com/eomcs/lms/handler/PhotoBoardAddCommand.java 변경
- com/eomcs/lms/handler/PhotoBoardListCommand.java 변경
- com/eomcs/lms/handler/PhotoBoardDetailCommand.java 변경
- com/eomcs/lms/handler/PhotoBoardDetail2Command.java 변경
- com/eomcs/lms/handler/PhotoBoardUpdateCommand.java 변경
- com/eomcs/lms/handler/PhotoBoardDeleteCommand.java 변경
- com/eomcs/lms/mapper/PhotoBoardMapper.xml 변경
- com/eomcs/lms/mapper/PhotoFileMapper.xml 변경
- com/eomcs/lms/App.java 변경
- com/eomcs/sql/DaoInvocationHandler.java 삭제
- com/eomcs/sql/MybatisDaoFactory.java 삭제
- com/eomcs/sql/SqlSessionFactoryProxy.java 삭제
- com/eomcs/sql/SqlSessionProxy.java 삭제
- com/eomcs/sql/TransactionManager.java 삭제
