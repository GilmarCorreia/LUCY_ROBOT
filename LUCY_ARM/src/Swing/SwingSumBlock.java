package Swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Polygon;

public class SwingSumBlock extends Polygon{
	
	private int x, y, diameter;
	
	public SwingSumBlock(Graphics g, int x, int y, int diameter) {
		setX(x);
		setY(y);
		setDiameter(diameter);
		g.setColor(new Color(0,0,0));
		g.drawOval(x-(diameter/2), y-(diameter/2), diameter, diameter);
		g.drawLine(x, y, (int) (x+(0.707106781*(diameter/2))), (int) (y-(0.707106781*(diameter/2))));
		g.drawLine(x, y, (int) (x+(0.707106781*(diameter/2))), (int) (y+(0.707106781*(diameter/2))));
		g.drawLine(x, y, (int) (x-(0.707106781*(diameter/2))), (int) (y-(0.707106781*(diameter/2))));
		g.drawLine(x, y, (int) (x-(0.707106781*(diameter/2))), (int) (y+(0.707106781*(diameter/2))));
		g.setFont(new Font("Arial", Font.BOLD, 12));
		g.drawString("sub",x-10,(int) (y+(diameter/2.5)));
	}

	private void setX(int data) {
		this.x=data;
	}
	
	private void setY(int data) {
		this.y=data;
	}
	
	private void setDiameter(int data) {
		this.diameter=data;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getDiameter() {
		return this.diameter;
	}
}
