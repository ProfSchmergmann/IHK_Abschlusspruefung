package com.cae.de.stringproblem;

import com.cae.de.framework.Consumer;
import com.cae.de.framework.EVAException;
import com.cae.de.framework.ISolver;
import com.cae.de.utils.Pair;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileStringConsumer extends Consumer<ISolver.Data<Pair<String, String>, String>> {

  private static final Logger LOGGER = Logger.getLogger(FileStringConsumer.class.getName());

  private final String outputFolderString;

  public FileStringConsumer(String outputFolderString) {
    this.outputFolderString = outputFolderString;
  }

  @Override
  public boolean write(ISolver.Data<Pair<String, String>, String> data) throws EVAException {
    var pathToFile = this.outputFolderString + "/" + data.getParameter().key() + "_out";
    try {
      if (!Files.exists(Path.of(this.outputFolderString))) {
        Files.createDirectory(Path.of(this.outputFolderString));
      }
      var br = new BufferedWriter(new FileWriter(pathToFile));
      LOGGER.log(Level.INFO, "Schreibe in die Datei: " + pathToFile);
      br.write(data.getResult());
      br.close();
      return true;
    } catch (IOException e) {
      throw new EVAException("Konnte nicht in die Datei: " + pathToFile + " schreiben!");
    }
  }
}
