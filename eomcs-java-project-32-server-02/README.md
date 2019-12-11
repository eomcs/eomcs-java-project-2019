# eomcs-java-project-4.1-server

네트워크 API를 활용하여 애플리케이션 사이에 데이터 주고 받기

- 데이터 관리를 별도의 프로그램으로 분리하기
- 네트워크 API 사용법
- 클라이언트/서버 애플리케이션 아키텍처의 이해
- `Stateful` 통신 방식의 특징과 장단점 이해하기
- 상속의 일반화(generalization) 기법과 추상 클래스 활용법
 
## 프로젝트 - 수업관리 시스템  

### 과제: 데이터 관리 기능을 별도의 프로그램으로 분리하여 서버로 만든다.

`App.java`에서 데이터 관리 기능을 분리하여 `ServerApp.java`를 정의한다.

#### ver. 4.1.0
서버 애플리케이션 프로그래밍을 위한 프로젝트 `eomcs-java-project`를 생성한다. 프로젝트를 만들고 초기화시키는 것은 **1.0 자바 애플리케이션 프로젝트 만들기**를 참조하라.

#### ver. 4.1.1
간단한 통신을 수행할 서버 프로그램과 서버 프로그램을 테스트할 클라이언트 프로그램을 만든다.

- ServerApp.java
    - `ServerSocket`을 이용하여 클라이언트 연결 요청을 승인한다.
- ServerTest.java
    - 서버와의 연결을 테스트한다.

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
서버와 연결을 끊었음.
```

## 실습 소스

- src/main/java/com/eomcs/lms/ 패키지 생성
- src/main/java/com/eomcs/lms/ServerApp.java 추가
- src/main/java/com/eomcs/lms/ServerTest.java 추가