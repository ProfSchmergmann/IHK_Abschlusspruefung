package com.cae.de.framework;

/**
 * Interface ISolver, welches das EVA-Prinzip widerspiegelt. Dieses Interface beinhaltet alle
 * Methoden um ein Problem mit Ein- und Ausgabedaten zu lösen.
 *
 * @param <P> das Eingabeobjekt
 * @param <R> das Ergebnisobjekt
 */
public interface ISolver<P, R> {

  void done();

  /**
   * Methode, um mit einem gegebenen Producer eine Eingabe zu lesen.
   *
   * @param producer der Producer
   * @return das this Objekt, für Chaining
   * @throws EVAException falls intern eine {@link java.io.IOException} geworfen wird, um alles in
   *     der aufrufenden Methode behandeln zu können
   */
  ISolver<P, R> input(Producer<P> producer) throws EVAException;

  /**
   * Methode, um mit einem gegebenen Consumer eine Ausgabe zu produzieren.
   *
   * @param consumer der Consumer
   * @return das this Objekt, für Chaining
   * @throws EVAException falls intern eine {@link java.io.IOException} geworfen wird, um alles in
   *     der aufrufenden Methode behandeln zu können
   */
  ISolver<P, R> output(Consumer<Data<P, R>> consumer) throws EVAException;

  /**
   * Methode, um das Problem mit den vorher eingelesenen Daten zu lösen.
   *
   * @return das this Objekt, für Chaining
   */
  ISolver<P, R> process();

  /**
   * Data Klasse, welche dafür gedacht ist, das Problem mit Eingabeobjekt und Ergebnisobjekt zu
   * speichern.
   *
   * @param <P> Typ, um das Problem zu speichern
   * @param <R> Typ, um das Ergebnis zu speichern
   */
  class Data<P, R> {
    private final P parameter;
    private R result;

    public Data(P parameter, R result) {
      this.parameter = parameter;
      this.result = result;
    }

    public P getParameter() {
      return this.parameter;
    }

    public R getResult() {
      return this.result;
    }

    public void setResult(R result) {
      this.result = result;
    }
  }
}
