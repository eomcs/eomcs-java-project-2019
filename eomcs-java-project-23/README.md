# 23 - 인터페이스를 활용하여 객체 사용 규칙 정의하기

## 학습 목표

- 인터페이스의 용도와 이점을 이해한다.
- 객체 간의 사용 규칙을 정의할 때 인터페이스를 활용할 수 있다.


## 실습 소스 및 결과

- src/main/java/com/eomcs/util/List.java 추가
- src/main/java/com/eomcs/util/ArrayList.java 변경
- src/main/java/com/eomcs/util/LinkedList.java 변경
- src/main/java/com/eomcs/lms/handler/LessonHandler.java 변경
- src/main/java/com/eomcs/lms/handler/MemberHandler.java 변경
- src/main/java/com/eomcs/lms/handler/BoardHandler.java 변경
- src/main/java/com/eomcs/lms/App.java 변경

## 실습

### 훈련1. 데이터 관리 객체를 구분 없이 사용하게 하라.

- List.java
    - 핸들러와 목록을 다루는 객체 사이의 호출 규칙을 정의한다.
    - 즉 핸들러가 호출할 메서드의 규칙을 인터페이스로 정의한다.
- ArrayList.java
    - `List` 인터페이스를 구현한다.
- LinkedList.java
    - `List` 인터페이스를 구현한다.
- LessonHandler.java
    - ArrayList 또는 LinkedList를 직접 지정하는 대신에 인터페이스를 필드로 선언한다.
    - 생성자에서 List 구현체를 공급받도록 변경하여 특정 클래스에 의존하는 코드를 제거한다.
- MemberHandler.java
    - ArrayList 또는 LinkedList를 직접 지정하는 대신에 인터페이스를 필드로 선언한다.
    - 생성자에서 List 구현체를 공급받도록 변경하여 특정 클래스에 의존하는 코드를 제거한다.
- BoardHandler.java
    - ArrayList 또는 LinkedList를 직접 지정하는 대신에 인터페이스를 필드로 선언한다.
    - 생성자에서 List 구현체를 공급받도록 변경하여 특정 클래스에 의존하는 코드를 제거한다.
- App.java
    - 핸들러를 생성할 때 List 구현체를 넘겨준다.
