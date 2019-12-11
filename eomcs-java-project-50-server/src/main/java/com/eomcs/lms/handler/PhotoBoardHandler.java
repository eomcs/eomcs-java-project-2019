package com.eomcs.lms.handler;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import com.eomcs.lms.dao.PhotoBoardDao;
import com.eomcs.lms.dao.PhotoFileDao;
import com.eomcs.lms.domain.PhotoBoard;
import com.eomcs.lms.domain.PhotoFile;
import com.eomcs.sql.TransactionManager;
import com.eomcs.stereotype.CommandMapping;

@Component
public class PhotoBoardHandler {

  PhotoBoardDao photoBoardDao;
  PhotoFileDao photoFileDao;
  TransactionManager txManager;
  
  public PhotoBoardHandler(PhotoBoardDao photoBoardDao, PhotoFileDao photoFileDao, TransactionManager txManager) {
    this.photoBoardDao = photoBoardDao;
    this.photoFileDao = photoFileDao;
    this.txManager = txManager;
  }

  @CommandMapping("/photoboard/add")
  public void add(BufferedReader in, PrintWriter out) {
    try {
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
      
      txManager.beginTransaction();
      
      photoBoardDao.add(board);
      for (PhotoFile photo : photos) {
        photo.setBoardNo(board.getNo());
      }
      photoFileDao.add(photos);
      
      
      txManager.commit();
      out.println("사진을 저장했습니다.");
      
    } catch (Exception e) {
      txManager.rollback();
      out.printf("%s : %s\n", e.toString(), e.getMessage());
      
    }
  }
  
  @CommandMapping("/photoboard/delete")
  public void delete(BufferedReader in, PrintWriter out) {
    try {
      out.print("번호?\n!{}!\n"); out.flush();
      int no = Integer.parseInt(in.readLine());
      
      txManager.beginTransaction();
      
      // 게시물에 첨부된 사진 파일을 먼저 삭제한다.
      photoFileDao.deleteByBoard(no);
      
      if (photoBoardDao.delete(no) > 0) {
        out.println("사진을 삭제했습니다.");
      } else { 
        out.println("해당 사진을 찾을 수 없습니다.");
      }
      txManager.commit();
      
    } catch (Exception e) {
      txManager.rollback();
      out.printf("%s : %s\n", e.toString(), e.getMessage());
      
    }
  }
  
  @CommandMapping("/photoboard/detail2")
  public void detail2(BufferedReader in, PrintWriter out) {
    try {
      out.print("번호?\n!{}!\n"); out.flush();
      int no = Integer.parseInt(in.readLine());

      PhotoBoard board = photoBoardDao.detail2(no);
      if (board == null) { 
        out.println("해당 사진을 찾을 수 없습니다.");
        return;
      }

      out.printf("제목: %s\n", board.getTitle());
      out.printf("작성일: %s\n", board.getCreatedDate());
      out.printf("조회수: %d\n", board.getViewCount());
      out.printf("수업: %d\n", board.getLessonNo());
      out.println("사진 파일:");
      
      List<PhotoFile> photos = board.getPhotos();
      
      for (PhotoFile photo : photos) {
        out.printf("> %s\n", photo.getFilepath());
      }
      
    } catch (Exception e) {
      out.printf("%s : %s\n", e.toString(), e.getMessage());
    }
  }
  
  @CommandMapping("/photoboard/detail")
  public void detail(BufferedReader in, PrintWriter out) {
    try {
      out.print("번호?\n!{}!\n"); out.flush();
      int no = Integer.parseInt(in.readLine());

      PhotoBoard board = photoBoardDao.detail(no);
      if (board == null) { 
        out.println("해당 사진을 찾을 수 없습니다.");
        return;
      }

      out.printf("제목: %s\n", board.getTitle());
      out.printf("작성일: %s\n", board.getCreatedDate());
      out.printf("조회수: %d\n", board.getViewCount());
      out.printf("수업: %d\n", board.getLessonNo());
      out.println("사진 파일:");
      
      List<PhotoFile> photos = photoFileDao.listByBoard(no);
      
      for (PhotoFile photo : photos) {
        out.printf("> %s\n", photo.getFilepath());
      }
      
    } catch (Exception e) {
      out.printf("%s : %s\n", e.toString(), e.getMessage());
    }
  }
  
  @CommandMapping("/photoboard/list")
  public void list(BufferedReader in, PrintWriter out) {
    try {
      List<PhotoBoard> boards = photoBoardDao.list();
      if (boards == null) { 
        out.println("서버에서 데이터를 가져오는데 오류 발생!");
        return;
      }
      
      for (PhotoBoard board : boards) {
        out.printf("%3d, %-20s, %s, %d, %d\n", 
            board.getNo(), board.getTitle(), 
            board.getCreatedDate(), board.getViewCount(), board.getLessonNo());
      }
      
    } catch (Exception e) {
      out.printf("%s : %s\n", e.toString(), e.getMessage());
    }
  }
  
  @CommandMapping("/photoboard/update")
  public void update(BufferedReader in, PrintWriter out) {
    try {
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

      txManager.beginTransaction();
      
      photoBoardDao.update(board);
      
      if (updatePhotos.size() > 0) {
        photoFileDao.deleteByBoard(no);
        for (PhotoFile photo : updatePhotos) {
          photo.setBoardNo(board.getNo());
        }
        photoFileDao.add(updatePhotos);
      }
      
      txManager.commit();
      
      out.println("사진을 변경했습니다.");

    } catch (Exception e) {
      txManager.rollback();
      out.printf("%s : %s\n", e.toString(), e.getMessage());
      
    }
  }
}
