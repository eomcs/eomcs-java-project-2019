package com.eomcs.lms.handler;

import java.util.Arrays;
import com.eomcs.lms.domain.Member;

public class MemberList {
  static final int DEFAULT_CAPACITY = 10;
  Member[] list;
  int size = 0;

  public MemberList() {
    list  = new Member[DEFAULT_CAPACITY];
  }

  public MemberList(int initialCapacity) {
    if (initialCapacity > DEFAULT_CAPACITY)
      list = new Member[initialCapacity];
    else
      list = new Member[DEFAULT_CAPACITY];
  }

  public Member[] toArray() {
    return Arrays.copyOf(list, size);
  }

  public void add(Member member) {
    if (size >= list.length) {
      int oldCapacity = list.length;
      int newCapacity = oldCapacity + (oldCapacity >> 1);
      list = Arrays.copyOf(list, newCapacity);
    }

    list[size++] = member;
  }
}
