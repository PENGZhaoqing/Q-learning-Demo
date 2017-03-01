package cn.pengzhaoqing.Q_learning;

import cn.pengzhaoqing.Q_learning.MainFrame;

public class GameRunner extends MainFrame {

	public GameRunner(Qlearner learner) {
		super(learner);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		Qlearner learner = new Qlearner();
		new FramePanel(learner);	
	}
	
}



