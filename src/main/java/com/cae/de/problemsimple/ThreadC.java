package com.cae.de.problemsimple;

import com.cae.de.framework.Observer;
import com.cae.de.framework.WriteRunnable;
import com.cae.de.problem.AutoKorrelationsFunktion;
import com.cae.de.utils.Pair;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Implementierung eines {@link WriteRunnable}s und {@link Observer}s. */
public class ThreadC
    implements Observer<Pair<AutoKorrelationsFunktion, Integer>>,
        WriteRunnable<AutoKorrelationsFunktion> {

  private static final Logger LOGGER = Logger.getLogger(ThreadC.class.getName());
  private final Path pathToOutputFolder;
  private final Set<String> writtenFiles = new HashSet<>();
  private final AtomicBoolean running = new AtomicBoolean(false);
  private final AtomicBoolean writing = new AtomicBoolean(false);

  /**
   * Konstruktor, welcher den relativen Pfad zum Ausgabeordner setzt.
   *
   * @param pathToOutputFolder der relative Pfad des Ausgabeordners
   */
  public ThreadC(Path pathToOutputFolder) {
    this.pathToOutputFolder = pathToOutputFolder;
  }

  /** Methode, die dafür da ist, den Zustand dieses Threads zu setzen. */
  @Override
  public void run() {
    if (this.running.get()) return;
    this.running.set(true);
  }

  /**
   * Update-Methode, welche versucht die gegebene {@link AutoKorrelationsFunktion} zu schreiben.
   * Diese Methode prüft, ob das Objekt schon geschrieben wurde und schreibt es nicht doppelt. Wenn
   * alle Objekte geschrieben wurden, wird das Programm beendet.
   *
   * @param akfip ein Paar des Objektes was geschrieben werden soll und der gesamten Anzahl an
   *     Objekten
   */
  @Override
  public void update(Pair<AutoKorrelationsFunktion, Integer> akfip) {
    if (this.writing.get()) {
      LOGGER.log(
          Level.WARNING,
          "ThreadC ist schreibend und wird \"" + akfip.key().fileName() + "\" nicht bearbeiten!");
      return;
    }
    this.writing.set(true);
    if (this.writtenFiles.size() == akfip.value()) {
      LOGGER.log(Level.INFO, "Alle Daten wurden geschrieben. Programm wird beendet.");
      System.exit(0);
    }
    if (this.writtenFiles.contains(akfip.key().fileName())) {
      return;
    }
    this.write(akfip.key());
    this.writing.set(false);
  }

  /**
   * Funktion zum Schreiben der {@link AutoKorrelationsFunktion}. Es wird zuerst geprüft, ob der
   * gegebene Ordner existiert. Wenn nein wird dieser neu erstellt. Danach wird die Datei
   * geschrieben und wenn eine gleichnamige Datei vorhanden ist, wird diese überschrieben.
   *
   * @param akf das zu schreibende Objekt
   * @return true falls es geklappt hat, false andernfalls
   */
  @Override
  public boolean write(AutoKorrelationsFunktion akf) {
    var pathToFile = this.pathToOutputFolder + "/" + "out" + akf.fileName();
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
      br.write(akf.getOutputString());
      br.close();
      this.writtenFiles.add(akf.fileName());
      return true;
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, "Konnte nicht in die Datei: \"" + pathToFile + "\" schreiben!");
    }
    return false;
  }
}
