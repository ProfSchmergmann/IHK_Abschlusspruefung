package com.cae.de.problem;

import com.cae.de.framework.Observable;
import com.cae.de.framework.Observer;
import com.cae.de.framework.WriteRunnable;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadC extends Observable<AutoKorrelationsFunktion>
    implements Observer<AutoKorrelationsFunktion>, WriteRunnable<AutoKorrelationsFunktion> {

  private static final Logger LOGGER = Logger.getLogger(ThreadC.class.getName());
  private final Path pathToOutputFolder;
  private final AtomicBoolean running = new AtomicBoolean(false);
  private final AtomicBoolean writing = new AtomicBoolean(false);

  public ThreadC(Path pathToOutputFolder) {
    this.pathToOutputFolder = pathToOutputFolder;
  }

  @Override
  public void run() {
    if (this.running.get()) return;
    this.running.set(true);
  }

  @Override
  public void update(AutoKorrelationsFunktion autoKorrelationsFunktion) {
    if (!this.writing.get()) {
      this.write(autoKorrelationsFunktion);
    }
  }

  @Override
  public boolean write(AutoKorrelationsFunktion autoKorrelationsFunktion) {
    this.writing.set(true);
    var pathToFile = this.pathToOutputFolder + "/" + "out" + autoKorrelationsFunktion.fileName();
    try {
      if (!Files.exists(this.pathToOutputFolder)) {
        Files.createDirectory(this.pathToOutputFolder);
      }
      if (Files.exists(Path.of(pathToFile))) {
        LOGGER.log(Level.WARNING, "Überschreibe die Datei: \"" + pathToFile + "\"!");
      } else {
        LOGGER.log(Level.INFO, "Schreibe in die Datei: " + pathToFile);
      }
      var br = new BufferedWriter(new FileWriter(pathToFile));
      br.write(autoKorrelationsFunktion.getOutputString());
      br.close();
      this.notifyObserver(autoKorrelationsFunktion);
      this.writing.set(false);
      return true;
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, "Konnte nicht in die Datei: \"" + pathToFile + "\" schreiben!");
    }
    this.writing.set(false);
    return false;
  }
}
