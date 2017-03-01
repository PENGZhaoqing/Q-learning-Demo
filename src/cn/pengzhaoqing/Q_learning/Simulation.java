package cn.pengzhaoqing.Q_learning;

import java.util.ArrayList;
import java.util.Random;

public class Simulation implements Runnable {
	private int[] state;
	private Agent agent;
	private Obstacle obstacle;
	private Cliff cliff;
	private Terminal terminal;
	private Qlearner learner;

	private ArrayList<Integer> max_index = new ArrayList<Integer>();
	private Random rand = new Random();
	public Boolean reset = false;

	public Simulation(Qlearner learner) {
		this.learner = learner;
		this.agent = learner.agent;
		this.obstacle = learner.obstacle;
		this.cliff = learner.cliff;
		this.terminal = learner.terminal;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		int action;
		int[] newstate;
		int count = 0;

		do {
			action = getAction(state);
			newstate = agent.getNextState(state, action);
			learner.graphUpdate(state, count);
			state = newstate;
			count++;

			if (reset)
				break;

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} while (!agent.arriveIn(cliff.location, state)
				&& !agent.arriveIn(terminal.location, state));
		
		//into terminal 
		learner.graphUpdate(state, count);
		
	}

	public int getAction(int[] state) {
		int y = state[0];
		int x = state[1];
		int action;

		double temp = agent.Qtable[y][x][0];

		// if the first item of Qtable is not available
		for (int k = 0; k < agent.Qtable[y][x].length; k++) {
			if (agent.actions[y][x][k] > 0) {
				temp = agent.Qtable[y][x][k];
				break;
			}
		}

		action = 0;

		for (int k = 0; k < agent.Qtable[y][x].length; k++) {
			if (agent.Qtable[y][x][k] >= temp && agent.actions[y][x][k] > 0) {
				temp = agent.Qtable[y][x][k];
				action = k;
			}
		}

		for (int k = 0; k < agent.Qtable[y][x].length; k++) {
			if (temp == agent.Qtable[y][x][k] && agent.actions[y][x][k] > 0) {
				max_index.add(k);
			}
		}

		// value
		switch (max_index.size()) {
		case 1:
			action = max_index.get(0);
			break;
		case 2:
			action = max_index.get(rand.nextInt(2));
			break;
		case 3:
			action = max_index.get(rand.nextInt(3));
			break;
		case 4:
			action = max_index.get(rand.nextInt(4));
			break;
		default:
			System.out.println("something worng with action choosen");
			action = agent.randAction(state);
		}

		max_index.clear();

		return action;
	}

	public int[] getState() {
		return state;
	}

	public void setState(int[] state) {
		this.state = state;
	}

}
