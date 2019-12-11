# eomcs-java-project-5.8-server

CRUD 메서드를 묶어 한 클래스로 분류하기

- 클래스 문법 활용하기

## 프로젝트 - 수업관리 시스템  

CRUD 요청을 처리하는 기능을 유지보수가 쉽게 한 클래스로 묶는다. 

### ver 5.8.0 - CRUD 관련 명령 핸들러를 한 개의 명령 핸들러로 묶어라.

#### 1단계) 수업의 CRUD를 다루는 명령핸들러를 한 클래스로 묶는다.

- LessonHandler.java
    - `LessonAddCommand`, `LessonListCommand`, `LessonDetailCommand`, 
      `LessonUpdateCommand`, `LessonDeleteCommand`, `LessonSearchCommand` 클래스의 작업을 
      이 클래스로 합친다.

##### 실습 결과

`eomcs-java-project-client`프로젝트의 `ClientApp`을 실행한다.
```
이전과 같다.
```


#### 2단계) 회원의 CRUD를 다루는 명령핸들러를 한 클래스로 묶는다.

- MemberHandler.java
    - `MemberAddCommand`, `MemberListCommand`, `MemberDetailCommand`, 
      `MemberUpdateCommand`, `MemberDeleteCommand`, `MemberSearchCommand` 클래스의 작업을 
      이 클래스로 합친다.

##### 실습 결과

`eomcs-java-project-client`프로젝트의 `ClientApp`을 실행한다.
```
이전과 같다.
```

#### 3단계) 게시물의 CRUD를 다루는 명령핸들러를 한 클래스로 묶는다.

- BoardHandler.java
    - `BoardAddCommand`, `BoardListCommand`, `BoardDetailCommand`, 
      `BoardUpdateCommand`, `BoardDeleteCommand` 클래스의 작업을 이 클래스로 합친다.

##### 실습 결과

`eomcs-java-project-client`프로젝트의 `ClientApp`을 실행한다.
```
이전과 같다.
```

#### 4단계) 사진게시물의 CRUD를 다루는 명령핸들러를 한 클래스로 묶는다.

- PhotoBoardHandler.java
    - `PhotoBoardAddCommand`, `PhotoBoardListCommand`, `PhotoBoardDetailCommand`, 
      `PhotoBoardUpdateCommand`, `PhotoBoardDeleteCommand`, `PhotoBoardDetail2Command` 클래스의 작업을 
      이 클래스로 합친다.

##### 실습 결과

`eomcs-java-project-client`프로젝트의 `ClientApp`을 실행한다.
```
이전과 같다.
```

#### 5단계) 로그인의 CRUD를 다루는 명령핸들러를 한 클래스로 묶는다.

- AuthHandler.java
    - `LoginCommand`클래스의 이름을 변경한다.

##### 실습 결과

`eomcs-java-project-client`프로젝트의 `ClientApp`을 실행한다.
```
이전과 같다.
```

## 실습 소스

- com/eomcs/lms/handler/BoardAddCommand.java 삭제
- com/eomcs/lms/handler/BoardDeleteCommand.java 삭제
- com/eomcs/lms/handler/BoardDetailCommand.java 삭제
- com/eomcs/lms/handler/BoardListCommand.java 삭제
- com/eomcs/lms/handler/BoardUpdateCommand.java 삭제
- com/eomcs/lms/handler/BoardHandler.java 추가 
- com/eomcs/lms/handler/LessonAddCommand.java 삭제
- com/eomcs/lms/handler/LessonDeleteCommand.java 삭제
- com/eomcs/lms/handler/LessonDetailCommand.java 삭제
- com/eomcs/lms/handler/LessonListCommand.java 삭제
- com/eomcs/lms/handler/LessonSearchCommand.java 삭제
- com/eomcs/lms/handler/LessonUpdateCommand.java 삭제
- com/eomcs/lms/handler/LessonHandler.java 추가 
- com/eomcs/lms/handler/MemberAddCommand.java 삭제
- com/eomcs/lms/handler/MemberDeleteCommand.java 삭제
- com/eomcs/lms/handler/MemberDetailCommand.java 삭제
- com/eomcs/lms/handler/MemberListCommand.java 삭제
- com/eomcs/lms/handler/MemberSearchCommand.java 삭제
- com/eomcs/lms/handler/MemberUpdateCommand.java 삭제
- com/eomcs/lms/handler/MemberHandler.java 추가 
- com/eomcs/lms/handler/PhotoBoardAddCommand.java 삭제
- com/eomcs/lms/handler/PhotoBoardDeleteCommand.java 삭제
- com/eomcs/lms/handler/PhotoBoardDetailCommand.java 삭제
- com/eomcs/lms/handler/PhotoBoardDetail2Command.java 삭제
- com/eomcs/lms/handler/PhotoBoardListCommand.java 삭제
- com/eomcs/lms/handler/PhotoBoardUpdateCommand.java 삭제
- com/eomcs/lms/handler/PhotoBoardHandler.java 추가 
- com/eomcs/lms/handler/LoginCommand.java 삭제
- com/eomcs/lms/handler/AuthHandler.java 추가 
