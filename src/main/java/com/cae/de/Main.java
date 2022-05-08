package com.cae.de;

import com.cae.de.framework.Consumer;
import com.cae.de.framework.ISolver;
import com.cae.de.framework.Producer;
import com.cae.de.problem.FileStringConsumer;
import com.cae.de.problem.FileStringProducer;
import com.cae.de.problem.FileStringSolver;
import com.cae.de.utils.CmdLineParser;
import com.cae.de.utils.io.ExternalStringFileReader;
import com.cae.de.utils.io.ExternalStringFileWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/** Main Klasse welche über den Programmaufruf gestartet wird. */
public class Main {
  private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

  /**
   * Main Methode.
   *
   * @param args die Programmzeilenargumente
   */
  public static void main(String[] args) {
    var cmdLineParser = new CmdLineParser(args);


    var reader = new ExternalStringFileReader();
    var map = new HashMap<String, String>();
    try {
      map =
          Files.list(Path.of(cmdLineParser.getInputFolder()))
              .parallel()
              .collect(
                  Collectors.toMap(
                      f -> reader.readObject(f.toString()),
                      f -> String.valueOf(f.getFileName()),
                      (a, b) -> b,
                      HashMap::new));
    } catch (IOException e) {
      LOGGER.log(
          Level.SEVERE,
          "Konnte input Dateien innerhalb " + cmdLineParser.getInputFolder() + " nicht lesen.");
      System.exit(1);
    }

    var writer = new ExternalStringFileWriter();
    try {
      if (!Files.exists(Path.of(cmdLineParser.getOutputFolder()))) {
        Files.createDirectory(Path.of(cmdLineParser.getOutputFolder()));
      }
      var finalOutputFolder = cmdLineParser.getOutputFolder();
      map.forEach(
          (l, s) -> {
            var outputPath = finalOutputFolder + "/" + s + "_out.txt";
            if (Files.exists(Path.of(outputPath))) {
              LOGGER.log(
                  Level.WARNING, "Datei " + outputPath + " existiert. Sie wird überschrieben.");
            }
            writer.write(l, outputPath);
          });
    } catch (IOException e) {
      LOGGER.log(
          Level.SEVERE,
          "Konnte keine Dateien in den output Ordner: "
              + cmdLineParser.getOutputFolder()
              + " schreiben.");
      System.exit(1);
    }
  }
}
