package com.eomcs.lms.dao;

import java.util.List;
import com.eomcs.lms.domain.PhotoBoard;

public interface PhotoBoardDao {

  int delete(int no) throws Exception;
  int update(PhotoBoard photo) throws Exception;
  int add(PhotoBoard photo) throws Exception;
  List<PhotoBoard> list() throws Exception;
  PhotoBoard detail(int no) throws Exception;
  PhotoBoard detail2(int no) throws Exception;
}
