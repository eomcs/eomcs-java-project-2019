package com.eomcs.lms.handler;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import com.eomcs.lms.DataLoaderListener;
import com.eomcs.lms.dao.PhotoBoardDao;
import com.eomcs.lms.dao.PhotoFileDao;
import com.eomcs.lms.domain.PhotoBoard;
import com.eomcs.lms.domain.PhotoFile;

public class PhotoBoardUpdateCommand implements Command {

  PhotoBoardDao photoBoardDao;
  PhotoFileDao photoFileDao;
  
  public PhotoBoardUpdateCommand(PhotoBoardDao photoBoardDao, PhotoFileDao photoFileDao) {
    this.photoBoardDao = photoBoardDao;
    this.photoFileDao = photoFileDao;
  }

  @Override
  public void execute(BufferedReader in, PrintWriter out) {
    try {
      DataLoaderListener.connection.setAutoCommit(false);
      out.print("번호?\n!{}!\n"); out.flush();
      int no = Integer.parseInt(in.readLine());

      PhotoBoard board = photoBoardDao.detail(no);
      if (board == null) { 
        out.println("해당 사진을 찾을 수 없습니다.");
        return;
      }

      out.printf("제목(%s)?\n!{}!\n", board.getTitle()); out.flush();
      String input = in.readLine();
      if (input.length() > 0) 
        board.setTitle(input);

      out.println("사진 파일:");
      List<PhotoFile> photos = photoFileDao.listByBoard(no);
      for (PhotoFile photo : photos) {
        out.printf("> %s\n", photo.getFilepath());
      }
      
      out.println();
      out.println("사진은 일부만 변경할 수 없습니다.");
      out.println("전체를 새로 등록해야 합니다.");
      out.print("사진을 변경하시겠습니까?(y/N)\n!{}!\n"); out.flush();
      input = in.readLine();
      
      ArrayList<PhotoFile> updatePhotos = new ArrayList<>();
      
      if (input.toLowerCase().equals("y")) {
        out.println("최소 한 개의 사진 파일을 등록해야 합니다.");
        out.println("파일명 입력 없이 그냥 엔터를 치면 파일 추가를 마칩니다.");
        while (true) {
          out.print("사진 파일?\n!{}!\n"); out.flush();
          String filepath = in.readLine();
          
          if (filepath.length() > 0) {
            PhotoFile file = new PhotoFile();
            file.setFilepath(filepath);
            updatePhotos.add(file);
            continue;
          }
          
          if (updatePhotos.size() == 0) {
            out.println("최소 한 개의 사진 파일을 등록해야 합니다.");
          } else {
            break;
          }
        }
      }

      photoBoardDao.update(board);
      
      if (updatePhotos.size() > 0) {
        photoFileDao.deleteByBoard(no);
        for (PhotoFile photo : updatePhotos) {
          photo.setBoardNo(no);
          photoFileDao.add(photo);
        }
      }
      DataLoaderListener.connection.commit();
      
      out.println("사진을 변경했습니다.");

    } catch (Exception e) {
      try {DataLoaderListener.connection.rollback();} catch (Exception ex) {}
      out.printf("%s : %s\n", e.toString(), e.getMessage());
      
    } finally {
      try {DataLoaderListener.connection.setAutoCommit(true);} catch (Exception ex) {}
    }
  }
}
