package com.cae.de.utils.algorithms;

import com.cae.de.models.Landkarte;
import com.cae.de.models.Staat;
import com.cae.de.utils.la.Kreis;
import com.cae.de.utils.la.Punkt;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    var epsilon = 1e-6;

    // Abstoßungskräfte der Staaten bestimmen, bei denen die Kreise überlappen
    landkarte
        .getBeziehungen()
        .forEach(
            (staat, nachbarn) -> {
              var k1 = new Kreis(new Punkt(staat.getX(), staat.getY()), staat.getKenngroesse());
              landkarte.getBeziehungen().keySet().stream()
                  .filter(
                      nachbar ->
                          staat != nachbar
                              && !landkarte.getBeziehungen().get(staat).contains(nachbar))
                  .filter(
                      nachbar ->
                          k1.getAbstandZwischenKreisen(
                                  new Kreis(
                                      new Punkt(nachbar.getX(), nachbar.getY()),
                                      nachbar.getKenngroesse()))
                              < 0)
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
              var k1 = new Kreis(new Punkt(staat.getX(), staat.getY()), staat.getKenngroesse());
              nachbarn.forEach(
                  nachbar ->
                      landkarte.addKraft(
                          staat,
                          nachbar,
                          k1.getAbstandZwischenKreisen(
                              new Kreis(
                                  new Punkt(nachbar.getX(), nachbar.getY()),
                                  nachbar.getKenngroesse()))));
            });

    var verschiebungen =
        landkarte.getStaatenNachKenngroesseSortiert().stream()
            .collect(
                Collectors.toMap(
                    staat -> staat,
                    value -> new HashSet<Punkt>(),
                    (prev, next) -> next,
                    HashMap::new));

    // Kräfte auf sortierte Liste der Staaten anwenden und neue Punkte der HashMap hinzufügen
    landkarte
        .getStaatenNachKenngroesseSortiert()
        .forEach(
            staat ->
                landkarte
                    .getKreafte()
                    .get(staat)
                    .forEach(
                        (nachbarstaat, kraft) -> {
                          var m1 = new Punkt(staat.getX(), staat.getY());
                          var m2 = new Punkt(nachbarstaat.getX(), nachbarstaat.getY());
                          var m1new =
                              (nachbarstaat.equals(landkarte.getStaatMitMeistenNachbarn()))
                                  ? m1.verschiebeInRichtung(m2, kraft)
                                  : m1.verschiebeInRichtung(m2, kraft / 2);
                          verschiebungen.get(staat).add(m1new);
                        }));

    // Setze für jeden Staat einen neuen Mittelpunkt, basieren auf den vorher ausgerechneten
    // Punkten, wobei der Staat mit den meisten Nachbarn nicht beachtet wird und der Staat nicht
    // verschoben wird, wenn der neue Mittelpunkt in irgendeinem anderen Kreis liegt.
    verschiebungen.keySet().stream()
        .filter(staat -> !staat.equals(landkarte.getStaatMitMeistenNachbarn()))
        .forEach(
            staat -> {
              var mNeu = Punkt.getMittelpunkt(verschiebungen.get(staat));
              var kNeu = new Kreis(mNeu, staat.getKenngroesse());
              var neuerKreisAusserhalb =
                  getKreiseAllerAnderenStaaten(staat, landkarte.getStaatenNachKenngroesseSortiert())
                      .stream()
                      .noneMatch(kreis -> kreis.getAbstandZwischenKreisen(kNeu) < epsilon);
              if (neuerKreisAusserhalb) {
                LOGGER.log(
                    Level.INFO,
                    "Verschiebe: "
                        + staat.getIdentifier()
                        + " von "
                        + new Punkt(staat.getX(), staat.getY())
                        + " nach "
                        + mNeu);
                staat.setX(mNeu.x());
                staat.setY(mNeu.y());
              }
            });

    landkarte.removeKraefte();
  }

  /**
   * Berechnet die Kreise aller anderen Staaten außer dem des gegebenen Staats.
   *
   * @param staat der Staat, von dem der Kreis nicht berechnet werden soll
   * @param staaten alle Staaten
   * @return ein Set der Kreise aller anderen Staaten
   */
  private static Set<Kreis> getKreiseAllerAnderenStaaten(Staat staat, List<Staat> staaten) {
    return staaten.stream()
        .filter(staat1 -> !staat1.equals(staat))
        .map(staat1 -> new Kreis(new Punkt(staat1.getX(), staat1.getY()), staat1.getKenngroesse()))
        .collect(Collectors.toSet());
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
    var i = 1;
    var minimumIterationen = i;
    var beziehungenMitKleinstemAbstand = Landkarte.deepCopyBeziehungen(landkarte.getBeziehungen());
    var abstandZwischenAllenNachbarn = landkarte.getAbstandZwischenNachbarStaaten();
    while (i <= maxIterationen) {
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
