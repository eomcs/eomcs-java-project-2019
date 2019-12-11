package com.eomcs.lms.domain;

import java.io.Serializable;

public class PhotoFile implements Serializable, Cloneable {
  private static final long serialVersionUID = 1L;
  
  private int no;
  private String filepath;
  private int boardNo;
  
  @Override
  public String toString() {
    return "PhotoFile [no=" + no + ", filepath=" + filepath + ", boardNo=" + boardNo + "]";
  }

  @Override
  public PhotoFile clone() throws CloneNotSupportedException {
    return (PhotoFile) super.clone();
  }

  public int getNo() {
    return no;
  }

  public void setNo(int no) {
    this.no = no;
  }

  public String getFilepath() {
    return filepath;
  }

  public void setFilepath(String filepath) {
    this.filepath = filepath;
  }

  public int getBoardNo() {
    return boardNo;
  }

  public void setBoardNo(int boardNo) {
    this.boardNo = boardNo;
  }
  
  
}
