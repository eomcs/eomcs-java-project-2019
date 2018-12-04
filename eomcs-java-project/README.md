# eomcs-java-project : v01

자바 애플리케이션 프로젝트 만들기

## 학습목표

- `그레이들`을 이용하여 프로젝트 디렉토리를 구성하는 방법
- `아파치 메이븐` 프로젝트의 디렉토리 구조를 이해하기
- `그레이들`로 빌드하고 실행하는 방법

## 실습

### 프로젝트 디렉토리 및 설정 파일 생성
```
[~/git/java-project]$ gradle init
```

### 설정 파일을 둘 디렉토리 생성

```
[~/git/java-project]$ cd src/main
[~/git/java-project/src/main]$ mkdir resources
[~/git/java-project/src/main]$ cd ../test
[~/git/java-project/src/test]$ mkdir resources
```

### Gradle 설정 파일 편집

build.gradle 파일 편집

### 프로젝트 빌드하기

```
[~/git/java-project]$ gradle build
```

### 프로젝트 실행하기

```
[~/git/java-project]$ gradle run
```
