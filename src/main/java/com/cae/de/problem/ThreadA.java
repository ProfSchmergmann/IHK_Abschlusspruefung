package com.cae.de.problem;

import com.cae.de.framework.Observable;
import com.cae.de.framework.Observer;
import com.cae.de.framework.ReadRunnable;
import com.cae.de.utils.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Implementierung eines {@link ReadRunnable}s und {@link Observable}s. Dieser Thread kümmert sich
 * um das Einlesen der Daten und das Informieren der {@link Observer} über neue Daten.
 */
public class ThreadA extends Observable<Pair<Data, Integer>> implements ReadRunnable<Data> {

  private static final Logger LOGGER = Logger.getLogger(ThreadA.class.getName());
  private final Path pathToInputFolder;
  private final AtomicBoolean running = new AtomicBoolean(false);
  private final long sleepTime;

  /**
   * Konstruktor, welcher den Pfad zum Eingabeordner setzt, welcher dann benutzt wird um die Daten
   * darin zu lesen.
   *
   * @param pathToInputFolder der relative Pfad zum Eingabeordner
   * @param sleepTime Zeit in Millisekunden, die der Thread schlafen soll, bevor dieser neue Daten
   *     liest und schickt
   */
  public ThreadA(Path pathToInputFolder, long sleepTime) {
    this.pathToInputFolder = pathToInputFolder;
    this.sleepTime = sleepTime;
  }

  /**
   * Methode für das Einlesen einer Datei in ein {@link Data} Objekt. Falls ein Fehler beim Einlesen
   * passiert, sei es ein semantischer oder syntaktischer, wird das ignoriert, eine Warning in den
   * Log geschrieben und null zurückgegeben.
   *
   * @param pathToFile der relative Pfad zur Datei
   * @return ein {@link Data} Objekt
   */
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
    } catch (Exception e) {
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

  /**
   * Methode run, welche beim Aufruf dieser Klasse gestartet wird. Diese Methode soll eine
   * Signaldatenquelle simulieren, welche alle 0,05 Sekunden neue Daten schickt. Intern wird das mit
   * {@link Thread#sleep(long)} realisiert. Wenn neue Daten vorhanden sind, werden alle {@link
   * Observer} über die Daten informiert. Der Thread läuft unendlich lange weiter und schickt auch
   * unendlich lange weiter Daten.
   */
  @Override
  public void run() {
    if (this.running.get()) return;
    this.running.set(true);
    String[] paths = new File(String.valueOf(this.pathToInputFolder)).list();
    if (paths != null) {
      while (true) {
        for (var pathToFile : paths) {
          var data = this.read(Path.of(this.pathToInputFolder + "/" + pathToFile));
          if (data != null) {
            this.notifyObservers(new Pair<>(data, paths.length));
            LOGGER.log(
                Level.INFO,
                "Schicke neue Daten aus der Datei: \"" + pathToFile + "\" an alle Observer!");
          }
          try {
            LOGGER.log(Level.INFO, "Thread A wartet " + this.sleepTime + " Millisekunden!");
            Thread.sleep(this.sleepTime);
          } catch (InterruptedException e) {
            LOGGER.log(Level.SEVERE, "Thread A konnte nicht warten! Stoppe den Thread A!");
            break;
          }
        }
      }
    } else {
      LOGGER.log(
          Level.SEVERE,
          "Keine Daten im Ordner "
              + this.pathToInputFolder
              + " oder Ordner nicht verfügbar! Beende das Programm!");
      System.exit(0);
    }
  }
}
