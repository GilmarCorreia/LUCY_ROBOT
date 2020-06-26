package Swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Polygon;

public class SwingBlock extends Polygon{
	private int x, y, width, height;
	
	public SwingBlock(Graphics g, int x, int y, int width, int height, String text) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		g.setColor(new Color(10,150,255));
		g.fillRoundRect(x, y-(height/2), width, height,10,10);
		g.setColor(new Color(0,0,0));
		g.setFont(new Font("Arial", Font.BOLD, 16));
		g.drawString(text,x+11,y+7);
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
