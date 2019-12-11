package com.eomcs.lms.dao;

import java.util.List;
import com.eomcs.lms.domain.PhotoFile;

public interface PhotoFileDao {

  int delete(int no) throws Exception;
  int deleteByBoard(int boardNo) throws Exception;
  int add(List<PhotoFile> photoFiles) throws Exception;
  List<PhotoFile> list() throws Exception;
  List<PhotoFile> listByBoard(int boardNo) throws Exception;
  
}
