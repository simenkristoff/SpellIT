package spellit.ui.interfaces;

import spellit.ui.views.AbstractTile;

/**
 * The Interface TileContainerInterface.
 */
public interface TileContainerInterface {

  /**
   * Mouse intersection.
   *
   * @param mouseX the mouse X-coordinate
   * @param mouseY the mouse Y-coordinate
   * @return true, if successful
   */
  public boolean mouseIntersection(double mouseX, double mouseY);

  /**
   * Gets the intersecting tile.
   *
   * @param mouseX the mouse X-coordinate
   * @param mouseY the mouse Y-coordinate
   * @return the intersecting tile
   */
  public AbstractTile getIntersectingTile(double mouseX, double mouseY);
}