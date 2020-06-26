package Swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public class SwingArrow extends Polygon{
	private int x, y, width, height;
	public int RIGHT = 0, LEFT = 1, UP = 2, DOWN = 3; 
	public SwingArrow(Graphics g, int x, int y, int width, int height, int direction) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
	    g.setColor(new Color(0,0,0));
		g.fillRect(x, y, width, height);
		if(direction == RIGHT)
			g.fillPolygon(new int[]{x+width,x+20+width,x+width}, new int[]{(y+(height/2))-10,(y+(height/2)),(y+(height/2))+10}, 3);
		else if(direction == LEFT)
			g.fillPolygon(new int[]{x,x-20,x}, new int[]{(y+(height/2))-10,(y+(height/2)),(y+(height/2))+10}, 3);
		else if(direction == UP)
			g.fillPolygon(new int[]{(x+(width/2))-10,(x+(width/2)),(x+(width/2))+10},new int[]{y,y-20,y}, 3);
		else if(direction == DOWN)	
			g.fillPolygon(new int[]{(x+(width/2))-10,(x+(width/2)),(x+(width/2))+10},new int[]{y+height,y+20+height,y+height}, 3);
	}
	
	private void setX(int data) {
		this.x=data;
	}
	
	private void setY(int data) {
		this.y=data;
	}
	
	private void setWidth(int data) {
		this.width=data;
	}
	
	private void setHeight(int data) {
		this.height=data;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
}