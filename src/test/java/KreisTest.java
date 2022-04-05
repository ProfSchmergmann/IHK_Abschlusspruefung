import com.cae.de.utils.la.Kreis;
import org.junit.jupiter.api.Test;

public class KreisTest {

  @Test
  public void getAbstand() {
    var k1 = new Kreis(0, 0, 10);
    var k2 = new Kreis(0, 5, 10);
    System.out.println(k1.getAbstand(k2));
  }
}
