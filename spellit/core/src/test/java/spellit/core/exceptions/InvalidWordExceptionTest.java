package spellit.core.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class InvalidWordExceptionTest {

  @Test
  @DisplayName("Should instantiate new InvalidWordException")
  public void invalidWordExceptionTest() {
    InvalidWordException ex = new InvalidWordException("fisk");
    assertEquals(ex.getMessage(), "Ordet 'fisk' finnes ikke i ordboken som brukes");
  }
}
