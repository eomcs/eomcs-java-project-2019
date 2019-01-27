package com.eomcs.util;

public class Queue<E> extends LinkedList<E> implements Cloneable {

  @Override
  public Queue<E> clone() {
    // Queue의 수퍼 클래스인 LinkedList가 복제를 허락하지 않았기 때문에
    // 자식 클래스에서는 직접 복제 기능을 구현해야 한다.
    Queue<E> temp = new Queue<>();
    for (int i = 0; i < this.size(); i++) {
      temp.offer(this.get(i));
    }
    return temp;
  }
  
  public void offer(E value) {
    add(value);
  }
  
  public E poll() {
    if (size() > 0)
      return remove(0);
    return null;
  }
  
  public boolean empty() {
    return this.size() == 0;
  }
  
  public Iterator<E> iterator() {
    return new Iterator<>() {
      
      Queue<E> temp = Queue.this.clone();
      
      @Override
      public boolean hasNext() {
        return temp.size() > 0;
      }

      @Override
      public E next() {
        return temp.poll();
      }
    };
  }
  
/*
  public static void main(String[] args) throws Exception {
    Queue<String> queue = new Queue<>();
    queue.offer("aaa");
    queue.offer("bbb");
    queue.offer("ccc");
    queue.offer("ddd");
    queue.offer("eee");
    queue.offer("fff");
    
    Queue<String> temp1 = queue.clone();
    while (temp1.size() > 0) {
      System.out.println(temp1.poll());
    }
    System.out.println("----------------------");
    
    Queue<String> temp2 = queue.clone();
    while (temp2.size() > 0) {
      System.out.println(temp2.poll());
    }
  }
*/
}
