package cn.pengzhaoqing.Q_learning;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import cn.pengzhaoqing.Q_learning.Variant;

public class MainFrame extends Frame {

	private static final long serialVersionUID = 1L;
	
	private int iteration;
	private Image offScreenImage = null;

	public boolean Qflag = false;
	public boolean routeflag = false;

	private Qlearner learner;

	public MainFrame(Qlearner learner) {
		this.setLearner(learner);
		
	}

	public void launchFrame() {
		this.setTitle("Reinforcement learning");
		this.setBounds(Variant.Framework_X, Variant.Framework_Y,
				Variant.Framework_width * Variant.SCALE,
				Variant.Framework_height * Variant.SCALE);
//		 this.setLayout(new FlowLayout(FlowLayout.LEFT));
		// this.setLayout(new GridLayout(2,2));
		this.setBackground(Variant.Background);
		this.setVisible(true);

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

	}

	@Override
	public void update(Graphics g) {
		if (offScreenImage == null) {
			offScreenImage = this.createImage(Variant.Framework_width
					* Variant.SCALE, Variant.Framework_height * Variant.SCALE);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Variant.Background);
		gOffScreen.fillRect(0, 0, Variant.Framework_width * Variant.SCALE,
				Variant.Framework_height * Variant.SCALE);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);

	}

	public void paramsUpdate(ArrayList<int[]> ob, ArrayList<int[]> cl, int[] state,
			ArrayList<int[]>goal, int steps) {
		learner.obstacle.setLocation(ob);
		learner.cliff.setLocation(cl);
		learner.agent.setLocation(state);
		learner.terminal.setLocation(goal);;
		iteration=steps;
	}

	@Override
	public void paint(Graphics g) {;
		g.setColor(Color.RED);
		g.drawString("Iteraction: " + iteration, Variant.SCALE+10, 100);
		learner.obstacle.draw(g);
		learner.cliff.draw(g);
		learner.agent.draw(g);
		learner.terminal.draw(g);
		g.setColor(Color.BLUE);
		
		g.setColor(Color.blue);

		if (Qflag) {
			learner.visualizer.drawQtable(g);
		}

		g.setColor(Color.BLACK);
		if (routeflag) {
			learner.visualizer.drawRoute(g);
		}

	}

	public Qlearner getLearner() {
		return learner;
	}

	public void setLearner(Qlearner learner) {
		this.learner = learner;
	}

}
