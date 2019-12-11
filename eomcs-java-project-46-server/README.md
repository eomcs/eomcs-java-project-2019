# eomcs-java-project-5.5-server

객체 생성을 자동화하는 IoC 컨테이너 만들기

- IoC 컨테이너의 개념과 구동 원리
- 리플랙션 API를 활용하여 객체를 생성하는 방법


## 프로젝트 - 수업관리 시스템  

새 명령을 추가할 때마다 `App` 클래스에 핸들러 객체를 등록하는 코드를 추가해야 한다. IoC 컨테이너를 도입하여 이런 번거로움을 없애보자.

### ver 5.5.0 - 지정된 패키지에서 `Command` 구현체를 찾아 객체를 자동 생성하라.

#### 1단계) `Command` 객체를 자동 생성하는 클래스를 만든다.

- ApplicationContext.java
    - 생성자에 패키지 이름을 지정하면 해당 패키지를 뒤져서 `Command` 구현 클래스를 찾는다.
    - `Command` 구현체의 인스턴스를 생성하여 보관한다.

#### 2단계) `Command` 구현체에 name 필드를 추가한다.

- XxxCommand.java
    - 명령어를 저장하는 `name` 필드를 추가한다.

#### 3단계) 애플리케이션이 시작될 때 IoC 컨테이너를 생성한다.

- DataLoaderListener.java
    - 이 클래스에서 `ApplicationContext` 객체를 준비한다.
    - 클래스 목적에 맞춰 이름을 `ContextLoaderListener`로 변경한다.
  
#### 4단계) 명령어를 처리할 때 IoC 컨테이너에서 핸들러를 꺼낸다.

- App.java
    - 기존에는 이 클래스에서 핸들러 객체를 생성하였다. 관련 코드를 제거한다.
    - `ApplicationContext` 객체에서 핸들러를 꺼내 사용한다.

##### 실습 결과

`eomcs-java-project-client`프로젝트의 `ClientApp`을 실행한다.
```
이전과 같다.
```

## 실습 소스

- com/eomcs/context/ApplicationContext.java 추가
- com/eomcs/lms/handler/XxxCommand.java 변경
- com/eomcs/lms/DataLoaderListener.java 삭제
- com/eomcs/lms/ContextLoaderListener.java 추가
- com/eomcs/lms/App.java 변경
