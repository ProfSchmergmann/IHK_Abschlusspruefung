package com.cae.de.stringproblem;

import com.cae.de.framework.EVAException;
import com.cae.de.framework.Producer;
import com.cae.de.utils.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class FileStringProducer extends Producer<Pair<String, String>> {

  private static final Logger LOGGER = Logger.getLogger(FileStringProducer.class.getName());
  private final String pathToFile;

  public FileStringProducer(String pathToFile) {
    this.pathToFile = pathToFile;
  }

  @Override
  public List<Pair<String, String>> readToList() throws EVAException {
    try {
      var list = new ArrayList<Pair<String, String>>();
      ArrayList<String> pathList = null;
      if (Files.isDirectory(Path.of(this.pathToFile))) {
        pathList =
            new ArrayList<>(
                Files.list(Path.of(this.pathToFile).getFileName()).map(String::valueOf).toList());
      }
      if (pathList == null) {
        pathList = new ArrayList<>();
        pathList.add(this.pathToFile);
      }
      for (var path : pathList) {
        var br = new BufferedReader(new FileReader(path));
        LOGGER.log(Level.INFO, "Lese aus der Datei: " + path);
        list.add(new Pair<>(this.pathToFile, br.lines().collect(Collectors.joining())));
        br.close();
      }
      return list;
    } catch (IOException e) {
      throw new EVAException(
          "Konnte Dateien aus dem Pfad: " + this.pathToFile + " nicht einlesen!");
    }
  }
}
