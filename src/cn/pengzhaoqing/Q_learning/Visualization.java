package cn.pengzhaoqing.Q_learning;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.text.DecimalFormat;

public class Visualization {

	public DecimalFormat df = new DecimalFormat("0.0");
	private Agent agent;
	private Font font = new Font("TimesRoman", Font.BOLD, Variant.SCALE / 8);

	public Visualization(Agent agent) {
		this.agent = agent;
	}

	public String format(double num) {
		return String.valueOf(df.format(num));
	}

	public void drawQtable(Graphics g) {
		for (int i = 0; i < agent.Qtable.length; i++) {
			for (int j = 0; j < agent.Qtable[i].length; j++) {
				for (int k = 0; k < agent.Qtable[i][j].length; k++) {
					Rectangle rect = new Rectangle(j * Variant.SCALE, i
							* Variant.SCALE, Variant.SCALE, Variant.SCALE);
					drawcenterString(g, format(agent.Qtable[i][j][k]), rect, font, k);
				}
			}
		}
	}

	public void drawRoute(Graphics g) {
		for (int i = 0; i < agent.Qtable.length; i++) {
			for (int j = 0; j < agent.Qtable[i].length; j++) {
				Rectangle rect = new Rectangle(j * Variant.SCALE, i
						* Variant.SCALE, Variant.SCALE, Variant.SCALE);
				if (findLarge(agent.Qtable[i][j]) > -1) {
					drawArrowLine(g, rect, findLarge(agent.Qtable[i][j]));
				}
			}
		}
	}

	public int findLarge(double[] array) {
		double temp = array[0];
		int index = 0;
		boolean flag = false;
		for (int i = 0; i < array.length; i++) {
			if (array[i] > temp && array[i] > 0) {
				flag = true;
				temp = array[i];
				index = i;
			}
		}
		// if flag == false, it means there does not have a larger number
		if (flag == false && temp <= 0) {
			index = -1;
		}
		// System.out.println(flag
		// + "************************************************" + index);
		return index;
	}

	public void drawArrowHead(Graphics g, int x, int y, int direction,
			int length) {
		switch (direction) {
		case 0:
			g.drawLine(x, y, x - length, y + length);
			g.drawLine(x, y, x + length, y + length);
			break;
		case 1:
			g.drawLine(x, y, x - length, y - length);
			g.drawLine(x, y, x + length, y - length);
			break;
		case 2:
			g.drawLine(x, y, x + length, y - length);
			g.drawLine(x, y, x + length, y + length);
			break;
		case 3:
			g.drawLine(x, y, x - length, y - length);
			g.drawLine(x, y, x - length, y + length);
			break;
		}
	}

	public void drawArrowLine(Graphics g, Rectangle rect, int direction) {
		int x2 = 0;
		int y2 = 0;
		switch (direction) {
		case 0:
			x2 = (int) (rect.getX() + rect.getWidth() / 2);
			y2 = (int) rect.getY();
			drawArrowHead(g, x2, y2, direction, Variant.SCALE / 10);
			break;
		case 1:
			x2 = (int) (rect.getX() + rect.getWidth() / 2);
			y2 = (int) (rect.getY() + rect.getHeight());
			drawArrowHead(g, x2, y2, direction, Variant.SCALE / 10);
			break;
		case 2:
			x2 = (int) rect.getX();
			y2 = (int) (rect.getY() + rect.getHeight() / 2);
			drawArrowHead(g, x2, y2, direction, Variant.SCALE / 10);
			break;
		case 3:
			x2 = (int) (rect.getX() + rect.getWidth());
			y2 = (int) (rect.getY() + rect.getHeight() / 2);
			drawArrowHead(g, x2, y2, direction, Variant.SCALE / 10);
			break;
		}
		g.drawLine((int) (rect.getWidth() / 2 + rect.getX()),
				(int) (rect.getHeight() / 2 + rect.getY()), x2, y2);
	}

	public void drawcenterString(Graphics g, String text, Rectangle rect,
			Font font, int position) {

		FontMetrics metrics = g.getFontMetrics(font);
		int x = 0;
		int y = 0;
		switch (position) {
		case 0:
			x = (int) ((rect.width - metrics.stringWidth(text)) / 2 + rect
					.getX());
			y = (int) (rect.getY() + metrics.getHeight() + metrics.getAscent());
			break;
		case 1:
			x = (int) ((rect.width - metrics.stringWidth(text)) / 2 + rect
					.getX());
			y = (int) (rect.getY() + rect.getHeight() - metrics.getHeight());
			break;
		case 2:
			x = (int) (metrics.stringWidth(text) / 2 + rect.getX());
			y = (int) (rect.getY() + (rect.getHeight() - metrics.getHeight())
					/ 2 + metrics.getAscent());
			break;
		case 3:
			x = (int) (rect.getWidth() - metrics.stringWidth(text) * 3 / 2 + rect
					.getX());
			y = (int) (rect.getY() + (rect.getHeight() - metrics.getHeight())
					/ 2 + metrics.getAscent());
			break;
		}
		g.setFont(font);
		// Draw the String
		g.drawString(text, x, y);
		// Dispose the Graphics
	}

}
