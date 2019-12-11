package com.eomcs.lms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;

public interface Command {
  void execute(BufferedReader in, PrintWriter out);
}
