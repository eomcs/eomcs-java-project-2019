// 데이터 목록을 일관된 방법으로 꺼낼 수 있게 정의한 사용 규칙
package com.eomcs.util;

public interface Iterator<E> {
  boolean hasNext();
  E next();
}
