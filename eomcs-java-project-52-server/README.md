# eomcs-java-project-6.1-server

애노테이션을 이용하여 트랜잭션 제어하기

- Spring 에서 제공하는 애노테이션을 이용하여 트랜잭션을 제어하는 방법


## 프로젝트 - 수업관리 시스템  

Spring 프레임워크는 애노테이션을 이용하여 트랜잭션을 다루는 방법을 제공한다. 
이전 버전에서는 스프링에서 제공하는 클래스를 사용하여 트랜잭션을 직접 제어 했는데,
이번 버전은 애노테이션을 이용하여 간접적으로 트랜잭션을 제어해보자.

### ver 6.1.0 - @Transactional 애노테이션으로 트랜잭션을 제어하라.

- PhotoBoardHandler.java
    - 직접 트랜잭션 관리자를 다루기 않기 때문에 필드와 생성자에서 해당 변수를 제거한다.
    - add(), delete(), update() 메서드에서 기존의 트랜잭션 제어 코드를 제거한다.
    - add(), delete(), update() 메서드에 @Transactional 애노테이션을 적용한다.
    - 메서드를 실행하다거 오류가 발생하면 트랜잭션 관리자가 rollback 하기 때문에 add(), delete(), update() 메서드에서 실행 오류가 발생하면 예외를 던지도록 변경한다.
- ContextLoaderListener.java
    - @Transactional을 적용한 클래스의 경우 Spring IoC 컨테이너는 원본 객체를 생성하는 대신 프록시 객체를 만들어 사용한다.
    - 그래서 핸들러의 메서드를 등록할 때 원본 클래스의 메서드를 찾아 @CommandMapping 정보를 추출한다.


##### 실습 결과

서버 시작 후 `eomcs-java-project-client`프로젝트의 `ClientApp`을 실행한다.
```
이전과 같다.
```

## 실습 소스

- com/eomcs/lms/handler/PhotoBoardHandler.java 변경
- com/eomcs/lms/ContextLoaderListener.java 변경