package com.cae.de.models;

import com.cae.de.utils.Pair;
import com.cae.de.utils.algorithms.IStrategy;
import com.cae.de.utils.la.Kreis;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/** Klasse zur Representation aller gegebenen Staaten und deren Beziehungen. */
public class Landkarte {

  private static final Logger LOGGER = Logger.getLogger(Landkarte.class.getName());
  private final String kenngroesse;
  private final HashMap<Staat, HashMap<Staat, Double>> kreafte;
  private final IStrategy strategy;
  private final Staat staatMitMeistenNachbarn;
  private HashMap<Staat, HashSet<Staat>> beziehungen;
  private int iterationen;

  /**
   * Konstruktor, welcher die Liste der Staaten, die Kenngröße, die Beziehungen und die Strategie
   * setzt.
   *
   * @param kenngroesse die Kenngröße (z. B.: Bierkonsum)
   * @param beziehungen die Nachbarschaftsbeziehungen der Staaten
   * @param strategy die Strategie für die Bewegung der Mittelpunkte
   */
  public Landkarte(
      String kenngroesse, HashMap<Staat, HashSet<Staat>> beziehungen, IStrategy strategy) {
    this.kenngroesse = kenngroesse;
    this.beziehungen = beziehungen;
    this.strategy = strategy;
    this.kreafte = new HashMap<>();
    this.beziehungen.forEach((key, value) -> this.kreafte.put(key, new HashMap<>()));
    this.staatMitMeistenNachbarn =
        this.getBeziehungen().entrySet().stream()
            .sorted(Comparator.comparingInt(e -> e.getValue().size()))
            .toList()
            .get(0)
            .getKey();
    LOGGER.log(
        Level.INFO,
        "Neue Landkarte mit der Kenngröße: "
            + this.kenngroesse
            + " und den Beziehungen: "
            + this.getBeziehungentoString()
            + " initialisiert.");
  }

  public static HashMap<Staat, HashSet<Staat>> deepCopyBeziehungen(
      HashMap<Staat, HashSet<Staat>> beziehungen) {
    var copyMap = new HashMap<Staat, HashSet<Staat>>();
    var staaten = beziehungen.keySet().stream().map(Staat::new).toList();
    for (var entry : beziehungen.entrySet()) {
      var nachbarn =
          entry.getValue().stream()
              .map(
                  staat ->
                      staaten.stream()
                          .filter(staat1 -> staat1.getIdentifier().equals(staat.getIdentifier()))
                          .findFirst()
                          .get())
              .collect(Collectors.toCollection(HashSet::new));
      var staat =
          staaten.stream()
              .filter(staat1 -> staat1.getIdentifier().equals(entry.getKey().getIdentifier()))
              .findFirst()
              .get();
      copyMap.put(staat, nachbarn);
    }
    return copyMap;
  }

  public double getAbstandZwischenNachbarStaaten() {
    return this.getBeziehungen().entrySet().stream()
        .map(
            (staatHashSetEntry) -> {
              var s1 = staatHashSetEntry.getKey();
              var k1 = new Kreis(s1.getX(), s1.getY(), s1.getKenngroesse());
              return staatHashSetEntry.getValue().stream()
                  .map(
                      s2 ->
                          Math.abs(
                              new Kreis(s2.getX(), s2.getY(), s2.getKenngroesse())
                                  .getAbstandZwischenKreisen(k1)))
                  .mapToDouble(Double::doubleValue)
                  .sum();
            })
        .mapToDouble(Double::doubleValue)
        .map(d -> d / 2)
        .sum();
  }

  public Staat getStaatMitMeistenNachbarn() {
    return this.staatMitMeistenNachbarn;
  }

  public void normalisiereKenngroesse() {
    var maxKenngroesse = this.getSortedStaaten().get(0).getKenngroesse();
    for (var staat : this.getSortedStaaten()) {
      if (staat.getKenngroesse() > maxKenngroesse) maxKenngroesse = staat.getKenngroesse();
    }
    for (var staat : this.getSortedStaaten()) {
      staat.setKenngroesse(staat.getKenngroesse() / maxKenngroesse);
    }
  }

  public double getMinKenngroesse() {
    var minKenngroesse = this.getSortedStaaten().get(0).getKenngroesse();
    for (var staat : this.getSortedStaaten()) {
      if (staat.getKenngroesse() < minKenngroesse) minKenngroesse = staat.getKenngroesse();
    }
    return minKenngroesse;
  }

  public void addKraft(Staat staat, Staat nachbarStaat, double kraft) {
    this.kreafte.get(staat).put(nachbarStaat, kraft);
  }

  public void removeKraefte() {
    this.kreafte.forEach((key, value) -> value.clear());
  }

  public int getIterationen() {
    return this.iterationen;
  }

  public void setIterationen(int iterationen) {
    this.iterationen = iterationen;
  }

  public String getKenngroesse() {
    return this.kenngroesse;
  }

  public IStrategy getStrategy() {
    return this.strategy;
  }

  /**
   * Rechnet mit der gegebenen Strategie und der Anzahl an Iterationen
   *
   * @param iterationen die Anzahl der Iterationen
   */
  public void rechne(int iterationen) {
    this.strategy.rechne(this, iterationen);
  }

  public double getMinX() {
    double minX =
        this.getSortedStaaten().get(0).getX() - this.getSortedStaaten().get(0).getKenngroesse();
    for (var staat : this.getSortedStaaten()) {
      var value = staat.getX() - staat.getKenngroesse();
      if (value < minX) minX = value;
    }
    return minX;
  }

  public double getMaxX() {
    double maxX =
        this.getSortedStaaten().get(0).getX() + this.getSortedStaaten().get(0).getKenngroesse();
    for (var staat : this.getSortedStaaten()) {
      var value = staat.getX() + staat.getKenngroesse();
      if (value > maxX) maxX = value;
    }
    return maxX;
  }

  public double getMinY() {
    double minY =
        this.getSortedStaaten().get(0).getY() - this.getSortedStaaten().get(0).getKenngroesse();
    for (var staat : this.getSortedStaaten()) {
      var value = staat.getY() - staat.getKenngroesse();
      if (value < minY) minY = value;
    }
    return minY;
  }

  public double getMaxY() {
    double maxY =
        this.getSortedStaaten().get(0).getY() + this.getSortedStaaten().get(0).getKenngroesse();
    for (var staat : this.getSortedStaaten()) {
      var value = staat.getY() + staat.getKenngroesse();
      if (value > maxY) maxY = value;
    }
    return maxY;
  }

  public Pair<Pair<Double, Double>, Pair<Double, Double>> getRangeForGnuPlot() {
    var xMin = this.getMinX();
    var xMax = this.getMaxX();
    var yMin = this.getMinY();
    var yMax = this.getMaxY();
    var absX = Math.abs(xMax - xMin);
    var absY = Math.abs(yMax - yMin);
    var diff = Double.compare(absX, absY);
    LOGGER.log(Level.INFO, "Die Differenz zwischen den x und y Werten war: " + diff);
    if (diff > 0) {
      LOGGER.log(Level.INFO, "Daher wird y mit " + absX / 2 + " angepasst");
      yMin -= absX / 2;
      yMax += absX / 2;
    } else if (diff < 0) {
      LOGGER.log(Level.INFO, "Daher wird x mit " + absY / 2 + " angepasst");
      xMin -= absY / 2;
      xMax += absX / 2;
    }
    return new Pair<>(new Pair<>(xMin, xMax), new Pair<>(yMin, yMax));
  }

  @Override
  public String toString() {
    return "Landkarte{"
        + "kenngroesse='"
        + this.kenngroesse
        + '\''
        + ", beziehungen="
        + this.beziehungen
        + ", kreafte="
        + this.kreafte
        + ", strategy="
        + this.strategy
        + ", iterationen="
        + this.iterationen
        + '}';
  }

  public String getBeziehungentoString() {
    var s = new StringBuilder();
    for (var entry : this.beziehungen.entrySet()) {
      s.append(entry.getKey().getIdentifier()).append(": [");
      boolean appended = false;
      for (var nachbar : entry.getValue()) {
        s.append(nachbar.getIdentifier()).append(", ");
        appended = true;
      }
      if (appended) s.delete(s.length() - 2, s.length());
      s.append("]").append(", ");
    }
    s.delete(s.length() - 2, s.length());
    return s.toString();
  }

  public HashMap<Staat, HashMap<Staat, Double>> getKreafte() {
    return this.kreafte;
  }

  public HashMap<Staat, HashSet<Staat>> getBeziehungen() {
    return this.beziehungen;
  }

  public void setBeziehungen(HashMap<Staat, HashSet<Staat>> staatHashSetHashMap) {
    this.beziehungen = staatHashSetHashMap;
  }

  public List<Staat> getSortedStaaten() {
    return this.beziehungen.keySet().stream()
        .sorted(Comparator.comparingDouble(Staat::getKenngroesse))
        .toList();
  }
}
