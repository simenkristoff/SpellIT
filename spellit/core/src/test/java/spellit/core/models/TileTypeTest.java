package spellit.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
public class TileTypeTest {

  private final List<TileType> types = List.of(TileType.DEFAULT, TileType.TW, TileType.DW,
      TileType.TL, TileType.DL, TileType.STAR);
  private final List<String> values = List.of("", "TW", "DW", "TL", "DL", "");
  private final List<String> classNames = List.of("", "triple-word", "double-word", "triple-letter",
      "double-letter", "star");

  @Test
  @Order(1)
  @DisplayName("Should return correct values")
  public void tileTypeTest() {
    for (int i = 0; i < types.size(); i++) {
      TileType type = types.get(i);
      assertEquals(type.getValue(), values.get(i));
      assertEquals(type.getClassName(), classNames.get(i));
    }
  }
}
