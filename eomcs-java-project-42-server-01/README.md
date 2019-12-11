# eomcs-java-project-5.1-server

SQL 삽입 공격과 자바 시큐어 코딩

- SQL 삽입 공격과 그 방어법
- Statement와 PreparedStatement 특징 비교

## 프로젝트 - 수업관리 시스템  

DB 프로그래밍의 핵심은 JDBC API를 사용하여 SQL문을 실행하는 것이다. SQL 문은 보통 사용자가 입력한 값을 가지고 작성하는데, 여기서 보안 문제가 발생한다. SQL을 잘 아는 사용자가 입력 값에 SQL 문법을 포함시켜서 내부 데이터를 조회한다거나 변경할 수 있다.

### 과제 - SQL 삽입 공격에 대햔 보안 문제를 해결하라.

#### ver 5.1.0 - 사용자 로그인 기능을 만들라.

- MemberDao.java
    - 이메일과 암호로 회원 정볼르 조회하는 메서드를 추가한다.
- LoginCommand.java
    - 사용자로부터 이메일, 암호를 입력 받아 로그인을 수행한다.
- App.java
    - `LoginCommand`를 커맨드맵에 등록한다.

##### 실습 결과

`eomcs-java-project-server` 프로젝트의 `App` 클래스를 실행한다.
```
이전과 같다.
```

`eomcs-java-project-client`프로젝트의 `ClientApp`을 실행한다.
```
명령> /auth/login
이메일?
user01@test.com
암호?
1212
이메일 또는 암호가 맞지 않습니다!

명령> /auth/login
이메일?
user01@test.com
암호?
1111
홍길동님 환영합니다!

명령> 
```

##### SQL 삽입 공격

로그인할 때 SQL 문법에 영향을 끼치는 값을 넣어 SQL 삽입 공격을 실험해보자.
```
명령> /auth/login
이메일?
user01@test.com' or 'x'='x
암호?
1212
홍길동님 환영합니다!

명령> 
```

분명히 잘못된 암호를 넣었는데도 불구하고 로그인에 성공했다. 이것이 SQL 삽입 공격이다. 

`MemberDao.detail(String email, String password)`의 코드를 살펴보면 다음과 같이 사용자 입력한 값을 가지고 SQL문을 만들어 실행한다.

```
ResultSet rs = stmt.executeQuery(
    "SELECT MNO,NAME,EMAIL,PWD,PHOTO,TEL,CDT FROM MEMBER WHERE EMAIL='" +
    email + "' AND PWD='" + password + "'")
```

즉 다음과 같이 사용자가 입력한 값이 SQL 문장 안에 그대로 삽입된다.

```
SELECT MNO,NAME,EMAIL,PWD,PHOTO,TEL,CDT FROM MEMBER 
WHERE EMAIL='user01@test.com' or 'x'='x' AND PWD='1212'
```

사용자 이메일 값 속에 포함된 `' or 'x'='x` 문구 때문에 이메일만 일치하다면 `where` 절 조건은 무조건 참이 된다. 암호가 다르더라도 로그인에 성공하는 것이다. 


## 실습 소스

- src/main/java/com/eomcs/lms/dao/MemberDao.java 변경
- src/main/java/com/eomcs/lms/handler/LoginCommand.java 추가
- src/main/java/com/eomcs/lms/App.java 변경
