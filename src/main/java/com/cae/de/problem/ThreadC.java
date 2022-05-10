package com.cae.de.problem;

import com.cae.de.framework.Observer;
import com.cae.de.framework.WriteRunnable;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementierung eines {@link WriteRunnable}s und {@link Observer}s. Dieser Thread kümmert sich um
 * die Ausgabe der Daten und implementiert intern ebenfalls das Master-Worker Pattern.
 */
public class ThreadC
    implements Observer<AutoKorrelationsFunktion>, WriteRunnable<AutoKorrelationsFunktion> {

  private static final Logger LOGGER = Logger.getLogger(ThreadC.class.getName());
  private final Path pathToOutputFolder;
  private final AtomicBoolean running = new AtomicBoolean(false);

  /**
   * Konstruktor, welcher den relativen Pfad zum Ausgabeordner setzt.
   *
   * @param pathToOutputFolder der relative Pfad des Ausgabeordners
   */
  public ThreadC(Path pathToOutputFolder) {
    this.pathToOutputFolder = pathToOutputFolder;
  }

  /** Methode, die dafür da ist, den Zustand dieses Threads zu setzten. */
  @Override
  public void run() {
    if (this.running.get()) return;
    this.running.set(true);
  }

  /**
   * Update-Methode, welche die gegebene {@link AutoKorrelationsFunktion} an einen Worker-Thread zur
   * Weiterbearbeitung schickt.
   *
   * @param autoKorrelationsFunktion das Objekt was geschrieben werden soll
   */
  @Override
  public void update(AutoKorrelationsFunktion autoKorrelationsFunktion) {
    CompletableFuture.supplyAsync(() -> this.write(autoKorrelationsFunktion));
  }

  /**
   * Funktion zum Schreiben der {@link AutoKorrelationsFunktion}. Es wird zuerst geprüft, ob der
   * gegebene Ordner existiert. Wenn nein wird dieser neu erstellt. Danach wird die Datei
   * geschrieben und wenn eine gleichnamige Datei vorhanden ist, wird diese überschrieben.
   *
   * @param autoKorrelationsFunktion das zu schreibende Objekt
   * @return true falls es geklappt hat, false andernfalls
   */
  @Override
  public boolean write(AutoKorrelationsFunktion autoKorrelationsFunktion) {
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
      return true;
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, "Konnte nicht in die Datei: \"" + pathToFile + "\" schreiben!");
    }
    return false;
  }
}
