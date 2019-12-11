# eomcs-java-project-5.7-server

인터페이스 대신 애노테이션으로 메서드 구분하기

- 호출할 메서드를 애노테이션으로 표시하는 방법

## 프로젝트 - 수업관리 시스템  

명령어를 처리하는 클래스는 반드시 `Command` 인터페이스를 구현해야 하는 것이 기존 방식이었다.
만약 한 클래스에 여러 개의 명령어를 처리하는 메서드를 두려고 한다면 기존 방식으로는 너무 코딩이 복잡해진다.
애노테이션을 활용하면 굳이 인터페이스를 구현하도록 강제할 필요가 없어 코딩이 유연해지고,
한 클래스에 여러 명령을 처리할 수 있는 메서드를 둘 수 있다.

### ver 5.7.0 - 애노테이션을 사용하여 명령 핸들러를 지정하라.

#### 1단계) 애노테이션을 정의한다.

- CommandMapping.java
    - 명령이 들어왔을 때 호출되는 메서드에 이 애노테이션을 붙인다.

#### 2단계) 애노테이션을 핸들러에 적용한다.

- Command.java
    - 이 인터페이스는 제거한다.
- XxxCommand.java
    - 명령어에 대해 호출될 메서드에 `CommandMapping` 애노테이션을 붙인다.
    - 애노테이션의 `command` 속성에 명령어를 설정한다.
    - `@Component` 애노테이션의 `value` 속성은 제거한다.
    - 메서드 이름은 명령어에 맞춰 적절하게 변경한다. 
        - 예) add(), list(), detail(), update(), delete(), search(), login() 등

#### 3단계) IoC 컨테이너가 생성한 객체 중에 @CommandMapping이 붙은 메서드 정보를 추출한다.

- CommandMappingHandlerMapping.java
    - IoC 컨테이너에 들어있는 객체에서 `@CommandMapping` 애노테이션이 붙은 메서드를 찾아 따로 보관한다.
- CommandMappingHandlerMapping.HandlerMethod 중첩 클래스
    - `@CommandMapping` 애노테이션이 붙은 메서드와 인스턴스 정보를 저장한다.
- ContextLoaderListener.java
    - 객체를 생성한 후 `@CommandMapping` 애노테이션이 붙은 메서드를 찾아 `CommandMappingHandlerMapping` 객체에 보관한다.

#### 4단계) 클라이언트로부터 명령이 들어오면 CommandMappingHandlerMapping 객체에서 메서드를 찾아 호출한다.

- App.java
    - iocContainer에서 명령 핸들러를 꺼내는 것이 아니라 `CommandMappingHandlerMapping`에서 꺼내 호출한다.

##### 실습 결과

`eomcs-java-project-client`프로젝트의 `ClientApp`을 실행한다.
```
이전과 같다.
```

## 실습 소스

- com/eomcs/stereotype/CommandMapping.java 추가
- com/eomcs/lms/handler/Command.java 삭제
- com/eomcs/lms/handler/XxxCommand.java 변경
- com/eomcs/context/CommandMappingHandlerMapping.java 추가
- com/eomcs/lms/ContextLoaderListener.java 변경
- com/eomcs/lms/App.java 변경