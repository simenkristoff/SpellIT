package spellit.core.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TurnExceptionTest {

  @Test
  @DisplayName("Should instantiate new TurnException")
  public void turnExceptionTest() {
    TurnException ex = new TurnException("Dette er en feil..");
    assertEquals(ex.getMessage(), "Dette er en feil..");
  }
}
