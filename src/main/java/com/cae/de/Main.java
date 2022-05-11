package com.cae.de;

import com.cae.de.problem.ThreadA;
import com.cae.de.problem.ThreadB;
import com.cae.de.problem.ThreadC;
import com.cae.de.utils.CmdLineParser;

import java.nio.file.Path;
import java.util.concurrent.Executors;

/** Main Klasse welche über den Programmaufruf gestartet wird. */
public class Main {

  /**
   * Main Methode.
   *
   * @param args die Programmzeilenargumente
   */
  public static void main(String[] args) {
    var cmdLineParser = new CmdLineParser(args);

    var threadA =
        new ThreadA(Path.of(cmdLineParser.getInputFolder()), cmdLineParser.getSleepTime());
    var executorService = Executors.newFixedThreadPool(3);

    if (cmdLineParser.isMasterWorker()) {
      var threadB = new ThreadB(cmdLineParser.getThreadPoolSize(), cmdLineParser.getQueueSize());
      var threadC = new ThreadC(Path.of(cmdLineParser.getOutputFolder()));
      threadA.registerObserver(threadB);
      threadB.registerObserver(threadC);

      executorService.execute(threadA);
      executorService.execute(threadB);
      executorService.execute(threadC);
    } else {
      var threadB = new com.cae.de.problemsimple.ThreadB();
      var threadC = new com.cae.de.problemsimple.ThreadC(Path.of(cmdLineParser.getOutputFolder()));
      threadA.registerObserver(threadB);
      threadB.registerObserver(threadC);

      executorService.execute(threadA);
      executorService.execute(threadB);
      executorService.execute(threadC);
    }
  }
}
