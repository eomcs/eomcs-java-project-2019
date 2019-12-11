# eomcs-java-project-6.2-server

Log4j를 사용하여 애플리케이션 로그 처리하기

- Log4j를 설정하고 이용하는 방법
- Log4j2를 설정하고 이용하는 방법

## 프로젝트 - 수업관리 시스템  

애플리케이션의 실행 상태를 확인하고 싶을 때 보통 `System.out.println()`을 사용하여 변수의 값이나 
메서드의 리턴 값, 객체의 필드 값을 출력한다. 
이 방식의 문제는 개발을 완료한 후 이런 코드를 찾아 제거하기가 매우 번거롭다는 것이다. 
또한 콘솔 출력이 아닌 파일이나 네트웍으로 출력하려면 별개의 코드를 작성해야 한다.
이런 문제점을 해결하기 위해 나온 것이 `Log4j`라는 라이브러리이다.
개발 중에는 로그를 자세하게 출력하고 개발이 완료된 후에는 중요 로그만 출력하도록 조정하는 기능을 제공한다.
로그의 출력 형식을 지정할 수 있다. 출력 대상도 콘솔, 파일, 네트워크, DB 등 다양하게 지정할 수 있다.

### ver 6.2.0 - `System.out.println()` 대신에 Log4j를 적용하여 로그를 출력하라.

#### 1단계) Log4j 라이브러리를 추가한다.

- 라이브러리 정보 알아내기
    - `mvnrepository.com`에서 `log4j`를 검색한다.
- build.gradle
    - `log4j` 라이브러리 정보를 추가한다.
    - `$ gradle eclipse`를 실행하여 이클립스 설정 파일을 갱신한다.
    - 이클립스 워크스페이스에 로딩되어 있는 클래스를 갱신한다.

#### 2단계) Log4j 설정 파일을 추가한다.

- src/main/resources 
    - 애플리케이션이 실행 중에 사용하는 `.properties`, `.xml`, `.txt` 와 같은 설정 파일을 두는 디렉토리이다.
    - 디렉토리를 추가한 후, `$ gradle eclipse` 를 다시 실행하여 이클립스 설정 파일을 갱신한다.
    - 그래야만 `resources` 폴더가 소스 폴더가 된다.
- log4j.properties
    - `Log4j` 의 출력 대상, 출력 형식 등을 정의한 설정 파일이다.
    - 자바 CLASSPATH의 루트 디렉토리에 파일을 둔다.
- AppConfig.java
    - `SqlSessionFactory`를 생성할 때 MyBatis에서 사용할 로깅 엔진을 `LOG4J`로 지정한다.


#### 3단계) 각 클래스의 로그 출력을 Log4j로 전환한다.

- App.java
    - 기존에는 `System.out.println()`을 사용하여 출력하였다.
    - Log4j로 전환한다.
- ContextLoaderListener.java
    - 기존에는 `System.out.println()`을 사용하여 출력하였다.
    - Log4j로 전환한다.



### ver 6.2.1 - `Log4j 1.x` 라이브러리를 `Log4j 2` 라이브러리로 전환하라.

#### 1단계) Log4j 2 라이브러리를 추가한다.

- 라이브러리 정보 알아내기
    - `mvnrepository.com`에서 `log4j core`를 검색한다.
- build.gradle
    - `log4j core` 라이브러리 정보를 추가한다.
    - 기존의 `log4j 1.x` 라이브러리 정보를 제거한다.
    - `$ gradle eclipse`를 실행하여 이클립스 설정 파일을 갱신한다.
    - 이클립스 워크스페이스에 로딩되어 있는 클래스를 갱신한다.

#### 2단계) Log4j 설정 파일을 추가한다.

- log4j.properties
    - 파일을 제거한다.
- log4j2.xml
    - 자바 CLASSPATH 루트 디렉토리(src/main/resources)에 이 파일을 둔다.
- AppConfig.java
    - `SqlSessionFactory`를 생성할 때 MyBatis에서 사용할 로깅 엔진을 `LOG4J2`로 지정한다.
    - `LOG4J2`를 사용할 경우 지정하지 않아도 된다. 


##### 실습 결과

`eomcs-java-project-server` 프로젝트의 `App` 클래스를 실행한다.
```
[DEBUG] 2018-12-01 15:47:19.087 [main] ContextLoaderListener - DataLoaderListener.contextInitialized() 실행!
[DEBUG] 2018-12-01 15:47:19.195 [main] AnnotationConfigApplicationContext - Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@1e4d3ce5
[DEBUG] 2018-12-01 15:47:19.205 [main] DefaultListableBeanFactory - Creating shared instance of singleton bean 'org.springframework.context.annotation.internalConfigurationAnnotationProcessor'
[DEBUG] 2018-12-01 15:47:19.269 [main] ClassPathBeanDefinitionScanner - Identified candidate component class: file [/Users/eomjinyoung/git/eomcs-java-project/eomcs-java-project-6.2.1-server/bin/main/com/eomcs/lms/handler/AuthHandler.class]
[DEBUG] 2018-12-01 15:47:19.270 [main] ClassPathBeanDefinitionScanner - Identified candidate component class: file [/Users/eomjinyoung/git/eomcs-java-project/eomcs-java-project-6.2.1-server/bin/main/com/eomcs/lms/handler/BoardHandler.class]
[DEBUG] 2018-12-01 15:47:19.271 [main] ClassPathBeanDefinitionScanner - Identified candidate component class: file [/Users/eomjinyoung/git/eomcs-java-project/eomcs-java-project-6.2.1-server/bin/main/com/eomcs/lms/handler/LessonHandler.class]
[DEBUG] 2018-12-01 15:47:19.272 [main] ClassPathBeanDefinitionScanner - Identified candidate component class: file [/Users/eomjinyoung/git/eomcs-java-project/eomcs-java-project-6.2.1-server/bin/main/com/eomcs/lms/handler/MemberHandler.class]
[DEBUG] 2018-12-01 15:47:19.275 [main] ClassPathBeanDefinitionScanner - Identified candidate component class: file [/Users/eomjinyoung/git/eomcs-java-project/eomcs-java-project-6.2.1-server/bin/main/com/eomcs/lms/handler/PhotoBoardHandler.class]
[DEBUG] 2018-12-01 15:47:19.314 [main] ClassPathMapperScanner - Identified candidate component class: file [/Users/eomjinyoung/git/eomcs-java-project/eomcs-java-project-6.2.1-server/bin/main/com/eomcs/lms/dao/BoardDao.class]
[DEBUG] 2018-12-01 15:47:19.314 [main] ClassPathMapperScanner - Identified candidate component class: file [/Users/eomjinyoung/git/eomcs-java-project/eomcs-java-project-6.2.1-server/bin/main/com/eomcs/lms/dao/LessonDao.class]
[DEBUG] 2018-12-01 15:47:19.314 [main] ClassPathMapperScanner - Identified candidate component class: file [/Users/eomjinyoung/git/eomcs-java-project/eomcs-java-project-6.2.1-server/bin/main/com/eomcs/lms/dao/MemberDao.class]
[DEBUG] 2018-12-01 15:47:19.314 [main] ClassPathMapperScanner - Identified candidate component class: file [/Users/eomjinyoung/git/eomcs-java-project/eomcs-java-project-6.2.1-server/bin/main/com/eomcs/lms/dao/PhotoBoardDao.class]
[DEBUG] 2018-12-01 15:47:19.315 [main] ClassPathMapperScanner - Identified candidate component class: file [/Users/eomjinyoung/git/eomcs-java-project/eomcs-java-project-6.2.1-server/bin/main/com/eomcs/lms/dao/PhotoFileDao.class]
[DEBUG] 2018-12-01 15:47:19.315 [main] ClassPathMapperScanner - Creating MapperFactoryBean with name 'boardDao' and 'com.eomcs.lms.dao.BoardDao' mapperInterface
[DEBUG] 2018-12-01 15:47:19.316 [main] ClassPathMapperScanner - Enabling autowire by type for MapperFactoryBean with name 'boardDao'.
[DEBUG] 2018-12-01 15:47:19.316 [main] ClassPathMapperScanner - Creating MapperFactoryBean with name 'lessonDao' and 'com.eomcs.lms.dao.LessonDao' mapperInterface
[DEBUG] 2018-12-01 15:47:19.316 [main] ClassPathMapperScanner - Enabling autowire by type for MapperFactoryBean with name 'lessonDao'.
[DEBUG] 2018-12-01 15:47:19.316 [main] ClassPathMapperScanner - Creating MapperFactoryBean with name 'memberDao' and 'com.eomcs.lms.dao.MemberDao' mapperInterface
[DEBUG] 2018-12-01 15:47:19.316 [main] ClassPathMapperScanner - Enabling autowire by type for MapperFactoryBean with name 'memberDao'.
[DEBUG] 2018-12-01 15:47:19.316 [main] ClassPathMapperScanner - Creating MapperFactoryBean with name 'photoBoardDao' and 'com.eomcs.lms.dao.PhotoBoardDao' mapperInterface
[DEBUG] 2018-12-01 15:47:19.316 [main] ClassPathMapperScanner - Enabling autowire by type for MapperFactoryBean with name 'photoBoardDao'.
[DEBUG] 2018-12-01 15:47:19.317 [main] ClassPathMapperScanner - Creating MapperFactoryBean with name 'photoFileDao' and 'com.eomcs.lms.dao.PhotoFileDao' mapperInterface
[DEBUG] 2018-12-01 15:47:19.317 [main] ClassPathMapperScanner - Enabling autowire by type for MapperFactoryBean with name 'photoFileDao'.
[DEBUG] 2018-12-01 15:47:19.402 [main] DefaultListableBeanFactory - Creating shared instance of singleton bean 'org.springframework.context.event.internalEventListenerProcessor'
[DEBUG] 2018-12-01 15:47:19.405 [main] DefaultListableBeanFactory - Creating shared instance of singleton bean 'org.springframework.context.event.internalEventListenerFactory'
[DEBUG] 2018-12-01 15:47:19.405 [main] DefaultListableBeanFactory - Creating shared instance of singleton bean 'org.springframework.transaction.config.internalTransactionalEventListenerFactory'
[DEBUG] 2018-12-01 15:47:19.410 [main] DefaultListableBeanFactory - Creating shared instance of singleton bean 'org.springframework.context.annotation.internalAutowiredAnnotationProcessor'
[DEBUG] 2018-12-01 15:47:19.410 [main] DefaultListableBeanFactory - Creating shared instance of singleton bean 'org.springframework.aop.config.internalAutoProxyCreator'
[DEBUG] 2018-12-01 15:47:19.454 [main] DefaultListableBeanFactory - Creating shared instance of singleton bean 'appConfig'
[DEBUG] 2018-12-01 15:47:19.469 [main] DefaultListableBeanFactory - Creating shared instance of singleton bean 'org.springframework.transaction.config.internalTransactionAdvisor'
[DEBUG] 2018-12-01 15:47:19.469 [main] DefaultListableBeanFactory - Creating shared instance of singleton bean 'org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration'
[DEBUG] 2018-12-01 15:47:19.492 [main] DefaultListableBeanFactory - Creating shared instance of singleton bean 'transactionAttributeSource'
[DEBUG] 2018-12-01 15:47:19.496 [main] DefaultListableBeanFactory - Creating shared instance of singleton bean 'transactionInterceptor'
[DEBUG] 2018-12-01 15:47:19.503 [main] DefaultListableBeanFactory - Creating shared instance of singleton bean 'authHandler'
[DEBUG] 2018-12-01 15:47:19.506 [main] DefaultListableBeanFactory - Creating shared instance of singleton bean 'memberDao'
[DEBUG] 2018-12-01 15:47:19.513 [main] DefaultListableBeanFactory - Creating shared instance of singleton bean 'sqlSessionFactory'
[DEBUG] 2018-12-01 15:47:19.514 [main] DefaultListableBeanFactory - Creating shared instance of singleton bean 'dataSource'
[DEBUG] 2018-12-01 15:47:19.532 [main] PropertySourcesPropertyResolver - Found key 'jdbc.driver' in PropertySource 'class path resource [com/eomcs/lms/conf/jdbc.properties]' with value of type String
[DEBUG] 2018-12-01 15:47:19.532 [main] PropertySourcesPropertyResolver - Found key 'jdbc.url' in PropertySource 'class path resource [com/eomcs/lms/conf/jdbc.properties]' with value of type String
[DEBUG] 2018-12-01 15:47:19.532 [main] PropertySourcesPropertyResolver - Found key 'jdbc.username' in PropertySource 'class path resource [com/eomcs/lms/conf/jdbc.properties]' with value of type String
[DEBUG] 2018-12-01 15:47:19.532 [main] PropertySourcesPropertyResolver - Found key 'jdbc.password' in PropertySource 'class path resource [com/eomcs/lms/conf/jdbc.properties]' with value of type String
[DEBUG] 2018-12-01 15:47:19.550 [main] DefaultListableBeanFactory - Autowiring by type from bean name 'sqlSessionFactory' via factory method to bean named 'dataSource'
[DEBUG] 2018-12-01 15:47:19.550 [main] DefaultListableBeanFactory - Autowiring by type from bean name 'sqlSessionFactory' via factory method to bean named 'org.springframework.context.annotation.AnnotationConfigApplicationContext@1e4d3ce5'
[DEBUG] 2018-12-01 15:47:19.553 [main] LogFactory - Logging initialized using 'class org.apache.ibatis.logging.commons.JakartaCommonsLoggingImpl' adapter.
[DEBUG] 2018-12-01 15:47:19.556 [main] SqlSessionFactoryBean - Property 'configuration' or 'configLocation' not specified, using default MyBatis Configuration
[DEBUG] 2018-12-01 15:47:19.590 [main] VFS - Class not found: org.jboss.vfs.VFS
[DEBUG] 2018-12-01 15:47:19.590 [main] JBoss6VFS - JBoss 6 VFS API is not available in this environment.
[DEBUG] 2018-12-01 15:47:19.591 [main] VFS - Class not found: org.jboss.vfs.VirtualFile
[DEBUG] 2018-12-01 15:47:19.591 [main] VFS - VFS implementation org.apache.ibatis.io.JBoss6VFS is not valid in this environment.
[DEBUG] 2018-12-01 15:47:19.591 [main] VFS - Using VFS adapter org.apache.ibatis.io.DefaultVFS
[DEBUG] 2018-12-01 15:47:19.592 [main] DefaultVFS - Find JAR URL: file:/Users/eomjinyoung/git/eomcs-java-project/eomcs-java-project-6.2.1-server/bin/main/com/eomcs/lms/domain
[DEBUG] 2018-12-01 15:47:19.592 [main] DefaultVFS - Not a JAR: file:/Users/eomjinyoung/git/eomcs-java-project/eomcs-java-project-6.2.1-server/bin/main/com/eomcs/lms/domain
[DEBUG] 2018-12-01 15:47:19.657 [main] DefaultVFS - Reader entry: Board.class
[DEBUG] 2018-12-01 15:47:19.658 [main] DefaultVFS - Reader entry: Lesson.class
[DEBUG] 2018-12-01 15:47:19.658 [main] DefaultVFS - Reader entry: Member.class
[DEBUG] 2018-12-01 15:47:19.658 [main] DefaultVFS - Reader entry: PhotoBoard.class
[DEBUG] 2018-12-01 15:47:19.659 [main] DefaultVFS - Reader entry: PhotoFile.class
[DEBUG] 2018-12-01 15:47:19.659 [main] DefaultVFS - Listing file:/Users/eomjinyoung/git/eomcs-java-project/eomcs-java-project-6.2.1-server/bin/main/com/eomcs/lms/domain
[DEBUG] 2018-12-01 15:47:19.659 [main] DefaultVFS - Find JAR URL: file:/Users/eomjinyoung/git/eomcs-java-project/eomcs-java-project-6.2.1-server/bin/main/com/eomcs/lms/domain/Board.class
[DEBUG] 2018-12-01 15:47:19.659 [main] DefaultVFS - Not a JAR: file:/Users/eomjinyoung/git/eomcs-java-project/eomcs-java-project-6.2.1-server/bin/main/com/eomcs/lms/domain/Board.class
[DEBUG] 2018-12-01 15:47:19.660 [main] DefaultVFS - Reader entry: ����   7 i  com/eomcs/lms/domain/Board  java/lang/Object  java/lang/Cloneable  java/io/Serializable serialVersionUID J 
[DEBUG] 2018-12-01 15:47:19.663 [main] DefaultVFS - Find JAR URL: file:/Users/eomjinyoung/git/eomcs-java-project/eomcs-java-project-6.2.1-server/bin/main/com/eomcs/lms/domain/Lesson.class
[DEBUG] 2018-12-01 15:47:19.663 [main] DefaultVFS - Not a JAR: file:/Users/eomjinyoung/git/eomcs-java-project/eomcs-java-project-6.2.1-server/bin/main/com/eomcs/lms/domain/Lesson.class
[DEBUG] 2018-12-01 15:47:19.664 [main] DefaultVFS - Reader entry: ����   7 w  com/eomcs/lms/domain/Lesson  java/lang/Object  java/lang/Cloneable  java/io/Serializable serialVersionUID J 
[DEBUG] 2018-12-01 15:47:19.665 [main] DefaultVFS - Find JAR URL: file:/Users/eomjinyoung/git/eomcs-java-project/eomcs-java-project-6.2.1-server/bin/main/com/eomcs/lms/domain/Member.class
[DEBUG] 2018-12-01 15:47:19.665 [main] DefaultVFS - Not a JAR: file:/Users/eomjinyoung/git/eomcs-java-project/eomcs-java-project-6.2.1-server/bin/main/com/eomcs/lms/domain/Member.class
[DEBUG] 2018-12-01 15:47:19.666 [main] DefaultVFS - Reader entry: ����   7 p  com/eomcs/lms/domain/Member  java/lang/Object  java/lang/Cloneable  java/io/Serializable serialVersionUID J 
[DEBUG] 2018-12-01 15:47:19.667 [main] DefaultVFS - Find JAR URL: file:/Users/eomjinyoung/git/eomcs-java-project/eomcs-java-project-6.2.1-server/bin/main/com/eomcs/lms/domain/PhotoBoard.class
[DEBUG] 2018-12-01 15:47:19.667 [main] DefaultVFS - Not a JAR: file:/Users/eomjinyoung/git/eomcs-java-project/eomcs-java-project-6.2.1-server/bin/main/com/eomcs/lms/domain/PhotoBoard.class
[DEBUG] 2018-12-01 15:47:19.668 [main] DefaultVFS - Reader entry: ����   7 o  com/eomcs/lms/domain/PhotoBoard  java/lang/Object  java/lang/Cloneable  java/io/Serializable serialVersionUID J 
[DEBUG] 2018-12-01 15:47:19.669 [main] DefaultVFS - Find JAR URL: file:/Users/eomjinyoung/git/eomcs-java-project/eomcs-java-project-6.2.1-server/bin/main/com/eomcs/lms/domain/PhotoFile.class
[DEBUG] 2018-12-01 15:47:19.669 [main] DefaultVFS - Not a JAR: file:/Users/eomjinyoung/git/eomcs-java-project/eomcs-java-project-6.2.1-server/bin/main/com/eomcs/lms/domain/PhotoFile.class
[DEBUG] 2018-12-01 15:47:19.669 [main] DefaultVFS - Reader entry: ����   7 N  com/eomcs/lms/domain/PhotoFile  java/lang/Object  java/io/Serializable  java/lang/Cloneable serialVersionUID J 
[DEBUG] 2018-12-01 15:47:19.670 [main] ResolverUtil - Checking to see if class com.eomcs.lms.domain.Board matches criteria [is assignable to Object]
[DEBUG] 2018-12-01 15:47:19.671 [main] ResolverUtil - Checking to see if class com.eomcs.lms.domain.Lesson matches criteria [is assignable to Object]
[DEBUG] 2018-12-01 15:47:19.671 [main] ResolverUtil - Checking to see if class com.eomcs.lms.domain.Member matches criteria [is assignable to Object]
[DEBUG] 2018-12-01 15:47:19.672 [main] ResolverUtil - Checking to see if class com.eomcs.lms.domain.PhotoBoard matches criteria [is assignable to Object]
[DEBUG] 2018-12-01 15:47:19.672 [main] ResolverUtil - Checking to see if class com.eomcs.lms.domain.PhotoFile matches criteria [is assignable to Object]
[DEBUG] 2018-12-01 15:47:19.673 [main] SqlSessionFactoryBean - Scanned package: 'com.eomcs.lms.domain' for aliases
[DEBUG] 2018-12-01 15:47:19.763 [main] SqlSessionFactoryBean - Parsed mapper file: 'file [/Users/eomjinyoung/git/eomcs-java-project/eomcs-java-project-6.2.1-server/bin/main/com/eomcs/lms/mapper/BoardMapper.xml]'
[DEBUG] 2018-12-01 15:47:19.780 [main] SqlSessionFactoryBean - Parsed mapper file: 'file [/Users/eomjinyoung/git/eomcs-java-project/eomcs-java-project-6.2.1-server/bin/main/com/eomcs/lms/mapper/LessonMapper.xml]'
[DEBUG] 2018-12-01 15:47:19.796 [main] SqlSessionFactoryBean - Parsed mapper file: 'file [/Users/eomjinyoung/git/eomcs-java-project/eomcs-java-project-6.2.1-server/bin/main/com/eomcs/lms/mapper/MemberMapper.xml]'
[DEBUG] 2018-12-01 15:47:19.812 [main] SqlSessionFactoryBean - Parsed mapper file: 'file [/Users/eomjinyoung/git/eomcs-java-project/eomcs-java-project-6.2.1-server/bin/main/com/eomcs/lms/mapper/PhotoBoardMapper.xml]'
[DEBUG] 2018-12-01 15:47:19.827 [main] SqlSessionFactoryBean - Parsed mapper file: 'file [/Users/eomjinyoung/git/eomcs-java-project/eomcs-java-project-6.2.1-server/bin/main/com/eomcs/lms/mapper/PhotoFileMapper.xml]'
[DEBUG] 2018-12-01 15:47:19.841 [main] DefaultListableBeanFactory - Autowiring by type from bean name 'authHandler' via constructor to bean named 'memberDao'
[DEBUG] 2018-12-01 15:47:19.841 [main] DefaultListableBeanFactory - Creating shared instance of singleton bean 'boardHandler'
[DEBUG] 2018-12-01 15:47:19.843 [main] DefaultListableBeanFactory - Creating shared instance of singleton bean 'boardDao'
[DEBUG] 2018-12-01 15:47:19.845 [main] DefaultListableBeanFactory - Autowiring by type from bean name 'boardHandler' via constructor to bean named 'boardDao'
[DEBUG] 2018-12-01 15:47:19.846 [main] DefaultListableBeanFactory - Creating shared instance of singleton bean 'lessonHandler'
[DEBUG] 2018-12-01 15:47:19.848 [main] DefaultListableBeanFactory - Creating shared instance of singleton bean 'lessonDao'
[DEBUG] 2018-12-01 15:47:19.850 [main] DefaultListableBeanFactory - Autowiring by type from bean name 'lessonHandler' via constructor to bean named 'lessonDao'
[DEBUG] 2018-12-01 15:47:19.850 [main] DefaultListableBeanFactory - Creating shared instance of singleton bean 'memberHandler'
[DEBUG] 2018-12-01 15:47:19.852 [main] DefaultListableBeanFactory - Autowiring by type from bean name 'memberHandler' via constructor to bean named 'memberDao'
[DEBUG] 2018-12-01 15:47:19.853 [main] DefaultListableBeanFactory - Creating shared instance of singleton bean 'photoBoardHandler'
[DEBUG] 2018-12-01 15:47:19.856 [main] DefaultListableBeanFactory - Creating shared instance of singleton bean 'photoBoardDao'
[DEBUG] 2018-12-01 15:47:19.859 [main] DefaultListableBeanFactory - Creating shared instance of singleton bean 'photoFileDao'
[DEBUG] 2018-12-01 15:47:19.860 [main] DefaultListableBeanFactory - Autowiring by type from bean name 'photoBoardHandler' via constructor to bean named 'photoBoardDao'
[DEBUG] 2018-12-01 15:47:19.861 [main] DefaultListableBeanFactory - Autowiring by type from bean name 'photoBoardHandler' via constructor to bean named 'photoFileDao'
[DEBUG] 2018-12-01 15:47:19.891 [main] DefaultListableBeanFactory - Creating shared instance of singleton bean 'transactionManager'
[DEBUG] 2018-12-01 15:47:19.891 [main] DefaultListableBeanFactory - Autowiring by type from bean name 'transactionManager' via factory method to bean named 'dataSource'
[DEBUG] 2018-12-01 15:47:19.921 [main] ContextLoaderListener - 커맨드 핸들러의 매핑 정보 준비하기
[DEBUG] 2018-12-01 15:47:19.921 [main] ContextLoaderListener - org.springframework.context.annotation.ConfigurationClassPostProcessor 클래스의 커맨드 매핑:
[DEBUG] 2018-12-01 15:47:19.922 [main] ContextLoaderListener - org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor 클래스의 커맨드 매핑:
[DEBUG] 2018-12-01 15:47:19.922 [main] ContextLoaderListener - org.springframework.context.event.EventListenerMethodProcessor 클래스의 커맨드 매핑:
[DEBUG] 2018-12-01 15:47:19.922 [main] ContextLoaderListener - org.springframework.context.event.DefaultEventListenerFactory 클래스의 커맨드 매핑:
[DEBUG] 2018-12-01 15:47:19.922 [main] ContextLoaderListener - com.eomcs.lms.AppConfig 클래스의 커맨드 매핑:
[DEBUG] 2018-12-01 15:47:19.923 [main] ContextLoaderListener - com.eomcs.lms.handler.AuthHandler 클래스의 커맨드 매핑:
[DEBUG] 2018-12-01 15:47:19.923 [main] ContextLoaderListener - ==> /auth/login : login()
[DEBUG] 2018-12-01 15:47:19.923 [main] ContextLoaderListener - com.eomcs.lms.handler.BoardHandler 클래스의 커맨드 매핑:
[DEBUG] 2018-12-01 15:47:19.923 [main] ContextLoaderListener - ==> /board/add : add()
[DEBUG] 2018-12-01 15:47:19.923 [main] ContextLoaderListener - ==> /board/update : update()
[DEBUG] 2018-12-01 15:47:19.923 [main] ContextLoaderListener - ==> /board/list : list()
[DEBUG] 2018-12-01 15:47:19.923 [main] ContextLoaderListener - ==> /board/delete : delete()
[DEBUG] 2018-12-01 15:47:19.924 [main] ContextLoaderListener - ==> /board/detail : detail()
[DEBUG] 2018-12-01 15:47:19.924 [main] ContextLoaderListener - com.eomcs.lms.handler.LessonHandler 클래스의 커맨드 매핑:
[DEBUG] 2018-12-01 15:47:19.924 [main] ContextLoaderListener - ==> /lesson/add : add()
[DEBUG] 2018-12-01 15:47:19.924 [main] ContextLoaderListener - ==> /lesson/update : update()
[DEBUG] 2018-12-01 15:47:19.924 [main] ContextLoaderListener - ==> /lesson/list : list()
[DEBUG] 2018-12-01 15:47:19.924 [main] ContextLoaderListener - ==> /lesson/delete : delete()
[DEBUG] 2018-12-01 15:47:19.924 [main] ContextLoaderListener - ==> /lesson/search : search()
[DEBUG] 2018-12-01 15:47:19.924 [main] ContextLoaderListener - ==> /lesson/detail : detail()
[DEBUG] 2018-12-01 15:47:19.925 [main] ContextLoaderListener - com.eomcs.lms.handler.MemberHandler 클래스의 커맨드 매핑:
[DEBUG] 2018-12-01 15:47:19.925 [main] ContextLoaderListener - ==> /member/add : add()
[DEBUG] 2018-12-01 15:47:19.925 [main] ContextLoaderListener - ==> /member/update : update()
[DEBUG] 2018-12-01 15:47:19.925 [main] ContextLoaderListener - ==> /member/list : list()
[DEBUG] 2018-12-01 15:47:19.925 [main] ContextLoaderListener - ==> /member/delete : delete()
[DEBUG] 2018-12-01 15:47:19.925 [main] ContextLoaderListener - ==> /member/search : search()
[DEBUG] 2018-12-01 15:47:19.926 [main] ContextLoaderListener - ==> /member/detail : detail()
[DEBUG] 2018-12-01 15:47:19.926 [main] ContextLoaderListener - com.eomcs.lms.handler.PhotoBoardHandler$$EnhancerBySpringCGLIB$$7733b417 클래스의 커맨드 매핑:
[DEBUG] 2018-12-01 15:47:19.926 [main] ContextLoaderListener - Spring AOP가 자동 생성한 프록시 객체이다.
[DEBUG] 2018-12-01 15:47:19.926 [main] ContextLoaderListener - ==> /photoboard/add : add()
[DEBUG] 2018-12-01 15:47:19.926 [main] ContextLoaderListener - ==> /photoboard/update : update()
[DEBUG] 2018-12-01 15:47:19.926 [main] ContextLoaderListener - ==> /photoboard/list : list()
[DEBUG] 2018-12-01 15:47:19.927 [main] ContextLoaderListener - ==> /photoboard/delete : delete()
[DEBUG] 2018-12-01 15:47:19.927 [main] ContextLoaderListener - ==> /photoboard/detail2 : detail2()
[DEBUG] 2018-12-01 15:47:19.927 [main] ContextLoaderListener - ==> /photoboard/detail : detail()
[DEBUG] 2018-12-01 15:47:19.927 [main] ContextLoaderListener - org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration$$EnhancerBySpringCGLIB$$517b7d41 클래스의 커맨드 매핑:
[DEBUG] 2018-12-01 15:47:19.927 [main] ContextLoaderListener - org.springframework.transaction.interceptor.BeanFactoryTransactionAttributeSourceAdvisor 클래스의 커맨드 매핑:
[DEBUG] 2018-12-01 15:47:19.927 [main] ContextLoaderListener - org.springframework.transaction.annotation.AnnotationTransactionAttributeSource 클래스의 커맨드 매핑:
[DEBUG] 2018-12-01 15:47:19.927 [main] ContextLoaderListener - org.springframework.transaction.interceptor.TransactionInterceptor 클래스의 커맨드 매핑:
[DEBUG] 2018-12-01 15:47:19.928 [main] ContextLoaderListener - org.springframework.transaction.event.TransactionalEventListenerFactory 클래스의 커맨드 매핑:
[DEBUG] 2018-12-01 15:47:19.928 [main] ContextLoaderListener - org.apache.commons.dbcp2.BasicDataSource 클래스의 커맨드 매핑:
[DEBUG] 2018-12-01 15:47:19.928 [main] ContextLoaderListener - org.apache.ibatis.session.defaults.DefaultSqlSessionFactory 클래스의 커맨드 매핑:
[DEBUG] 2018-12-01 15:47:19.928 [main] ContextLoaderListener - org.springframework.jdbc.datasource.DataSourceTransactionManager 클래스의 커맨드 매핑:
[DEBUG] 2018-12-01 15:47:19.928 [main] ContextLoaderListener - org.springframework.aop.framework.autoproxy.InfrastructureAdvisorAutoProxyCreator 클래스의 커맨드 매핑:
[DEBUG] 2018-12-01 15:47:19.928 [main] ContextLoaderListener - com.sun.proxy.$Proxy40 클래스의 커맨드 매핑:
[DEBUG] 2018-12-01 15:47:19.928 [main] ContextLoaderListener - com.sun.proxy.$Proxy41 클래스의 커맨드 매핑:
[DEBUG] 2018-12-01 15:47:19.928 [main] ContextLoaderListener - com.sun.proxy.$Proxy38 클래스의 커맨드 매핑:
[DEBUG] 2018-12-01 15:47:19.929 [main] ContextLoaderListener - com.sun.proxy.$Proxy43 클래스의 커맨드 매핑:
[DEBUG] 2018-12-01 15:47:19.929 [main] ContextLoaderListener - com.sun.proxy.$Proxy44 클래스의 커맨드 매핑:
[INFO ] 2018-12-01 15:47:19.938 [main] App - 서버 실행!
[INFO ] 2018-12-01 15:47:26.993 [main] App - 클라이언트와 연결되었음.
[DEBUG] 2018-12-01 15:47:26.993 [main] App - 스레드 생성됨!
[DEBUG] 2018-12-01 15:47:26.994 [pool-2-thread-1] App - 스레드 실행...
[DEBUG] 2018-12-01 15:47:26.996 [pool-2-thread-1] App - 명령어: /lesson/list
[DEBUG] 2018-12-01 15:47:27.004 [pool-2-thread-1] SqlSessionUtils - Creating a new SqlSession
[DEBUG] 2018-12-01 15:47:27.008 [pool-2-thread-1] SqlSessionUtils - SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@68b5f518] was not registered for synchronization because synchronization is not active
[DEBUG] 2018-12-01 15:47:27.038 [pool-2-thread-1] DataSourceUtils - Fetching JDBC Connection from DataSource
[DEBUG] 2018-12-01 15:47:27.101 [pool-2-thread-1] SpringManagedTransaction - JDBC Connection [441431244, URL=jdbc:mariadb://localhost:3306/eomcs, UserName=eomcs, MariaDB connector/J] will not be managed by Spring
[DEBUG] 2018-12-01 15:47:27.108 [pool-2-thread-1] list - ==>  Preparing: SELECT LNO, TITLE, SDT, EDT, TOT_HR FROM LESSON 
[DEBUG] 2018-12-01 15:47:27.138 [pool-2-thread-1] list - ==> Parameters: 
[DEBUG] 2018-12-01 15:47:27.162 [pool-2-thread-1] list - <==      Total: 6
[DEBUG] 2018-12-01 15:47:27.164 [pool-2-thread-1] SqlSessionUtils - Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@68b5f518]
[DEBUG] 2018-12-01 15:47:27.165 [pool-2-thread-1] DataSourceUtils - Returning JDBC Connection to DataSource
[INFO ] 2018-12-01 15:47:27.166 [pool-2-thread-1] App - 클라이언트와 연결 종료!
[DEBUG] 2018-12-01 15:47:27.167 [pool-2-thread-1] App - 스레드 종료!
[INFO ] 2018-12-01 15:47:30.083 [main] App - 클라이언트와 연결되었음.
[DEBUG] 2018-12-01 15:47:30.083 [main] App - 스레드 생성됨!
[DEBUG] 2018-12-01 15:47:30.083 [pool-2-thread-1] App - 스레드 실행...
[DEBUG] 2018-12-01 15:47:30.083 [pool-2-thread-1] App - 명령어: shutdown
[DEBUG] 2018-12-01 15:47:30.084 [pool-2-thread-1] ContextLoaderListener - DataLoaderListener.contextInitialized() 실행!
[INFO ] 2018-12-01 15:47:30.084 [pool-2-thread-1] App - 서버 종료!

```

## 실습 소스

- build.gradle 변경
- src/main/resources/log4j.properties 제거
- src/main/resources/log4j2.xml 추가
- com/eomcs/lms/AppConfig.java 변경