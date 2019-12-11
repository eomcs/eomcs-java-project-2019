package com.eomcs.lms;

import java.lang.reflect.Method;
import java.util.Map;
import org.springframework.aop.support.AopUtils;
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

      Class<?> originClass = null;

      if (AopUtils.isAopProxy(bean)) { // AOP 프록시 객체일 경우 원본 클래스를 알아내기
        Method m = bean.getClass().getMethod("getTargetClass");
        originClass = (Class<?>)m.invoke(bean);
      }

      Method[] methods = bean.getClass().getMethods();
      for (Method method : methods) {
        CommandMapping commandMappingAnno = null;
        if (originClass == null) {
          commandMappingAnno = method.getAnnotation(CommandMapping.class);
        } else {
          try {
            // 프록시 객체일 경우 원본 클래스의 메서드에서 @CommandMapping 애노테이션 정보를 찾아본다.
            Method originMethod = originClass.getMethod(method.getName(), method.getParameterTypes());
            commandMappingAnno = originMethod.getAnnotation(CommandMapping.class);
          } catch (NoSuchMethodException e) {
            // 원본 클래스에 없는 메서드라면 무시한다.
          }
        }
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
