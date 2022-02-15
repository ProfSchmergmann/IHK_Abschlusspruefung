package com.cae.de.utils.io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class ExternalStringFileWriter for writing strings to files outside the project like for example
 * in the same folder.
 */
public class ExternalStringFileWriter implements Writer<String> {

  private static final Logger LOGGER = Logger.getLogger(ExternalStringFileWriter.class.getName());

  @Override
  public boolean write(String s, String pathToFile) {
    try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(pathToFile))) {
      LOGGER.log(Level.INFO, "Writing to file: " + pathToFile);
      bufferedWriter.write(s);
      return true;
    } catch (IOException e) {
      LOGGER.log(Level.WARNING, "Could not write to file: " + pathToFile);
      return false;
    }
  }

  @Override
  public boolean write(String s) {
    return false;
  }
}
