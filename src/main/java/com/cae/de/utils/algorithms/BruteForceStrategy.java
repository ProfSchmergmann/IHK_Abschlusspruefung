package com.cae.de.utils.algorithms;

import com.cae.de.models.Landkarte;
import com.cae.de.models.Staat;
import com.cae.de.utils.la.Kreis;
import com.cae.de.utils.la.Punkt;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/** Eine erste Strategie, welche wahrscheinlich noch nicht optimal ist, deshalb der Name. */
public class BruteForceStrategy implements IStrategy {

  private static final Logger LOGGER = Logger.getLogger(BruteForceStrategy.class.getName());

  /**
   * Private Hilfsmethode für den Algorithmus. Zuerst werden alle Abstoßungskräfte zwischen den
   * Staaten berechnet, welche laut der Beziehungen keine Nachbarn sind, bei denen allerdings die
   * Kreise überlappen. Danach werden alle Abstoßungs- und Anziehungskräfte der Nachbarstaaten jedes
   * Staats berechnet. Ist die Berechnung der Kräfte fertig, wird für jeden staat ein Eintrag in
   * einer HashMap angelegt, worin als Wert alle Punkte gespeichert werden, wohin der Staat
   * potenziell verschoben werden könnte. Daraufhin wird jeder Staat jeweils zum Mittelpunkt dieser
   * Punkte geschoben, wobei der Staat mit den meisten Nachbarn nie verschoben wird. Anschließend
   * werden wieder alle Kräfte entfernt.
   *
   * @param landkarte die gegebene Landkarte
   */
  private static void iteriere(Landkarte landkarte) {
    var epsilon = 1e-10;

    // Abstoßungskräfte der Staaten bestimmen, bei denen die Kreise überlappen
    landkarte
        .getBeziehungen()
        .forEach(
            (staat, nachbarn) -> {
              var k1 = new Kreis(staat.getX(), staat.getY(), staat.getKenngroesse());
              landkarte.getBeziehungen().keySet().stream()
                  .filter(
                      staat2 ->
                          staat != staat2
                              && !landkarte.getBeziehungen().get(staat).contains(staat2))
                  .filter(staat2 -> k1.isInnerhalb(new Punkt(staat2.getX(), staat2.getY())))
                  .forEach(
                      nachbar ->
                          landkarte.addKraft(
                              staat,
                              nachbar,
                              (-1) * (staat.getKenngroesse() + nachbar.getKenngroesse())));
            });

    // Anziehungs-/ Abstoßungskräfte der Nachbarstaaten bestimmen
    landkarte
        .getBeziehungen()
        .forEach(
            (staat, nachbarn) -> {
              var k1 = new Kreis(staat.getX(), staat.getY(), staat.getKenngroesse());
              nachbarn.forEach(
                  nachbar ->
                      landkarte.addKraft(
                          staat,
                          nachbar,
                          k1.getAbstandZwischenKreisen(
                              new Kreis(
                                  nachbar.getX(), nachbar.getY(), nachbar.getKenngroesse()))));
            });

    var verschiebungen =
        landkarte.getStaatenNachKenngroesseSortiert().stream()
            .collect(
                Collectors.toMap(
                    Staat::getIdentifier,
                    value -> new HashSet<Punkt>(),
                    (prev, next) -> next,
                    HashMap::new));

    // Kräfte auf sortierte Liste der Staaten anwenden und neue Punkte der HashMap hinzufügen
    landkarte
        .getStaatenNachKenngroesseSortiert()
        .forEach(
            staat ->
                landkarte.getKreafte().get(staat).entrySet().stream()
                    .filter(n -> n.getValue() > epsilon || n.getValue() < -epsilon)
                    .forEach(
                        n -> {
                          var m1 = new Punkt(staat.getX(), staat.getY());
                          var nachbarstaat = n.getKey();
                          var m2 = new Punkt(nachbarstaat.getX(), nachbarstaat.getY());
                          var m1new =
                              (n.getValue() > 0)
                                  ? m1.verschiebeInRichtung(m2, n.getValue() / 2)
                                  : m1.verschiebeInGegenrichtung(m2, -n.getValue() / 2);
                          verschiebungen.get(staat.getIdentifier()).add(m1new);
                        }));

    // Setze für jeden Staat einen neuen Mittelpunkt, basieren auf den vorher ausgerechneten
    // Punkten, wobei der Staat mit den meisten Nachbarn nicht beachtet wird
    landkarte.getStaatenNachKenngroesseSortiert().stream()
        .filter(
            staat ->
                !staat
                    .getIdentifier()
                    .equals(landkarte.getStaatMitMeistenNachbarn().getIdentifier()))
        .forEach(
            staat -> {
              var p = Punkt.getMittelpunkt(verschiebungen.get(staat.getIdentifier()));
              LOGGER.log(
                  Level.INFO,
                  "Verschiebe: "
                      + staat.getIdentifier()
                      + " von "
                      + new Punkt(staat.getX(), staat.getY())
                      + " nach "
                      + p);
              staat.setX(p.x());
              staat.setY(p.y());
            });

    landkarte.removeKraefte();
  }

  /**
   * Der Algorithmus der {@link BruteForceStrategy} berechnet zuerst den Abstand zwischen allen
   * Nachbarn und speichert dazu die aktuellen Beziehungen inklusive der Staaten. Dann folgt eine
   * vorgegebene Anzahl an Iterationen, wobei der neue Abstand der Nachbarstaaten immer mit dem
   * bisher kleinsten Abstand verglichen wird, und wenn dieser kleiner ist als der andere, wird er
   * ausgetauscht und auch die Beziehungen dieser Iteration gespeichert. Wenn die vorgegebene Anzahl
   * an Iterationen erreicht ist, werden die benötigten Iterationen, sowie die Beziehungen in dem
   * übergebenen Landkarten Objekt gesetzt.
   *
   * @param landkarte die Landkarte
   * @param maxIterationen die maximalen Iterationen bis zum Abbruch
   */
  @Override
  public void rechne(Landkarte landkarte, int maxIterationen) {
    var i = 0;
    var minimumIterationen = 0;
    var beziehungenMitKleinstemAbstand = Landkarte.deepCopyBeziehungen(landkarte.getBeziehungen());
    var abstandZwischenAllenNachbarn = landkarte.getAbstandZwischenNachbarStaaten();
    while (i < maxIterationen) {
      iteriere(landkarte);
      var neuerAbstand = landkarte.getAbstandZwischenNachbarStaaten();
      if (abstandZwischenAllenNachbarn > neuerAbstand) {
        abstandZwischenAllenNachbarn = neuerAbstand;
        beziehungenMitKleinstemAbstand = Landkarte.deepCopyBeziehungen(landkarte.getBeziehungen());
        minimumIterationen = i;
      }
      i++;
    }
    landkarte.setBeziehungen(beziehungenMitKleinstemAbstand);
    landkarte.setIterationen(minimumIterationen);
  }
}
