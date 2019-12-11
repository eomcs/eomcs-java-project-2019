package com.eomcs.lms;

import java.lang.reflect.Method;
import java.util.Map;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.eomcs.context.ApplicationContextListener;
import com.eomcs.context.CommandMappingHandlerMapping;
import com.eomcs.stereotype.CommandMapping;

public class ContextLoaderListener implements ApplicationContextListener {

  @Override
  public void contextInitialized(Map<String,Object> context) {
    System.out.println("ContextLoaderListener.contextInitialized() 실행!");

    try {
      
      // IoC 컨테이너를 생성한다.
      ApplicationContext iocContainer = 
          new AnnotationConfigApplicationContext(AppConfig.class);
      
      context.put("iocContainer", iocContainer);
      
      // @CommandMapping 애노테이션이 붙은 메서드를 찾아 따로 보관한다.
      CommandMappingHandlerMapping handlerMapping = createCommandMappingHandlerMapping(iocContainer);
      
      context.put("handlerMapping", handlerMapping);
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  private CommandMappingHandlerMapping createCommandMappingHandlerMapping(ApplicationContext iocContainer) throws Exception {
    CommandMappingHandlerMapping handlerMapping = new CommandMappingHandlerMapping();
    
    String[] beanNames = iocContainer.getBeanDefinitionNames();
    for (String beanName : beanNames) {
      Object bean = iocContainer.getBean(beanName);
      System.out.printf("[%s]\n", bean.getClass().getName());
      
      Method[] methods = bean.getClass().getMethods();
      for (Method method : methods) {
        CommandMapping commandMappingAnno = method.getAnnotation(CommandMapping.class);
        if (commandMappingAnno != null) {
          System.out.printf("==> %s : %s()\n", commandMappingAnno.value(), method.getName());
          handlerMapping.addHandlerMethod(commandMappingAnno.value(), bean, method);
        }
      }
    }
    
    return handlerMapping;
  }

  @Override
  public void contextDestroyed(Map<String,Object> context) {
    System.out.println("ContextLoaderListener.contextInitialized() 실행!");
  }
}
