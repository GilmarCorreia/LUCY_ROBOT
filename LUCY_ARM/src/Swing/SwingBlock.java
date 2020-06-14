package Swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Polygon;

public class SwingBlock extends Polygon{
	
	public SwingBlock(Graphics g, int x, int y, int width, int height, String text) {
		g.setColor(new Color(10,150,255));
		g.fillRoundRect(x, y-(height/2), width, height,10,10);
		g.setColor(new Color(0,0,0));
		g.setFont(new Font("Arial", Font.BOLD, 16));
		g.drawString(text,x+11,y+7);
	}


}
