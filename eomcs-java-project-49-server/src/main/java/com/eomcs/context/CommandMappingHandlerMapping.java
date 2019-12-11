package com.eomcs.context;

import java.lang.reflect.Method;
import java.util.HashMap;

public class CommandMappingHandlerMapping {
  
  HashMap<String,HandlerMethod> handlerMap = new HashMap<>();
  
  public void addHandlerMethod(String name, Object bean, Method method) {
    handlerMap.put(name, new HandlerMethod(bean, method));
  }
  
  public HandlerMethod getHandlerMethod(String name) {
    return handlerMap.get(name);
  }
  
  public static class HandlerMethod {
    public Object bean;
    public Method method;
    
    public HandlerMethod(Object bean, Method method) {
      this.bean = bean;
      this.method = method;
    }
  }
  
  
}
