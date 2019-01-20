package com.eomcs.util;

import java.util.Arrays;

public class Stack<E> implements Cloneable {
  
  private static final int DEFAULT_SIZE = 10;
  
  private E[] list;
  private int size = 0;
  
  @SuppressWarnings("unchecked")
  public Stack() {
    list = (E[]) new Object[DEFAULT_SIZE];
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public Stack<E> clone() throws CloneNotSupportedException {
    return (Stack<E>) super.clone();
  }
  
  public void push(E obj) {
    if (size >= list.length) {
      // 스택이 꽉차면 크기를 50% 늘린다.
      int oldCapacity = list.length;
      int newCapacity = oldCapacity + (oldCapacity >> 1);
      list = Arrays.copyOf(list, newCapacity);
    }
    
    list[size++] = obj;
  }
  
  public E pop() {
    if (size == 0) 
      return null;
     
    return list[--size];
  }

  public int size() {
    return size;
  }
  
  public boolean empty() {
    return size == 0;
  }
  
/*
  public static void main(String[] args) throws Exception {
    Stack<String> stack = new Stack<>();
    stack.push("aaa");
    stack.push("bbb");
    stack.push("ccc");
    stack.push("ddd");
    stack.push("eee");
    stack.push("fff");
    stack.push("ggg");
    stack.push("hhh");
    stack.push("iii");
    stack.push("jjj");
    stack.push("kkk");
    stack.push("lll");
    
    Stack<String> temp1 = stack.clone();
    while (!temp1.empty()) {
      System.out.println(temp1.pop());
    }
    System.out.println("----------------------");
    
    Stack<String> temp2 = stack.clone();
    while (!temp2.empty()) {
      System.out.println(temp2.pop());
    }
  }
*/
}
