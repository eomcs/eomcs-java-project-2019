package com.eomcs.context;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.dao.MemberDao;
import com.eomcs.lms.dao.PhotoBoardDao;
import com.eomcs.lms.dao.PhotoFileDao;
import com.eomcs.sql.MybatisDaoFactory;
import com.eomcs.sql.SqlSessionFactoryProxy;
import com.eomcs.sql.TransactionManager;
import com.eomcs.stereotype.Component;

public class ApplicationContext {

  ArrayList<Class<?>> classes = new ArrayList<>();
  HashMap<String,Object> objPool = new HashMap<>();
  String packageName;

  public ApplicationContext(String packageName) throws Exception {
    this.packageName = packageName;
    
    // 패키지 이름을 파일 경로로 바꾼다. 
    // 예) com.eomcs.lms --> com/eomcs/lms
    String packagePath = packageName.replace(".",  "/");

    // 패키지 경로를 가지고 전체 경로를 알아낸다. 
    // 예) /Users/eomjinyoung/git/eomcs-java-project/eomcs-java-project-5.5.0-server/bin/main/com/eomcs/lms
    // - MyBatis 라이브러리에 있는 클래스를 이용한다.
    File dir = Resources.getResourceAsFile(packagePath);

    // `Command` 구현체를 찾는다.
    findCommands(dir, packagePath);

    // `Command` 구현체의 인스턴스를 생성한다.
    createCommands();
  }
  
  private void findCommands(File dir, String packagePath) throws Exception {

    // 디렉토리가 아니면 무시한다.
    if (!dir.isDirectory())
      return;

    // 디렉토리에 들어 있는 파일 정보를 조회한다.
    File[] files = dir.listFiles();

    for (File file : files) {
      // 해당 항목이 디렉토리이면 재귀호출을 이용하여 그 하위 항목을 찾는다.
      if (file.isDirectory()) {
        findCommands(file, packagePath + "/" + file.getName());
        continue;
      }

      // 파일의 확장자가 .class가 아니면 무시한다.
      if (!file.getName().endsWith(".class"))
        continue;

      // 경로와 파일 이름을 합쳐 클래스 전체 이름을 만든다.
      String className = (packagePath + "/" + file.getName())
          .replace("/", ".") // 클래스 이름이기 때문에 / 를 . 으로 바꾼다.
          .replace(".class", ""); // 확장자는 제거한다.

      // 클래스 이름으로 .class 파일을 로딩한다.
      Class<?> clazz = Class.forName(className);

      // @Component 애노테이션은 붙은 클래스인 경우에만 IoC 컨테이너 관리 대상 객체로 등록한다.
      Component compAnno = clazz.getAnnotation(Component.class);
      if (compAnno != null) {
        classes.add(clazz);
      }
    }

  }

  private void createCommands() throws Exception {
    for (Class<?> clazz : classes) {
      try {
        Object obj = null;
        
        try {
          // 클래스의 기본 생성자가 있으면 이를 호출하여 인스턴스를 만든다.
          Constructor<?> defaultConstructor = clazz.getConstructor();
          obj = defaultConstructor.newInstance();
          System.out.printf("%s() 생성자 호출!\n", clazz.getName());
          
        } catch (NoSuchMethodException e) {

          // 기본 생성자가 없으면 다른 생성자를 찾는다.
          Constructor<?>[] constructors = clazz.getConstructors();
  
          // 생성자의 파라미터를 조사하여 그 파라미터 값이 있으면 해당 생성자를 호출하여 인스턴스를 생성한다.
          for (Constructor<?> constructor : constructors) {
  
            // 생성자의 파라미터 정보를 알아낸다.
            Parameter[] params = constructor.getParameters();
  
            // 파라미터 타입과 일치하는 값을 풀에서 찾는다.
            Object[] values = findParameterValues(params);
  
            // 파라미터의 개수와 값의 개수가 일치하다면 생성자를 호출하여 인스턴스를 생성하고 객체풀에 등록한다.
            if (params.length == values.length) {
              obj = constructor.newInstance(values);
              System.out.printf("%s(", clazz.getName(), params);
              for (Parameter param : params) {
                System.out.printf("%s,", param.getType().getSimpleName());
              }
              System.out.println(") 생성자 호출!");
              break;
            }
          }
          if (obj == null) 
            continue; // 생성자를 호출하지 못해 인스턴스를 만들지 못했으면 포기한다.
        }
        
        // 객체를 등록할 때 사용할 이름을 애노테이션에서 꺼낸다.
        Component compAnno = clazz.getAnnotation(Component.class);

        // 이름 필드에 저장된 이름으로 객체를 풀에 등록한다.
        addBean(compAnno.value(), obj);
        
      } catch (Exception e) {
        // 그 외 다른 오류의 경우 객체 생성을 포기한다.
        System.err.printf("%s 클래스의 인스턴스 생성 실패!\n", clazz.getName());
        e.printStackTrace();
        continue;
      }
      
    }

  }


  private Object[] findParameterValues(Parameter[] params) {
    ArrayList<Object> paramValues = new ArrayList<>();

    for (Parameter param : params) {
      // 파라미터 타입에 해당하는 값을 찾는다.
      Object value = findObject(param.getType());
      if (value != null) {
        // 값이 있다면 저장한다.
        paramValues.add(value);
      }
    }
    return paramValues.toArray();
  }


  private Object findObject(Class<?> type) {
    // 객체풀에서 값 목록을 꺼낸다.
    Collection<?> values = objPool.values();

    for (Object value : values) {
      if (type.isInstance(value))
        return value;
    }
    return null;
  }

  public void addBean(String name, Object obj) {
    if (name == null || name.equals("")) {
      objPool.put(obj.getClass().getName(), obj);
    } else {
      objPool.put(name, obj);
    }
  }

  public Object getBean(String name) {
    return objPool.get(name);
  }
  
  public int getBeanDefinitionCount() {
    return objPool.size();
  }
  
  public String[] getBeanDefinitionNames() {
    String[] names = new String[objPool.size()];
    return objPool.keySet().toArray(names);
  }
  
  public void refresh() throws Exception {
    createCommands();
  }

  public static void main(String[] args) throws Exception {
    ApplicationContext ctx = new ApplicationContext("com.eomcs.lms");

    String resource = "com/eomcs/lms/conf/mybatis-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

    SqlSessionFactoryProxy sqlSessionFactoryProxy = new SqlSessionFactoryProxy(sqlSessionFactory);

    // Dao 구현체를 만드는 공장을 준비하기
    MybatisDaoFactory daoFactory = new MybatisDaoFactory(sqlSessionFactoryProxy);

    ctx.addBean(null, daoFactory.createDao(LessonDao.class));
    ctx.addBean(null, daoFactory.createDao(MemberDao.class));
    ctx.addBean(null, daoFactory.createDao(BoardDao.class));
    ctx.addBean(null, daoFactory.createDao(PhotoBoardDao.class));
    ctx.addBean(null, daoFactory.createDao(PhotoFileDao.class));

    ctx.addBean(null, new TransactionManager(sqlSessionFactoryProxy));

    System.out.println("--------------------------------------");
    String[] names = ctx.getBeanDefinitionNames();
    for (String name : names) {
      System.out.printf("%s : %s\n",name, ctx.getBean(name).getClass().getName());
    }
    
    System.out.println("--------------------------------------");
    
    ctx.refresh();
    
    System.out.println("--------------------------------------");
    names = ctx.getBeanDefinitionNames();
    for (String name : names) {
      System.out.printf("%s : %s\n",name, ctx.getBean(name).getClass().getName());
    }
  }
}
