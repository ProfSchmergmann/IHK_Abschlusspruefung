package com.cae.de;

import com.cae.de.framework.EVAException;
import com.cae.de.stringproblem.FileStringConsumer;
import com.cae.de.stringproblem.FileStringProducer;
import com.cae.de.stringproblem.FileStringSolver;
import com.cae.de.utils.CmdLineParser;

import java.util.logging.Level;
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

    try {
      new FileStringSolver()
          .input(new FileStringProducer(cmdLineParser.getInputFolder()))
          .process()
          .output(new FileStringConsumer(cmdLineParser.getOutputFolder()))
          .done();
    } catch (EVAException e) {
      LOGGER.log(Level.SEVERE, e.getMessage());
      System.exit(1);
    }
  }
}
