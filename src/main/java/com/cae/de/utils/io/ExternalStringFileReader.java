package com.cae.de.utils.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Class external File reader for reading files outside the project like for example in the same
 * folder.
 */
public class ExternalStringFileReader implements IReader<String> {

  private static final Logger LOGGER = Logger.getLogger(ExternalStringFileReader.class.getName());

  @Override
  public String readObject(String pathToFile) {
    return null;
  }

  @Override
  public String read(String pathToFile) {
    String s;
    try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
      s = br.lines().collect(Collectors.joining());
    } catch (IOException e) {
      LOGGER.log(Level.WARNING, "Could not read file: " + pathToFile);
      return null;
    }
    return s;
  }
}
