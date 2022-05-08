package com.cae.de.problem;

import com.cae.de.framework.Consumer;
import com.cae.de.framework.ISolver;
import com.cae.de.utils.Pair;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileStringConsumer extends Consumer<ISolver.Data<Pair<String, String>, String>> {

  private static final Logger LOGGER = Logger.getLogger(FileStringConsumer.class.getName());

  @Override
  public boolean write(ISolver.Data<Pair<String, String>, String> data) {
    try (BufferedWriter bufferedWriter =
        new BufferedWriter(new FileWriter(data.getParameter().key()))) {
      LOGGER.log(Level.INFO, "Schreibe in die Datei: " + data.getParameter().key());
      bufferedWriter.write(data.getResult());
      return true;
    } catch (IOException e) {
      LOGGER.log(
          Level.WARNING, "Konnte nicht in die Datei: " + data.getParameter().key() + " schreiben!");
      return false;
    }
  }
}
