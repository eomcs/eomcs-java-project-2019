package com.eomcs.lms.dao;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public abstract class AbstractDao<T> {
  
  ArrayList<T> list = new ArrayList<>();
  
  String filepath;
  
  public AbstractDao(String filepath) throws Exception {
    this.filepath = filepath;
    loadData();
  }
  
  @SuppressWarnings("unchecked")
  public void loadData() throws Exception {
    ObjectInputStream in = null;
    
    try {
      
      File file = new File(filepath);
      if (!file.exists()) {
        file.createNewFile();
        return;
      }
      
      if (file.length() == 0) {
        return;
      }
      
      in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
      
      list = (ArrayList<T>) in.readObject();
      
    } catch (Exception e) {
      throw e;
      
    } finally {
      try {in.close();} catch (Exception e) {}
    }
  }
  
  public void saveData() throws Exception {
    try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(
        new FileOutputStream(filepath)))) {
      
      out.writeObject(list);
      
    } catch (Exception e) {
      throw e;
    }
  }
}
