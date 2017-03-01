package cn.pengzhaoqing.Q_learning;

import javax.swing.*;

import net.java.dev.designgridlayout.DesignGridLayout;
import java.awt.Color;
import java.awt.event.*;

public class FramePanel extends JFrame implements ActionListener {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	JTextField gamma_text = new JTextField("0.9");
	JTextField alpha_text = new JTextField("0.9");
	JTextField epislon_text = new JTextField("0.2");
	JTextField interval_text = new JTextField("10");
	JTextField begin_text = new JTextField("3,2");
	JTextField episode_text = new JTextField("1000");

	private int x = Variant.Framework_X + Variant.Framework_width
			* Variant.SCALE;
	private int y = Variant.Framework_Y;
	private Qlearner learner;
	private Simulation sim ;
	
	public FramePanel(Qlearner learner) {
		this.learner = learner;
		sim= new Simulation(learner);
		
		this.setTitle("Control Panel");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		DesignGridLayout layout = new DesignGridLayout(panel);

		addGroup(layout, "Training");

		layout.row().grid(new JLabel("gamma")).add(gamma_text);
		layout.row().grid(new JLabel("alpha")).add(alpha_text);
		layout.row().grid(new JLabel("epislon")).add(epislon_text);
		layout.row().grid(new JLabel("inverval")).add(interval_text);
		layout.row().grid(new JLabel("steps")).add(episode_text);

		JCheckBox showQ = new JCheckBox("Show Q tables");
		showQ.addActionListener(this);
		JCheckBox route = new JCheckBox("Show routes");
		route.addActionListener(this);
		layout.row().grid(new JLabel("option:")).add(showQ).add(route);

		JButton training = new JButton("Start");
		training.addActionListener(this);
		JButton reset = new JButton("Reset");
		reset.addActionListener(this);
		layout.row().grid().add(training).add(reset);

		JButton pause = new JButton("Pause");
		pause.addActionListener(this);
		JButton resume = new JButton("Resume");
		resume.addActionListener(this);
		layout.row().grid().add(pause).add(resume);

		addGroup(layout, "Simulation");

		layout.row().grid(new JLabel("begin")).add(begin_text);

		JButton simulate = new JButton("Demo");
		simulate.addActionListener(this);
		JButton sim_stop = new JButton("Stop");
		sim_stop.addActionListener(this);
		layout.row().grid().add(simulate).add(sim_stop);

		this.setLocation(x, y);
		this.add(panel);
		this.pack();
		this.setVisible(true);

	}

	private void addGroup(DesignGridLayout layout, String name) {
		JLabel group = new JLabel(name);
		group.setForeground(Color.BLUE);
		layout.emptyRow();
		layout.row().left().add(group, new JSeparator()).fill();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Show Q tables")) {
			learner.frame.Qflag = switchFlag(learner.frame.Qflag);
		} else if (e.getActionCommand().equals("Show routes")) {
			learner.frame.routeflag = switchFlag(learner.frame.routeflag);
		} else if (e.getActionCommand().equals("Start")) {
			startTraining();
		} else if (e.getActionCommand().equals("Reset")) {
			learner.reset=true;
		} else if (e.getActionCommand().equals("Demo")) {
			startDemo();
		} else if (e.getActionCommand().equals("Pause")) {
			learner.pause();
		} else if (e.getActionCommand().equals("Resume")) {
			learner.resume();
		} else if (e.getActionCommand().equals("Stop")){
			sim.reset=true;
		}
	}

	public boolean switchFlag(boolean flag) {
		if (flag == true) {
			return false;
		} else {
			return true;
		}

	}

	public void startDemo() {
		String origin = begin_text.getText();
		String[] origin_s = origin.split(",");
		int[] origin_int = new int[2];
		for (int i = 0; i < origin_s.length; i++) {
			origin_int[i] = Integer.valueOf(origin_s[i]);
//			System.out.println(origin_int[i]);
		}

		learner.frame.launchFrame();
		sim.setState(origin_int);
		sim.reset=false;
		new Thread(sim).start();
	}

	public void startTraining() {
		String gamma = gamma_text.getText();
		learner.gamma=Double.valueOf(gamma);

		String alpha = alpha_text.getText();
		learner.alpha=Double.valueOf(alpha);

		String epislon = epislon_text.getText();
		learner.epislon=Double.valueOf(epislon);

		String steps = episode_text.getText();
		learner.iteration=Integer.valueOf(steps);

		String time = interval_text.getText();
		learner.time_interval=Integer.valueOf(time);

		learner.reset=false;
		learner.frame.launchFrame();
		new Thread(learner).start();
	}
}
