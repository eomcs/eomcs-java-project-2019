# eomcs-java-project-4.1-server

네트워크 API를 활용하여 애플리케이션 사이에 데이터 주고 받기

- 데이터 관리를 별도의 프로그램으로 분리하기
- 네트워크 API 사용법
- 클라이언트/서버 애플리케이션 아키텍처의 이해
- `Stateful` 통신 방식의 특징과 장단점 이해하기
- 상속의 일반화(generalization) 기법과 추상 클래스 활용법

## 프로젝트 - 수업관리 시스템  

### 과제: 데이터 관리 기능을 별도의 프로그램으로 분리하여 서버로 만든다.

`App.java`에서 데이터 관리 기능을 별도의 프로젝트로 분리한다.

#### ver 4.1.0
서버 애플리케이션 프로그래밍을 위한 프로젝트 `eomcs-java-project`를 생성한다. 프로젝트를 만들고 초기화시키는 것은 **1.0 자바 애플리케이션 프로젝트 만들기**를 참조하라.

#### ver 4.1.1
간단한 통신을 수행할 서버 프로그램과 서버 프로그램을 테스트할 클라이언트 프로그램을 만든다.

- ServerApp.java
    - `ServerSocket`을 이용하여 클라이언트 연결 요청을 승인한다.
- ServerTest.java
    - 서버와의 연결을 테스트한다.

#### ver 4.1.2
클라이언트, 서버 사이에 간단한 인사말을 주고 받는다.

- ServerApp.java
    - `Socket`의 입출력 스트림을 통해 데이터를 읽고 쓴다.
- ServerTest.java
    - `Socket`의 출력 스트림으로 먼저 데이터를 보내고, 입력 스트림으로 들어 온 데이터를 출력한다.

#### ver 4.1.3
클라이언트/서버 사이에 클래스의 인스턴스를 주고 받는다.

- com.eomcs.domain 패키지 복사
    - `eomcs-java-project`의 도메인 클래스를 모두 복사해온다.
    - 테스트할 때 객체의 필드 값을 확인하기 쉽게 toString()을 오버라이딩한다.
- ServerApp.java
    - ObjectInputStream/ObjectOutputStream 을 사용하여 객체를 주고 받는다.
- ServerTest.java
    - `Socket`의 출력 스트림으로 먼저 데이터를 보내고, 입력 스트림으로 들어 온 데이터를 출력한다.

#### ver 4.1.4
클라이언트가 객체를 보내면 서버에서 받아 컬렉션에 저장하고, 클라이언트가 객체를 요청하면 서버는 컬렉션에서 객체를 꺼내 보내준다.

- ServerApp.java
    - 명령어에 따라 컬렉션에 객체를 저장하거나 꺼낸다.
    - 명령어
        - "add" : 컬렉션에 객체를 저장한다.
        - "list" : 컬렉션에 저장된 객체 목록을 클라이언트에게 보낸다.
        - "quit" : 연결을 끊는다.
- ServerTest.java
    - 명령어를 객체를 저장, 조회를 요청한다.


#### 실행 결과

먼저 `ServerApp`을 실행한다.
```
서버 시작!
.
.
.
클라이언트와 연결되었음.
클라이언트와 연결을 끊었음.
```

`ServerTest`을 실행한다.
```
서버와 연결되었음.
OK
OK
Member [no=1, name=홍길동, email=hong@test.com, password=1111, photo=hong.jpeg, tel=1111-2222, registeredDate=null]
Member [no=2, name=임꺽정, email=leem@test.com, password=1111, photo=leem.jpeg, tel=1111-3333, registeredDate=null]
서버와 연결을 끊었음.
```

## 실습 소스

- src/main/java/com/eomcs/lms/ServerApp.java 변경
- src/main/java/com/eomcs/lms/ServerTest.java 변경