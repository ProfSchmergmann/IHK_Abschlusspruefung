package com.cae.de;

import com.cae.de.models.Landkarte;
import com.cae.de.utils.LogOption;
import com.cae.de.utils.io.FileLandkartenReader;
import com.cae.de.utils.io.GnuPlotWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main class which is to be started and executed. It evaluates the given arguments and starts the
 * program if everything went right.
 */
public class Main {

  private static final String INPUT_FOLDER_STRING = "-inputfolder";
  private static final String OUTPUT_FOLDER_STRING = "-outputfolder";
  private static final String LOG_STRING = "-log";
  private static final String LOG_LEVEL_STRING = "-loglvl";
  private static final Logger ROOT_LOGGER = Logger.getLogger("");
  private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
  private static final String ITERATIONEN = "-i";
  public static FileHandler FILE_HANDLER;
  private static int iterationen = 100;

  public static void main(String[] args) {
    var inputFolder = "input";
    var outputFolder = "output";
    var logOption = LogOption.TRUE;
    for (var i = 0; i < args.length; i++) {
      if (i + 1 < args.length) {
        switch (args[i]) {
          case INPUT_FOLDER_STRING -> inputFolder = args[++i];
          case OUTPUT_FOLDER_STRING -> outputFolder = args[++i];
          case LOG_STRING -> {
            try {
              logOption = LogOption.getOption(args[++i]);
            } catch (IllegalArgumentException e) {
              LOGGER.log(Level.WARNING, "Konnte \"" + args[i] + "\" keiner LOG_OPTION zuordnen."
                  + "LOG_OPTIONen sind \"true\", \"false\", \"file\". Der default Wert ist \"false\".");
            }
          }
          case ITERATIONEN -> iterationen = Integer.parseInt(args[++i]);
          case LOG_LEVEL_STRING -> {
            Level logLevel = switch (args[++i]) {
              case "info" -> Level.INFO;
              case "warning" -> Level.WARNING;
              default -> Level.ALL;
            };
            ROOT_LOGGER.setLevel(logLevel);
          }
        }
      }
    }

    // Default case for java logger is true so there is no case for it.
    switch (logOption) {
      case FILE -> {
        try {
          FILE_HANDLER = new FileHandler("IHK_Abschlusspruefung.log", true);
          ROOT_LOGGER.addHandler(FILE_HANDLER);
        } catch (IOException e) {
          ROOT_LOGGER.log(Level.WARNING, "Konnte keinen FileHandler zum Logger hinzufügen. "
              + "Logs werden in die Konsole geschrieben.");
        }
      }
      case FALSE -> {
        for (var handler : ROOT_LOGGER.getHandlers()) {
          ROOT_LOGGER.removeHandler(handler);
        }
      }
    }

    var reader = new FileLandkartenReader();
    var landkarten = new HashMap<Landkarte, String>();
    try {
      Files.list(Path.of(inputFolder))
          .parallel()
          .forEach(f -> landkarten.put(reader.readObject(f.toString()),
              String.valueOf(f.getFileName())));
    } catch (IOException  e) {
      LOGGER.log(Level.SEVERE,
          "Konnte input Dateien innerhalb " + inputFolder + " nicht lesen.");
      System.exit(1);
    }

    landkarten.forEach((l,s) -> l.normalisiereKenngroesse());

    landkarten.forEach((l,s) -> l.setIterationen(iterationen));

    landkarten.forEach((l,s) -> l.rechne(iterationen));

    var writer = new GnuPlotWriter();
    try {
      if (!Files.exists(Path.of(outputFolder))) {
        Files.createDirectory(Path.of(outputFolder));
      }
      var finalOutputFolder = outputFolder;
      landkarten.forEach((l,s) -> {
        var outputPath = finalOutputFolder + "/" + s + "_out.txt";
        if (Files.exists(Path.of(outputPath))) {
          LOGGER.log(Level.WARNING,
              "Datei " + outputPath + " existiert. Sie wird überschrieben.");
        }
        writer.write(l, outputPath);
      });
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE,
          "Konnte keine Dateien in den output Ordner: " + outputFolder + " schreiben.");
      System.exit(1);
    }
  }
}
