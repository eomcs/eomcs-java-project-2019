package com.eomcs.stereotype;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // 실행 시에 애노테이션을 추출할 수 있다.
@Target(ElementType.TYPE) // 타입에만 붙일 수 있다.
public @interface Component {
  
  String value() default ""; // 객체의 이름을 지정하는 프로퍼티. 선택 항목. 기본 값은 빈 문자열.
  
}
