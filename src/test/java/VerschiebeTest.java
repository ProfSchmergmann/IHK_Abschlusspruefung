import com.cae.de.utils.la.Punkt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VerschiebeTest {

  @Test
  public void verschiebe() {
    var p1 = new Punkt(0, 0);
    var p2 = new Punkt(0, 5);
    var p1Strich = p1.verschiebeInRichtung(p2, 6);
    var p2Strich = p2.verschiebeInRichtung(p1, 6);
    Assertions.assertEquals(6, p1Strich.y());
    Assertions.assertEquals(-1, p2Strich.y());
  }
}
