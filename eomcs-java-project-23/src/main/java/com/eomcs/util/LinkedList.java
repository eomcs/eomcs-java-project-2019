package com.eomcs.util;

import java.lang.reflect.Array;

public class LinkedList<E> implements List<E> {

  private Node<E> first;
  private Node<E> last;
  private int size;
  
  @SuppressWarnings("unchecked")
  public E[] toArray(E[] a) {
    if (a.length < this.size)
      a = (E[]) Array.newInstance(a.getClass().getComponentType(), this.size);
   
    int i = 0;
    for (Node<E> node = first; node != null; node = node.next) {
      a[i++] = node.value;
    }
    return a;
  }
  
  public void add(E obj) {
    this.size++;
    Node<E> node = new Node<E>(last, obj, null);
    
    if (first == null) {
      last = first = node;
      return;
    }
    
    last.next = node;
    last = node;
  }
  
  public E get(int index) {
    if (index < 0 || index >= size) 
      return null;
    
    Node<E> node = first;
    for (int i = 1; i <= index; i++) {
      node = node.next;
    }
    
    return node.value;
  }

  public E set(int index, E obj) {
    if (index < 0 || index >= size)
      return null;
    
    Node<E> node = first;
    
    for (int i = 1; i <= index; i++) {
      node = node.next;
    }
    
    E oldValue = node.value;
    node.value = obj;
    
    return oldValue;
  }
  
  public E remove(int index) {
    if (index < 0 || index >= size)
      return null;
    
    this.size--;
    
    if (size == 0) {
      E deletedValue = first.value;
      first = last = null;
      return deletedValue;
    }
    
    Node<E> node = first;
    
    for (int i = 1; i <= index; i++) {
      node = node.next;
    }
    
    if (node == first) {
      first = node.next;
      first.prev = null; // 첫 번째 노드이면 이전 노드를 가리키는 레퍼런스는 null 이어야 한다.
    } else if (node == last) {
      last = node.prev;
      last.next = null; // 마지막 노드이면 다음 노드를 가리키는 레퍼런스는 null 이어야 한다.
    } else { 
      node.prev.next = node.next;  // 삭제할 노드의 이전 노드는 삭제할 노드의 다음 노드를 가리킨다. 
      node.next.prev = node.prev;  // 삭제할 노드의 다음 노드는 삭제할 노드의 이전 노드를 가리킨다.
    }
    
    E deletedValue = node.value;
    
    node.value = null; // help GC
    node.next = null;
    node.prev = null;

    return deletedValue;
  }
  
  public int size() {
    return size;
  }
  
  private static class Node<E> {
    E value;
    Node<E> prev;
    Node<E> next;
    
    Node(Node<E> prev, E value, Node<E> next) {
      this.prev = prev;
      this.value = value;
      this.next = next;
    }
  }
/*
  public static void main(String[] args) {
    LinkedList<String> list = new LinkedList<>();
    
    list.add("aaa");
    list.add("bbb");
    list.add("ccc");
    list.add("ddd");
    list.add("eee");
    list.add("fff");

    print(list);
    
    //System.out.println(list.get(0));
//    System.out.println(list.set(0, "xxx"));
//    System.out.println(list.set(2, "xxx"));
//    System.out.println(list.set(5, "xxx"));
    System.out.println(list.remove(5));
    System.out.println(list.remove(2));
    System.out.println(list.remove(0));
    
    print(list);
    print(list.toArray(new String[list.size()]));
  }
  
  private static void print(LinkedList<?> ll) {
    System.out.println("-------------------------");
    for (int i = 0; i < ll.size(); i++) {
      System.out.println(ll.get(i));
    }
    System.out.println("-------------------------");
  }
  
  private static <T> void print(T[] a) {
    System.out.println("-------------------------");
    
    for (int i = 0; i < a.length; i++) {
      System.out.println(a[i]);
    }
    System.out.println("-------------------------");
  }
*/
}
