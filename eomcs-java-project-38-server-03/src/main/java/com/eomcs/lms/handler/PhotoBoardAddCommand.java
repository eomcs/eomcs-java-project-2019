package com.eomcs.lms.handler;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import com.eomcs.lms.DataLoaderListener;
import com.eomcs.lms.dao.PhotoBoardDao;
import com.eomcs.lms.dao.PhotoFileDao;
import com.eomcs.lms.domain.PhotoBoard;
import com.eomcs.lms.domain.PhotoFile;

public class PhotoBoardAddCommand implements Command {

  PhotoBoardDao photoBoardDao;
  PhotoFileDao photoFileDao;
  
  public PhotoBoardAddCommand(PhotoBoardDao photoBoardDao, PhotoFileDao photoFileDao) {
    this.photoBoardDao = photoBoardDao;
    this.photoFileDao = photoFileDao;
  }

  @Override
  public void execute(BufferedReader in, PrintWriter out) {
    try {
      DataLoaderListener.connection.setAutoCommit(false);
      PhotoBoard board = new PhotoBoard();

      out.print("제목?\n!{}!\n"); out.flush();
      board.setTitle(in.readLine());

      out.print("수업?\n!{}!\n"); out.flush();
      board.setLessonNo(Integer.parseInt(in.readLine()));

      ArrayList<PhotoFile> photos = new ArrayList<>();
      
      // 사진 게시물에 첨부한 사진을 최소 한 개 입력 받는다.
      out.println("최소 한 개의 사진 파일을 등록해야 합니다.");
      out.println("파일명 입력 없이 그냥 엔터를 치면 파일 추가를 마칩니다.");
      while (true) {
        out.print("사진 파일?\n!{}!\n"); out.flush();
        String filepath = in.readLine();
        
        if (filepath.length() > 0) {
          PhotoFile file = new PhotoFile();
          file.setFilepath(filepath);
          photos.add(file);
          continue;
        }
        
        if (photos.size() == 0) {
          out.println("최소 한 개의 사진 파일을 등록해야 합니다.");
        } else {
          break;
        }
      }
      
      photoBoardDao.add(board);
      
      for (PhotoFile photo : photos) {
        photo.setBoardNo(board.getNo());
        photoFileDao.add(photo);
      }
      DataLoaderListener.connection.commit();
      
      out.println("사진을 저장했습니다.");
      
    } catch (Exception e) {
      try {DataLoaderListener.connection.rollback();} catch (Exception ex) {}
      out.printf("%s : %s\n", e.toString(), e.getMessage());
      
    } finally {
      try {DataLoaderListener.connection.setAutoCommit(true);} catch (Exception ex) {}
    }

  }
}
