package cn.pengzhaoqing.Q_learning;


import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import cn.pengzhaoqing.Q_learning.Variant;

public class Cliff {

	public ArrayList<int[]> location = new ArrayList<int[]>();
	
	public void setLocation(ArrayList<int[]> location) {
		this.location=location;
	}

	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		for(int i=0;i<location.size();i++){
			g.fillRect(location.get(i)[1]*Variant.SCALE, location.get(i)[0]*Variant.SCALE, Variant.SCALE, Variant.SCALE);			
		}
	}

}

