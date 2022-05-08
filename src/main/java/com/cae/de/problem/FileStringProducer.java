package com.cae.de.problem;

import com.cae.de.framework.ISolver;
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

public class FileStringProducer extends Producer<ISolver.Data<Pair<String, String>, String>> {

  private static final Logger LOGGER = Logger.getLogger(FileStringProducer.class.getName());
  private final String pathToFile;

  public FileStringProducer(String pathToFile) {
    this.pathToFile = pathToFile;
  }

  @Override
  public List<ISolver.Data<Pair<String, String>, String>> readToList() throws IOException {
    var list = new ArrayList<ISolver.Data<Pair<String, String>, String>>();
    ArrayList<String> pathList = null;
    if (Files.isDirectory(Path.of(this.pathToFile))) {
      pathList =
          new ArrayList<>(Files.list(Path.of(this.pathToFile)).map(String::valueOf).toList());
    }
    if (pathList == null) {
      pathList = new ArrayList<>();
      pathList.add(this.pathToFile);
    }
    for (var path : pathList) {
      try (BufferedReader br = new BufferedReader(new FileReader(path))) {
        list.add(
            new ISolver.Data<>(
                new Pair<>(this.pathToFile, br.lines().collect(Collectors.joining())), ""));
      } catch (IOException e) {
        LOGGER.log(Level.WARNING, "Could not read file: " + path);
      }
    }
    return list;
  }
}
