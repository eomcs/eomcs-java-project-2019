# eomcs-java-project-5.3-server

MyBatis의 다양한 기능 활용법

- MyBatis의 다양한 기능을 사용하는 방법

## 프로젝트 - 수업관리 시스템  

`MyBatis`에게 제공하는 다양한 기능을 사용하면 좀 더 편리하게 코드를 작성할 수 있다.

### ver 5.3.0 - `MyBatis` SQL 매퍼에서 사용할 값 객체(Value Object)에 별명을 부여한다.

SQL 매퍼 파일에서 자바 클래스를 지정할 때 패키지 이름까지 지정해야 한다. 그런데 MyBatis 설정 파일에서 클래스에 대한 별명을 등록해두면 SQL 매퍼 파일에서 사용하기 편하다.

- mybatis-config.xml
    - `<typeAliases/>` 태그를 사용하여 별명을 등록한다.
- XxxMapper.xml
    - 클래스 이름을 지정할 때 별명을 사용한다. 

##### 실습 결과

`eomcs-java-project-client`프로젝트의 `ClientApp`을 실행한다.
```
이전과 같다.
```

### ver 5.3.1 - SQL 매퍼에서 Result Map을 정의하여 컬럼 이름과 프로퍼티 이름을 자동 연결한다.

컬럼 이름과 객체의 프로퍼티 이름이 다르면 결과 DB 결과 값을 객체에 넣을 수 없다. 그래서 프로퍼티 이름과 같은 이름으로 컬럼에 별명을 부여하였다. 문제는 매번 select를 작성할 때마다 별명을 부여해야 하기 때문에 불편하다. MyBatis에서 제공하는 `<resultMap/>`을 사용하면 별명을 부여할 필요가 없이 손쉽게 컬럼과 객체의 프로퍼티를 연결할 수 있다.

- XxxMapper.xml
    - 컬럼과 프로퍼티를 연결한 정보를 등록한다.
    - select 할 때 이 정보를 사용한다.

##### 실습 결과

`eomcs-java-project-client`프로젝트의 `ClientApp`을 실행한다.
```
이전과 같다.
```

### ver 5.3.2 - SQL의 일부코드를 따로 정의해두고 재사용한다.

여러 SQL문에서 반복적으로 사용되는 SQL 코드가 있다면 `<sql/>` 태그로 정의하여 재사용할 수 있다.

- BoardMapper.xml
    - list/detail SQL 문에 공통으로 들어갈 SQL 코드를 분리한다.
- MemberMapper.xml
    - list/search SQL 문에 공통으로 들어갈 SQL 코드를 분리한다.
    - detail/detailByEmailPassword 문에 공통으로 들어갈 SQL 코드를 분리한다.
- LessonMapper.xml
    - list/detail SQL 문에 공통으로 들어갈 SQL 코드를 분리한다. 
- PhotoBoardMapper.xml
    - list/detail SQL 문에 공통으로 들어갈 SQL 코드를 분리한다. 
- PhotoFileMapper.xml
    - list/detail SQL 문에 공통으로 들어갈 SQL 코드를 분리한다. 


##### 실습 결과

`eomcs-java-project-client`프로젝트의 `ClientApp`을 실행한다.
```
이전과 같다.
```

### ver 5.3.3 - 동적 SQL을 만드는 방법


#### `<bind>` 태그 

매퍼 파일에서 임의의 변수를 정의할 떄 사용한다.

- MemberMapper.xml
    - 회원 검색에서 사용할 '%검색어%' 값을 자바에서 만드는 대신에 `<bind>` 태그를 사용하여 만든다.
- MemberDao.java
    - `search()` 메서드에서 검색어 패턴을 만들기 위해 키워드 앞, 뒤로 "%"을 붙였는데, 이제 매퍼 파일에서 처리하니까 제거한다.

#### `<foreach>` 태그

SQL 코드를 반복 작성할 때 사용한다.

- PhotoFileMapper.xml
    - `add` SQL 문에 `foreach`를 적용하여 여러 개의 사진 파일을 한 번에 입력하는 SQL문을 작성한다.
- PhotoFileDao.java
    - `add()`의 파라미터를 `List` 타입으로 바꾼다.
- PhotoBoardAddCommand.java
    - `PhotoFileDao`의 `add()` 변경에 따라 코드를 변경한다.
- PhotoBoardUpdateCommand.java
    - `PhotoFileDao`의 `add()` 변경에 따라 코드를 변경한다.

#### `<set>` 태그

기존에는 update 할 때 변경했든 안했든 상관없이 모든 컬럼의 값을 update 했다. 이번에는 값을 변경한 컬럼에 대해서만 update를 수행한다.

- MemberMapper.xml
    - `update` SQL 문에 `<set>` 태그를 적용한다.
- MemberUpdateCommand.java
    - 변경할 값을 입력하지 않고 그냥 엔터를 치면 기존 값을 넣지 않고 null로 설정한다.


##### 실습 결과

`eomcs-java-project-client`프로젝트의 `ClientApp`을 실행한다.
```
이전과 같다.
```

#### `<where>` 태그

수업을 검색하는 기능을 추가한다. 단 검색을 위한 새 SQL을 추가하지 말고, 기존에 수업 목록을 출력할 때 사용한 SQL문에 조건을 추가하여 사용한다.

- LessonMapper.xml
    - `list` SQL 문에 `<where>` 태그를 적용한다.
- LessonDao.java
    - `search()` 메서드를 추가한다.
- LessonSearchCommand.java
    - 검색어(제목,설명,시작일,종료일)을 입력 받아 검색한다.
    - 여러 개를 입력할 경우 and 조건으로 검색한다.
- App.java
    - `/lesson/search` 명령을 처리할 `LessonSearchCommand` 객체를 등록한다.

##### 실습 결과

`eomcs-java-project-client`프로젝트의 `ClientApp`을 실행한다.
```
명령> /lesson/search
수업명?
기초
설명?

시작일(<=)?

종료일(<=)?

  1, 자바 기초 프로그래밍    , 2019-01-02 ~ 2019-01-15,   80

명령> /lesson/search
수업명?

설명?
응용
시작일(<=)?

종료일(<=)?

  2, 자바 고급 프로그래밍    , 2019-01-14 ~ 2019-01-25,   80

명령> /lesson/search
수업명?
자바
설명?

시작일(<=)?
2019-1-25
종료일(<=)?


명령> /lesson/search
수업명?
자바
설명?

시작일(<=)?
2019-1-10
종료일(<=)?

  2, 자바 고급 프로그래밍    , 2019-01-14 ~ 2019-01-25,   80
  3, 자바 웹 프로그래밍     , 2019-01-21 ~ 2019-02-01,   80

명령> 
```

#### 여러 개의 테이블을 조인한 결과 가져오기

사진 게시판의 기본 정보와 첨부 파일 정보를 한꺼번에 가져온다.

- PhotoBoard.java
    - `PhotoFile` 객체 목록을 저장할 프로퍼티를 추가한다.
- PhotoBoardMapper.xml
    - `detail2` SQL문을 추가한다.
    - `PHOTO` 테이블과 `PHO_FILE` 테이블을 조인하여 상세정보를 가져온다.
- PhotoBoardDao.java
    - `detail2()` 메서드를 추가한다.
- PhotoBoardDetail2Command.java
    - `/photoboard/detail2` 명령을 처리할 클래스를 추가한다.
- App.java
    - `/photoboard/detail2` 명령을 처리할 객체를 등록한다.

##### 실습 결과

`eomcs-java-project-client`프로젝트의 `ClientApp`을 실행한다.
```
명령> /photoboard/detail2
번호?
8
제목: nono
작성일: 2018-11-15
조회수: 0
수업: 1
사진 파일:
> x.png
> y.png
> z.png

명령>
```

## 실습 소스

- com/eomcs/lms/domain/PhotoBoard.java 변경
- com/eomcs/lms/mapper/MemberMapper.xml 변경
- com/eomcs/lms/mapper/PhotoFileMapper.xml 변경
- com/eomcs/lms/mapper/LessonMapper.xml 변경
- com/eomcs/lms/mapper/PhotoBoardMapper.xml 변경
- com/eomcs/lms/dao/MemberDao.java 변경
- com/eomcs/lms/dao/PhotoFileDao.java 변경
- com/eomcs/lms/dao/LessonDao.java 변경
- com/eomcs/lms/dao/PhotoBoardDao.java 변경
- com/eomcs/lms/handler/MemberUpdateCommand.java 변경
- com/eomcs/lms/handler/PhotoAddCommand.java 변경
- com/eomcs/lms/handler/PhotoUpdateCommand.java 변경
- com/eomcs/lms/handler/LessonSearchCommand.java 추가
- com/eomcs/lms/handler/PhotoBoardDetail2Command.java 추가
- com/eomcs/lms/App.java 변경