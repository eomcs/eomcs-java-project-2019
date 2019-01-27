# 24 - `Iterator` 디자인 패턴의 활용

## 학습 목표

- `Iterator` 디자인 패턴의 용도를 이해하고 활용할 수 있다.
- 자료구조와 상관없이 일관된 방법으로 데이터를 조회할 수 있다.


## 실습 소스 및 결과

- src/main/java/com/eomcs/util/Iterator.java 추가
- src/main/java/com/eomcs/util/Stack.java 변경
- src/main/java/com/eomcs/util/Queue.java 변경
- src/main/java/com/eomcs/lms/App.java 변경

## 실습

### 훈련1. Stack 이나 Queue 에서 값을 꺼내는 방법을 통일하라.

- Iterator.java
    - 인터페이스로 값을 꺼내는 규칙을 정의한다.
- Stack.java
    - `Iterator` 인터페이스의 구현체를 리턴하는 iterator() 를 정의한다.
- Queue.java
    - `Iterator` 인터페이스의 구현체를 리턴하는 iterator() 를 정의한다.
- App.java
    - `history`, `history2` 명령을 처리할 때 Stack, Queue 객체에서 직접 값을 꺼내지 않고 Iterator 구현체를 통해서 꺼낸다.
