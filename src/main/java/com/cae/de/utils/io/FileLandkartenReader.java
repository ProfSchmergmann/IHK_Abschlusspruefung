package com.cae.de.utils.io;

import com.cae.de.models.Landkarte;
import com.cae.de.models.Staat;
import com.cae.de.utils.algorithms.BruteForceStrategy;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileLandkartenReader  implements IReader<Landkarte> {

  private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

  @Override
  public Landkarte readObject(String pathToFile) {
    LOGGER.log(Level.INFO, "Trying to read " + pathToFile);
    try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
      var kenngroesse = br.readLine();
      br.readLine();
      var staatenListe = new ArrayList<Staat>();
      while (true) {
        var line = br.readLine();
        if (line.trim().equals("# Nachbarschaften")) break;
        var splittedLine = line.split("\t");
        staatenListe.add(new Staat(splittedLine[0],
            Double.parseDouble(splittedLine[1]),
            Double.parseDouble(splittedLine[2]),
            Double.parseDouble(splittedLine[3])));
      }
      var beziehungen = new HashMap<String, HashSet<String>>();
      while (true) {
        var line = br.readLine();
        if (line == null) break;
        var splittedLine = line.split(":");
        var firstIdent = splittedLine[0];
        if (!beziehungen.containsKey(firstIdent)) {
          beziehungen.put(firstIdent, new HashSet<>());
        }
        for (var ident : splittedLine[1].split("\s")) {
          beziehungen.get(firstIdent).add(ident);
          if (!beziehungen.containsKey(ident)) {
            beziehungen.put(ident, new HashSet<>());
          }
          beziehungen.get(ident).add(firstIdent);
        }
      }
      return new Landkarte(staatenListe, kenngroesse, beziehungen, new BruteForceStrategy());
    } catch (IOException e) {
      LOGGER.log(Level.WARNING, "Could not read file: " + pathToFile);
      return null;
    }
  }

  @Override
  public String read(String pathToFile) {
    return null;
  }
}
