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

  private static void iteriere(Landkarte landkarte) {
    var epsilon = 1e-10;

    // Abstoßungskräfte der Staaten bestimmen, bei denen die Kreise überlappen
    for (var entry : landkarte.getBeziehungen().entrySet()) {
      var staat = entry.getKey();
      var k1 = new Kreis(staat.getX(), staat.getY(), staat.getKenngroesse());
      for (var n : landkarte.getBeziehungen().entrySet()) {
        var staat2 = n.getKey();
        if (staat == staat2 || landkarte.getBeziehungen().get(staat).contains(staat2)) continue;
        if (k1.isInnerhalb(new Punkt(staat2.getX(), staat2.getY()))) {
          landkarte.addKraft(
              staat, staat2, (-1) * (staat.getKenngroesse() + staat2.getKenngroesse()));
        }
      }
    }

    // Anziehungs-/ Abstoßungskräfte der Nachbarstaaten bestimmen
    for (var entry : landkarte.getBeziehungen().entrySet()) {
      var staat = entry.getKey();
      var k1 = new Kreis(staat.getX(), staat.getY(), staat.getKenngroesse());
      for (var staat2 : entry.getValue()) {
        landkarte.addKraft(
            staat,
            staat2,
            k1.getAbstandZwischenKreisen(
                new Kreis(staat2.getX(), staat2.getY(), staat2.getKenngroesse())));
      }
    }

    var verschiebungen =
        landkarte.getSortedStaaten().stream()
            .collect(
                Collectors.toMap(
                    Staat::getIdentifier,
                    value -> new HashSet<Punkt>(),
                    (prev, next) -> next,
                    HashMap::new));

    // Kräfte auf sortierte Liste der Staaten anwenden und neue Punkte der HashMap hinzufügen
    for (var staat : landkarte.getSortedStaaten()) {
      for (var n : landkarte.getKreafte().get(staat).entrySet()) {
        if (n.getValue() > epsilon || n.getValue() < -epsilon) {
          var m1 = new Punkt(staat.getX(), staat.getY());
          var nachbarstaat = n.getKey();
          var m2 = new Punkt(nachbarstaat.getX(), nachbarstaat.getY());
          var m1new =
              (n.getValue() > 0)
                  ? m1.verschiebeInRichtung(m2, n.getValue() / 2)
                  : m1.verschiebeInGegenrichtung(m2, -n.getValue() / 2);
          verschiebungen.get(staat.getIdentifier()).add(m1new);
        }
      }
    }

    // Setze für jeden Staat einen neuen Mittelpunkt, basieren auf den vorher ausgerechneten Punkten
    for (var staat : landkarte.getSortedStaaten()) {
      // Bewege den Staat mit den meisten Nachbarn nicht
      if (staat.getIdentifier().equals(landkarte.getStaatMitMeistenNachbarn().getIdentifier()))
        continue;
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
    }

    landkarte.removeKraefte();
  }

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

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
