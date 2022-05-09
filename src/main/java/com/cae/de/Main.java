package com.cae.de;

import com.cae.de.problem.ThreadA;
import com.cae.de.problem.ThreadB;
import com.cae.de.problem.ThreadC;
import com.cae.de.utils.CmdLineParser;

import java.nio.file.Path;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/** Main Klasse welche über den Programmaufruf gestartet wird. */
public class Main {
  private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

  /**
   * Main Methode.
   *
   * @param args die Programmzeilenargumente
   */
  public static void main(String[] args) {
    var cmdLineParser = new CmdLineParser(args);

    var threadA = new ThreadA(Path.of(cmdLineParser.getInputFolder()));
    var threadB = new ThreadB();
    var threadC = new ThreadC(Path.of(cmdLineParser.getOutputFolder()));

    threadA.registerObserver(threadB);
    threadB.registerObserver(threadC);
    threadC.registerObserver(threadA);

    var executorService = Executors.newFixedThreadPool(3);
    executorService.execute(threadA);
    executorService.execute(threadB);
    executorService.execute(threadC);
  }
}
