package com.cae.de.problem;

import com.cae.de.framework.Observable;
import com.cae.de.framework.Observer;
import com.cae.de.framework.ReadRunnable;
import com.cae.de.utils.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ThreadA extends Observable<Data>
    implements Observer<AutoKorrelationsFunktion>, ReadRunnable<Data> {

  private static final Logger LOGGER = Logger.getLogger(ThreadA.class.getName());
  private final Path pathToInputFolder;
  private final HashSet<String> processedFiles;
  private final AtomicBoolean running = new AtomicBoolean(false);

  public ThreadA(Path pathToInputFolder) {
    this.pathToInputFolder = pathToInputFolder;
    this.processedFiles = new HashSet<>();
  }

  private boolean finishedProcessing(List<Path> paths) {
    if (this.processedFiles.size() != paths.size()) return false;
    paths.stream()
        .map(path -> path.getFileName().toString())
        .toList()
        .forEach(this.processedFiles::remove);
    return this.processedFiles.size() == 0;
  }

  @Override
  public Data read(Path pathToFile) {
    List<Pair<Integer, Integer>> list;
    try (var br = new BufferedReader(new FileReader(String.valueOf(pathToFile)))) {
      list =
          br.lines()
              .filter(line -> !line.startsWith("#"))
              .map(
                  line ->
                      new Pair<>(
                          Integer.parseInt(line.split("\t")[0]),
                          Integer.parseInt(line.split("\t")[1])))
              .collect(Collectors.toList());
    } catch (IOException e) {
      LOGGER.log(
          Level.WARNING, "Konnte Dateien aus dem Pfad: \"" + pathToFile + "\" nicht einlesen!");
      return null;
    }
    var xStart = new int[list.size()];
    var yStart = new int[list.size()];
    IntStream.range(0, list.size())
        .forEachOrdered(
            i -> {
              var tmp = list.get(i);
              xStart[i] = tmp.value();
              yStart[i] = tmp.key();
            });
    return new Data(pathToFile.getFileName().toString(), xStart, yStart);
  }

  @Override
  public void run() {
    if (this.running.get()) return;
    this.running.set(true);
    String[] paths = new File(String.valueOf(this.pathToInputFolder)).list();
    while (!this.finishedProcessing(Arrays.stream(paths).map(Path::of).toList())) {
      for (var pathToFile : paths) {
        var data = this.read(Path.of(this.pathToInputFolder + "/" + pathToFile));
        if (data != null) {
          this.notifyObserver(data);
          LOGGER.log(
              Level.INFO,
              "Schicke neue Daten aus der Datei: \"" + pathToFile + "\" an alle Observer!");
        }
        try {
          Thread.sleep(50);
          LOGGER.log(Level.INFO, "Thread A wartet 0.05 Sekunden!");
        } catch (InterruptedException e) {
          LOGGER.log(Level.SEVERE, "Thread A konnte nicht warten! Stoppe den Thread A!");
          break;
        }
      }
    }
    Thread.currentThread().interrupt();
  }

  @Override
  public void update(AutoKorrelationsFunktion autoKorrelationsFunktion) {
    this.processedFiles.add(autoKorrelationsFunktion.fileName());
  }
}
