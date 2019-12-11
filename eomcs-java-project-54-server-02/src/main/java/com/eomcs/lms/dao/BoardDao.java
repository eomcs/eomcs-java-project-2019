package com.eomcs.lms.dao;

import java.util.List;
import com.eomcs.lms.domain.Board;

public interface BoardDao {

  int delete(int no) throws Exception;
  int update(Board board) throws Exception;
  int add(Board board) throws Exception;
  List<Board> list() throws Exception;
  Board detail(int no) throws Exception;
}
