package cn.pengzhaoqing.Q_learning;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import cn.pengzhaoqing.Q_learning.Variant;

public class Obstacle {

	public ArrayList<int[]> location = new ArrayList<int[]>();
	
	public void setLocation(ArrayList<int[]> location) {
		this.location=location;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.GRAY);
		for(int i=0;i<location.size();i++){
			g.fillRect(location.get(i)[1]*Variant.SCALE, location.get(i)[0]*Variant.SCALE, Variant.SCALE, Variant.SCALE);			
		}
	}

	public ArrayList<Rectangle> getRect() {
		ArrayList<Rectangle> result=new ArrayList<Rectangle>();
		for(int i=0;i<location.size();i++){
			result.add(new Rectangle(location.get(i)[1],location.get(i)[0], Variant.SCALE, Variant.SCALE));
		}
		return result;
	}
	
}
