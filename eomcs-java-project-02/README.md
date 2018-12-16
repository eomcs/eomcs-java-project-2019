# 02 - `이클립스 IDE`로 프로젝트 다루기

## 학습 목표

- `그레이들`을 이용하여 `이클립스 IDE`용 프로젝트로 전환할 수 있다.
- `이클립스` 워크스페이스로 프로젝트를 가져올 수 있다.
- `이클립스`에서 빌드하고 실행할 수 있다.

## 실습 소스 및 결과

- ~/git/eomcs-study/eomcs-java-project/build.gradle (변경)

이클립스 IDE로 import 한 후 애플리케이션을 실행한다.

## 실습

### 작업1) 그레이들의 빌드 스크립트 파일에 이클립스 플러그인을 추가하라.

build.gradle 파일에 'eclipse' 플러그인을 추가한다.

```
plugins {
    id 'java'
    id 'application'
    id 'eclipse'
}
```

### 작업2) 이클립스 IDE에서 사용할 프로젝트 설정 파일을 생성하라.

```
[~/git/eomcs-study/eomcs-java-project]$ gradle eclipse
```

### 작업3) 이클립스 IDE의 워크스페이스로 프로젝트를 가져와라.

이클립스 워크스페이스로 가져온다.

### 작업4) 이클립스 IDE에서 프로젝트를 실행하라.

이클립스 메뉴를 통해 App 클래스를 실행한다.
