package Swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public class SwingArrow extends Polygon{
	public SwingArrow(Graphics g, int x, int y, int width, int height) {
	    g.setColor(new Color(0,0,0));
		g.fillRect(x, y, width, height);
	    g.fillPolygon(new int[]{x+width,x+20+width,x+width}, new int[]{(y+(height/2))-10,(y+(height/2)),(y+(height/2))+10}, 3);
	}
}