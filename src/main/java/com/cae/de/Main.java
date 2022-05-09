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

    var executorService = Executors.newFixedThreadPool(3);
    executorService.submit(new ThreadA(Path.of(cmdLineParser.getInputFolder())));
    executorService.submit(new ThreadB<ThreadA.Data>());
    executorService.submit(new ThreadC<ThreadA.Data>(Path.of(cmdLineParser.getOutputFolder())));

    //    try {
    //      new FileStringSolver()
    //          .input(new FileStringProducer(cmdLineParser.getInputFolder()))
    //          .process()
    //          .output(new FileStringConsumer(cmdLineParser.getOutputFolder()))
    //          .done();
    //    } catch (EVAException e) {
    //      LOGGER.log(Level.SEVERE, e.getMessage());
    //      System.exit(1);
    //    }
  }
}
