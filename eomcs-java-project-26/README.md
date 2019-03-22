# 26 - `커맨드(Command)` 디자인 패턴을 적용하기

## 학습 목표

- `Command` 디자인 패턴의 개념과 용도를 이해한다.
- `Command` 디자인 패턴을 활용할 수 있다.


## 실습 소스 및 결과

- src/main/java/com/eomcs/lms/handler/Command.java 추가
- src/main/java/com/eomcs/lms/handler/LessonAddCommand.java 추가
- src/main/java/com/eomcs/lms/handler/LessonListCommand.java 추가
- src/main/java/com/eomcs/lms/handler/LessonDetailCommand.java 추가
- src/main/java/com/eomcs/lms/handler/LessonUpdateCommand.java 추가
- src/main/java/com/eomcs/lms/handler/LessonDeleteCommand.java 추가
- src/main/java/com/eomcs/lms/handler/MemberAddCommand.java 추가
- src/main/java/com/eomcs/lms/handler/MemberListCommand.java 추가
- src/main/java/com/eomcs/lms/handler/MemberDetailCommand.java 추가
- src/main/java/com/eomcs/lms/handler/MemberUpdateCommand.java 추가
- src/main/java/com/eomcs/lms/handler/MemberDeleteCommand.java 추가
- src/main/java/com/eomcs/lms/handler/BoardAddCommand.java 추가
- src/main/java/com/eomcs/lms/handler/BoardListCommand.java 추가
- src/main/java/com/eomcs/lms/handler/BoardDetailCommand.java 추가
- src/main/java/com/eomcs/lms/handler/BoardUpdateCommand.java 추가
- src/main/java/com/eomcs/lms/handler/BoardDeleteCommand.java 추가
- src/main/java/com/eomcs/lms/handler/LessonHandler.java 삭제
- src/main/java/com/eomcs/lms/handler/MemberHandler.java 삭제
- src/main/java/com/eomcs/lms/handler/BoardHandler.java 삭제
- src/main/java/com/eomcs/lms/App.java 변경

## 실습

### 훈련1. 메서드를 호출하는 쪽과 실행 쪽 사이의 규칙을 정의하라.

- Command.java
    - `App` 클래스와 명령을 처리하는 클래스 사이의 호출 규칙을 정의한다.

### 훈련2. 명령을 처리하는 각 메서드를 객체로 분리하라.

- LessonHandler.java
    - 수업 CRUD 각 기능을 `Command` 규칙에 따라 객체로 분리한다.
- MemberHandler.java
    - 수업 CRUD 각 기능을 `Command` 규칙에 따라 객체로 분리한다.
- BoardHandler.java
    - 수업 CRUD 각 기능을 `Command` 규칙에 따라 객체로 분리한다.
- App.java (App.java.01)
    - 명령어가 입력되면 `Command` 규칙에 따라 객체를 실행한다.
    - `/board2/xxx` 명령 처리는 삭제한다.

### 훈련 3: `Map`으로 `Command` 객체를 관리하라.

- App.java
    - 명령어를 `key`, `Command` 객체를 `value`로 하여 Map에 저장한다.
    - 각 명령에 대해 조건문으로 분기하는 부분을 간략하게 변경한다.
