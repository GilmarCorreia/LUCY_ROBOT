package Swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Polygon;

public class SwingSumBlock extends Polygon{
	
	public SwingSumBlock(Graphics g, int x, int y, int diameter) {
		g.setColor(new Color(0,0,0));
		g.drawOval(x-(diameter/2), y-(diameter/2), diameter, diameter);
		g.drawLine(x, y, (int) (x+(0.707106781*(diameter/2))), (int) (y-(0.707106781*(diameter/2))));
		g.drawLine(x, y, (int) (x+(0.707106781*(diameter/2))), (int) (y+(0.707106781*(diameter/2))));
		g.drawLine(x, y, (int) (x-(0.707106781*(diameter/2))), (int) (y-(0.707106781*(diameter/2))));
		g.drawLine(x, y, (int) (x-(0.707106781*(diameter/2))), (int) (y+(0.707106781*(diameter/2))));
		g.setFont(new Font("Arial", Font.BOLD, 12));
		g.drawString("sub",x-10,(int) (y+(diameter/2.5)));
	}

}
