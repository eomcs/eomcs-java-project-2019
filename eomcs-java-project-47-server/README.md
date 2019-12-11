# eomcs-java-project-5.6-server

IoC 컨테이너 개선 : 애노테이션을 이용한 객체 관리

- 애노테이션을 활용하여 객체에 특별한 주석을 붙이는 방법
- 애노테이션을 정의하고, 사용하고, 추출하는 방법

## 프로젝트 - 수업관리 시스템  

기존의 IoC 컨테이너는 `Command` 구현체에 한정해서 객체를 관리하였다. 
애노테이션을 이용하면 관리해야 할 대상을 좀 더 쉽게 지정할 수 있다. 
기존에는 객체의 이름을 설정하기 위해 `name` 이라는 필드를 사용하였다. 
애노테이션의 프로퍼티를 이용하여 기존의 `name` 필드 역할을 대체할 수 있다.

### ver 5.6.0 - IoC 컨테이너가 관리할 객체를 애노테이션으로 지정하라.

#### 1단계) 애노테이션을 정의한다.

- Component.java
    - IoC 컨테이너가 관리할 대상에게 붙이는 애노테이션이다.

#### 2단계) 애노테이션을 핸들러에 적용한다.

- XxxCommand.java
    - `Component` 애노테이션을 붙인다.
    - `name` 필드를 제거하고 대신 `Component` 애노테이션의 `value` 속성에 객체 이름을 설정한다.

#### 3단계) IoC 컨테이너는 @Component 애노테이션이 붙은 클래스에 대해 인스턴스를 생성한다.

- ApplicationContext.java
    - 기존에는 `Command` 인터페이스 구현체인 경우에만 인스턴스를 자동으로 생성하였다.
    - 이것을 `@Component` 애노테이션이 붙은 클래스에 대해서만 인스턴스를 생성하도록 변경한다.

##### 실습 결과

`eomcs-java-project-client`프로젝트의 `ClientApp`을 실행한다.
```
이전과 같다.
```

## 실습 소스

- com/eomcs/stereotype/Component.java 추가
- com/eomcs/lms/handler/XxxCommand.java 변경
- com/eomcs/context/ApplicationContext.java 변경

