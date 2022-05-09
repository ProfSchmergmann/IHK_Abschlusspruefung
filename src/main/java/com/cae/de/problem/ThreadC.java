package com.cae.de.problem;

import com.cae.de.framework.Observable;
import com.cae.de.framework.Observer;
import com.cae.de.framework.WriteRunnable;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadC extends Observable<AutoKorrelationsFunktion>
    implements Observer<AutoKorrelationsFunktion>, WriteRunnable<AutoKorrelationsFunktion> {

  private static final Logger LOGGER = Logger.getLogger(ThreadC.class.getName());
  private final Path pathToOutputFolder;
  private boolean running = false;
  private boolean writing = false;

  public ThreadC(Path pathToOutputFolder) {
    this.pathToOutputFolder = pathToOutputFolder;
  }

  @Override
  public void run() {
    if (this.running) return;
    this.running = true;
  }

  @Override
  public void update(AutoKorrelationsFunktion autoKorrelationsFunktion) {
    if (!this.writing) {
      this.write(autoKorrelationsFunktion);
    }
  }

  @Override
  public boolean write(AutoKorrelationsFunktion autoKorrelationsFunktion) {
    this.writing = true;

    var pathToFile = this.pathToOutputFolder + "/" + "out" + autoKorrelationsFunktion.getFileName();
    try {
      if (!Files.exists(this.pathToOutputFolder)) {
        Files.createDirectory(this.pathToOutputFolder);
      }
      if (!Files.exists(Path.of(pathToFile))) {
        var br = new BufferedWriter(new FileWriter(pathToFile));
        LOGGER.log(Level.INFO, "Schreibe in die Datei: " + pathToFile);
        br.write(autoKorrelationsFunktion.toString());
        br.close();
        this.notifyObserver(autoKorrelationsFunktion);
        this.writing = false;
        return true;
      }
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, "Konnte nicht in die Datei: \"" + pathToFile + "\" schreiben!");
    }
    this.writing = false;
    return false;
  }
}
