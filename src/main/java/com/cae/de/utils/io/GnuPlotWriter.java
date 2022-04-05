package com.cae.de.utils.io;

import com.cae.de.models.Landkarte;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Writer um die Ausgabe für das Program GNUPlot lesbar zu machen. */
public class GnuPlotWriter implements IWriter<Landkarte> {

  private static final Logger LOGGER = Logger.getLogger(GnuPlotWriter.class.getName());

  @Override
  public boolean write(Landkarte landkarte, String pathToFile) {
    var range = landkarte.getRangeForGnuPlot();
    var s = new StringBuilder();
    s.append("reset")
        .append("\n")
        .append("set xrange [")
        .append(range.key().key())
        .append(":")
        .append(range.key().value())
        .append("]")
        .append("\n")
        .append("set yrange [")
        .append(range.value().key())
        .append(":")
        .append(range.value().value())
        .append("]")
        .append("\n")
        .append("set size ratio 1.0")
        .append("\n")
        .append("set title \"")
        .append(landkarte.getKenngroesse())
        .append(", Iterationen: ")
        .append(landkarte.getIterationen())
        .append("\"")
        .append("\n")
        .append("unset xtics")
        .append("\n")
        .append("unset ytics")
        .append("\n")
        .append("$data << EOD")
        .append("\n");

    for (int i = 0; i < landkarte.getStaaten().size(); i++) {
      var staat = landkarte.getStaaten().get(i);
      s.append(staat.getX())
          .append("\s")
          .append(staat.getY())
          .append("\s")
          .append(staat.getKenngroesse())
          .append("\s")
          .append(staat.getIdentifier())
          .append("\s")
          .append(i)
          .append("\n");
    }

    s.append("EOD")
        .append("\n")
        .append("plot \\")
        .append("\n")
        .append("'$data' using 1:2:3:5 with circles lc var notitle, \\")
        .append("\n")
        .append("'$data' using 1:2:4:5 with labels font \"arial,9\" tc variable notitle");

    try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(pathToFile))) {
      LOGGER.log(Level.INFO, "Schreibe Landkarte mit Kenngröße: " + landkarte.getKenngroesse() + " in die Datei: " + pathToFile);
      bufferedWriter.write(s.toString());
      return true;
    } catch (IOException e) {
      LOGGER.log(Level.WARNING, "Konnte nicht in die Datei: " + pathToFile + " schreiben.");
      return false;
    }
  }

  @Override
  public boolean write(Landkarte landkarte) {
    return false;
  }
}
