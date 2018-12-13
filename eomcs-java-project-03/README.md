# 03 - `GitHub.com`에 프로젝트 공유하기

## 학습 목표

- `.gitignore` 파일의 용도와 작성하는 방법
- `github.com`의 저장소에 프로젝트를 공유하는 방법

## 실습 결과

`https://github.com/사용자이름/eomcs-study` 웹 페이지에 가면 `eomcs-java-project` 프로젝트가 올라가 있는 것을 보게 된다.

## 실습

### 작업1) Git 저장소에 보관하지 않을 파일과 디렉토리 지정

```
~/git/eomcs-study/.gitignore
```

### 작업2) 저장소에 있는 파일과 디렉토리를 *Git* 관리 대상에 추가

```
[~/git/eomcs-study]$ git add .
```

### 작업3) 관리 대상에 등록된 파일을 로컬 *Git* 저장소에 보관

```
[~/git/eomcs-study]$ git commit -m "git 저장소에 프로젝트 보관"
```

### 작업4) 원격 *github.com* 저장소에 보관

```
[~/git/eomcs-study]$ git push
```
