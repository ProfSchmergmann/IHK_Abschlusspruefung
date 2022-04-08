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
    this.kreafte =
        this.getStaatenNachKenngroesseSortiert().stream()
            .collect(
                Collectors.toMap(
                    staat -> staat, value -> new HashMap<>(), (prev, next) -> next, HashMap::new));
    this.staatMitMeistenNachbarn =
        this.getBeziehungen().entrySet().stream()
            .max(Comparator.comparingInt(e -> e.getValue().size()))
            .get()
            .getKey();
    LOGGER.log(
        Level.INFO,
        "Neue Landkarte mit der Kenngröße: "
            + this.kenngroesse
            + " und den Beziehungen: "
            + this.getBeziehungentoString()
            + " initialisiert."
            + "\nStaat mit meisten Nachbarn:"
            + this.staatMitMeistenNachbarn);
  }

  /**
   * Statische Methode, welche dafür da ist eine "DeepCopy" der gegebenen HashMap anzulegen. Diese
   * Methode nutzt streams und den Copy Konstruktor der Klasse {@link Staat}.
   *
   * @param beziehungen die HashMap, die kopiert werden soll
   * @return eine deep copy der gegebenen HashMap
   */
  public static HashMap<Staat, HashSet<Staat>> deepCopyBeziehungen(
      HashMap<Staat, HashSet<Staat>> beziehungen) {
    var copyMap = new HashMap<Staat, HashSet<Staat>>();
    var staaten = beziehungen.keySet().stream().map(Staat::new).toList();
    beziehungen.forEach(
        (key, value) -> {
          var nachbarn =
              value.stream()
                  .map(
                      nachbar ->
                          staaten.stream()
                              .filter(
                                  staat1 -> staat1.getIdentifier().equals(nachbar.getIdentifier()))
                              .findFirst()
                              .get())
                  .collect(Collectors.toCollection(HashSet::new));
          var staat =
              staaten.stream()
                  .filter(staat1 -> staat1.getIdentifier().equals(key.getIdentifier()))
                  .findFirst()
                  .get();
          copyMap.put(staat, nachbarn);
        });
    return copyMap;
  }

  /**
   * Diese Methode berechnet den Abstand aller Nachbarstaaten, also aller Staaten, bei denen der
   * direkte Nachbar angegeben wurde. Eventuelle andere Beziehungen werden nicht beachtet. Für die
   * Abstandsberechnung wird die Methode {@link Kreis#getAbstandZwischenKreisen(Kreis)} genutzt,
   * wobei zu dem Ergebnis 1 addiert wird, falls die Kreise sich schneiden, sodass eine
   * Überschneidung als größtmöglicher Wert gewertet wird.
   *
   * @return den Abstand der zwischen allen benachbarten Staaten
   */
  public double getAbstandZwischenNachbarStaaten() {
    return this.getBeziehungen().entrySet().stream()
        .map(
            staatHashSetEntry -> {
              var s1 = staatHashSetEntry.getKey();
              var k1 = new Kreis(s1.getX(), s1.getY(), s1.getKenngroesse());
              return staatHashSetEntry.getValue().stream()
                  .map(
                      s2 -> {
                        var abstand =
                            new Kreis(s2.getX(), s2.getY(), s2.getKenngroesse())
                                .getAbstandZwischenKreisen(k1);
                        return abstand > 0 ? abstand : 1;
                      })
                  .mapToDouble(Double::doubleValue)
                  .sum();
            })
        .mapToDouble(Double::doubleValue)
        .map(d -> d / 2)
        .sum();
  }

  /**
   * Getter für den Staat mit den meisten Nachbarn.
   *
   * @return der Staat mit den meisten Nachbarn.
   */
  public Staat getStaatMitMeistenNachbarn() {
    return this.staatMitMeistenNachbarn;
  }

  /**
   * Methode zum Normalisieren der Kenngröße. Intern werden hier streams genutzt, um zuerst den
   * maximalen Wert herauszufinden, welcher später als Teiler für alle anderen Werte genutzt wird,
   * sodass alle Kenngrößen im Intervall [0,1] liegen.
   */
  public void normalisiereKenngroesse() {
    var maxKenngroesse =
        this.beziehungen.keySet().stream().mapToDouble(Staat::getKenngroesse).max().orElse(1);
    this.beziehungen
        .keySet()
        .forEach(staat -> staat.setKenngroesse(staat.getKenngroesse() / maxKenngroesse));
  }

  /**
   * Fügt eine Anziehungs- oder Abstoßungskraft zu der {@link #kreafte} Map hinzu.
   *
   * @param staat der eine Staat
   * @param nachbarStaat der andere Staat
   * @param kraft die zwischen beiden Staaten wirkende Kraft
   */
  public void addKraft(Staat staat, Staat nachbarStaat, double kraft) {
    this.kreafte.get(staat).put(nachbarStaat, kraft);
  }

  /**
   * Löscht alle Kräfte aus der {@link #kreafte} Map, wobei allerdings nur die dazugehörigen
   * HashSets geleert werden.
   */
  public void removeKraefte() {
    this.kreafte.forEach((key, value) -> value.clear());
  }

  /**
   * Getter für die Iterationen.
   *
   * @return die Anzahl der Iterationen
   */
  public int getIterationen() {
    return this.iterationen;
  }

  /**
   * Setter für die Iterationen.
   *
   * @param iterationen die Anzahl der Iterationen
   */
  public void setIterationen(int iterationen) {
    this.iterationen = iterationen;
  }

  /**
   * Getter für die Kenngröße.
   *
   * @return die Kenngröße
   */
  public String getKenngroesse() {
    return this.kenngroesse;
  }

  /**
   * Getter für die genutzte Strategie.
   *
   * @return die genutzte Strategie
   */
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

  /**
   * Berechnet unter Nutzung von streams den minimalen x-Wert. Falls dies nicht funktioniert, wird
   * {@link Double#MIN_VALUE} zurückgegeben.
   *
   * @return der minimale x-Wert
   */
  public double getMinX() {
    return this.beziehungen.keySet().stream()
        .mapToDouble(staat -> staat.getX() - staat.getKenngroesse())
        .min()
        .orElse(Double.MIN_VALUE);
  }

  /**
   * Berechnet unter Nutzung von streams den maximalen x-Wert. Falls dies nicht funktioniert, wird
   * {@link Double#MAX_VALUE} zurückgegeben.
   *
   * @return der maximale x-Wert
   */
  public double getMaxX() {
    return this.beziehungen.keySet().stream()
        .mapToDouble(staat -> staat.getX() + staat.getKenngroesse())
        .max()
        .orElse(Double.MAX_VALUE);
  }

  /**
   * Berechnet unter Nutzung von streams den minimalen y-Wert. Falls dies nicht funktioniert, wird
   * {@link Double#MIN_VALUE} zurückgegeben.
   *
   * @return der minimale y-Wert
   */
  public double getMinY() {
    return this.beziehungen.keySet().stream()
        .mapToDouble(staat -> staat.getY() - staat.getKenngroesse())
        .min()
        .orElse(Double.MIN_VALUE);
  }

  /**
   * Berechnet unter Nutzung von streams den maximalen y-Wert. Falls dies nicht funktioniert, wird
   * {@link Double#MAX_VALUE} zurückgegeben.
   *
   * @return der maximale y-Wert
   */
  public double getMaxY() {
    return this.beziehungen.keySet().stream()
        .mapToDouble(staat -> staat.getY() + staat.getKenngroesse())
        .max()
        .orElse(Double.MAX_VALUE);
  }

  /**
   * Berechnet den Bereich für die Ausgabe von GnuPlot in der Art, dass der x-Bereich genauso groß
   * wie der y-Bereich ist, sodass beide Achsen gleich lang sind.
   *
   * @return den Bereich von x und y, sodass key().key() == xMin, key().value() == xMax,
   *     value().key() == yMin, value().value() = yMax
   */
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

  /**
   * Berechnet einen schön formatierten String aller Beziehungen. Sinnvoll für Loggingausgaben.
   *
   * @return einen formatierten String aller Beziehungen
   */
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

  /**
   * Getter für die Kräfte zwischen den Staaten.
   *
   * @return die Kräfte zwischen allen Staaten
   */
  public HashMap<Staat, HashMap<Staat, Double>> getKreafte() {
    return this.kreafte;
  }

  /**
   * Getter für die Beziehungen zwischen allen Staaten.
   *
   * @return die Beziehungen zwischen allen Staaten
   */
  public HashMap<Staat, HashSet<Staat>> getBeziehungen() {
    return this.beziehungen;
  }

  /**
   * Setter für die Beziehungen zwischen den Staaten. Sinnvoll für eine Strategie, welche irgendeine
   * Art Qualitätskontrolle hat und die Beziehungen am Ende der Iterationen neu setzen möchte.
   *
   * @param staatHashSetHashMap die HashMap aller Beziehungen
   */
  public void setBeziehungen(HashMap<Staat, HashSet<Staat>> staatHashSetHashMap) {
    this.beziehungen = staatHashSetHashMap;
  }

  /**
   * Berechnet eine Liste aller Staaten sortiert nach der Kenngröße.
   *
   * @return die Liste der Staaten sortiert nach der Kenngröße
   */
  public List<Staat> getStaatenNachKenngroesseSortiert() {
    return this.beziehungen.keySet().stream()
        .sorted(Comparator.comparingDouble(Staat::getKenngroesse))
        .toList();
  }
}
