package com.cae.de.utils.io;

import com.cae.de.models.Landkarte;

/**
 * Writer um die Ausgabe für das Program GNUPlot lesbar zu machen.
 */
public class GnuPlotWriter implements IWriter<Landkarte> {

  @Override
  public boolean write(Landkarte landkarte, String pathToFile) {
    return false;
  }

  @Override
  public boolean write(Landkarte landkarte) {
    return false;
  }
}
