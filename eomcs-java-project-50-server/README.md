# eomcs-java-project-5.9-server

Spring IoC 컨테이너 도입하기

- 오픈 소스 Spring IoC 컨테이너를 설정하고 사용하는 방법

## 프로젝트 - 수업관리 시스템  

직접 만든 IoC 컨테이너 대신에 훨씬 더 많은 기능을 갖고 있는 오픈 소스 IoC 컨테이너 
`Spring IoC 컨테이너`를 사용한다.

### ver 5.9.0 - IoC 컨테이너를 Spring IoC 컨테이너로 대체하라.

#### 1단계) Spring IoC 컨테이너 라이브러리를 가져온다.

- Spring IoC 컨테이너의 라이브러리 정보 찾기
    - `mvnrepository.com`에서 `spring-context` 이름으로 검색하면 Spring IoC 컨테이너의 라이브러리 정보를 알 수 있다.
- 빌드 설정 파일에 의존 라이브러리 정보 추가하기
    - `build.gradle` 파일에 라이브러리 정보를 추가한다.
    - 예) `compile group: 'org.springframework', name: 'spring-context', version: '5.1.2.RELEASE'`
- 라이브러리 파일을 프로젝트로 가져오기
    - Gradle 빌드 도구를 사용하여 라이브러리를 가져온다.
    - `$ gradle eclipse` 명령을 실행하면, 의존 라이브러리를 가져온다. 
    - 또한 Eclipse의 CLASSPATH 정보를 갱신한다.
    - 명령어를 실행한 후 이클립스에서 프로젝트를 갱신해야 한다.

#### 2단계) Spring IoC 컨테이너의 설정 정보를 제공하는 클래스 만든다.

- AppConfig.java
    - 스프링 설정 정보를 제공하는 클래스이다.
    - 다른 객체가 사용할 의존 객체를 정의한다.
    - `ContextLoaderListener`에서 생성한 객체를 이 클래스에서 준비한다.

#### 3단계) Spring IoC 컨테이너를 생성한다.

- ApplicationContext.java
    - 기존의 IoC 컨테이너는 제거한다.
- ContextLoaderListener.java
    - Spring IoC 컨테이너에서 제공하는 클래스를 사용하여 IoC 컨테이너를 만든다.

#### 4단계) Spring IoC 컨테이너를 사용하여 클라이언트가 보낸 명령을 처리한다.

- App.java
    - 기존의 클래스 대신에 Spring IoC 컨테이너의 `ApplicationContext`를 사용한다.

#### 5단계) 명령 핸들러의 애노테이션을 Spring IoC 컨테이너의 애노테이션으로 교체한다.

- Component.java
    - Spring IoC 컨테이너의 애노테이션을 사용하기 때문에 이 애노테이션은 제거한다.
- BoardHandler.java
    - Spring IoC 컨테이너의 `@Component` 애노테이션으로 바꾼다.
- MemberHandler.java
    - Spring IoC 컨테이너의 `@Component` 애노테이션으로 바꾼다.
- LessonHandler.java
    - Spring IoC 컨테이너의 `@Component` 애노테이션으로 바꾼다.
- PhotoBoardHandler.java
    - Spring IoC 컨테이너의 `@Component` 애노테이션으로 바꾼다.
- AuthHandler.java
    - Spring IoC 컨테이너의 `@Component` 애노테이션으로 바꾼다.

##### 실습 결과

서버 시작 후 `eomcs-java-project-client`프로젝트의 `ClientApp`을 실행한다.
```
이전과 같다.
```

## 실습 소스

- build.gradle 변경
- com/eomcs/lms/AppConfig.java 추가 
- com/eomcs/context/ApplicationContext.java 삭제
- com/eomcs/lms/ContextLoaderListener.java 변경
- com/eomcs/lms/App.java 변경
- com/eomcs/stereotype/Component.java 삭제
- com/eomcs/lms/handler/BoardHandler.java 변경
- com/eomcs/lms/handler/MemberHandler.java 변경
- com/eomcs/lms/handler/LessonHandler.java 변경
- com/eomcs/lms/handler/PhotoBoardHandler.java 변경
- com/eomcs/lms/handler/AuthHandler.java 변경