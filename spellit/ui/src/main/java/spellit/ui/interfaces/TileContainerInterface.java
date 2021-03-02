package spellit.ui.interfaces;

import spellit.ui.views.AbstractTile;

public interface TileContainerInterface {

	public boolean mouseIntersection(double mouseX, double mouseY);

	public AbstractTile getIntersectingTile(double mouseX, double mouseY);
}