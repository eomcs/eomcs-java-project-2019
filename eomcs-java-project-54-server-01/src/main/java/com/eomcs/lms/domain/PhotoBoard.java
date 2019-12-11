package com.eomcs.lms.domain;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

public class PhotoBoard implements Cloneable, Serializable {
  private static final long serialVersionUID = 1L;

  private int no;
  private String title;
  private Date createdDate;
  private int viewCount;
  private int lessonNo;
  private List<PhotoFile> photos;
  
  @Override
  public String toString() {
    return "PhotoBoard [no=" + no + ", title=" + title + ", createdDate=" + createdDate
        + ", viewCount=" + viewCount + ", lessonNo=" + lessonNo + "]";
  }
  
  @Override
  public PhotoBoard clone() throws CloneNotSupportedException {
    return (PhotoBoard) super.clone();
  }
  
  public int getNo() {
    return no;
  }
  public void setNo(int no) {
    this.no = no;
  }
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public Date getCreatedDate() {
    return createdDate;
  }
  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }
  public int getViewCount() {
    return viewCount;
  }
  public void setViewCount(int viewCount) {
    this.viewCount = viewCount;
  }
  public int getLessonNo() {
    return lessonNo;
  }
  public void setLessonNo(int lessonNo) {
    this.lessonNo = lessonNo;
  }

  public List<PhotoFile> getPhotos() {
    return photos;
  }

  public void setPhotos(List<PhotoFile> photos) {
    this.photos = photos;
  }
  
}
