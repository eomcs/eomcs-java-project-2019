package com.eomcs.lms;

import java.sql.Date;
import java.util.Scanner;

public class BoardHandler {

  static Scanner keyboard;
  static final int LENGTH = 10;
  static Board[] boards = new Board[LENGTH];
  static int boardIdx = 0;

  static void listBoard() {
    for (int j = 0; j < boardIdx; j++) {
      System.out.printf("%3d, %-20s, %s, %d\n",
          boards[j].no, boards[j].contents, boards[j].createdDate, boards[j].viewCount);
    }
  }

  static void addBoard() {
    Board board = new Board();

    System.out.print("번호? ");
    board.no = Integer.parseInt(keyboard.nextLine());

    System.out.print("내용? ");
    board.contents = keyboard.nextLine();

    board.createdDate = new Date(System.currentTimeMillis());

    board.viewCount = 0;

    boards[boardIdx] = board;
    boardIdx++;

    System.out.println("저장하였습니다.");
  }

}
