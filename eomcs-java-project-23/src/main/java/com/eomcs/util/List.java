package com.eomcs.util;

public interface List<E> {
  void add(E obj);
  E get(int index);
  E set(int index, E obj);
  E remove(int index);
  E[] toArray(E[] a);
  int size();
}
