package cn.pengzhaoqing.Q_learning;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.pengzhaoqing.Q_learning.Variant;

public class Agent {

	public double[][][] Qtable = new double[Variant.Framework_height][Variant.Framework_width][Agent.Action
			.values().length];

	public int[][][] actions = new int[Variant.Framework_height][Variant.Framework_width][Agent.Action
			.values().length];

	public int[][][] rewards = new int[Variant.Framework_height][Variant.Framework_width][Agent.Action
			.values().length];

	private Qlearner learner;
	private Random rand = new Random();
	private int[] state;
	private int last_action = 0;
	private ArrayList<Integer> max_index = new ArrayList<Integer>();

	public Agent(Qlearner learner) {
		this.learner = learner;
	}

	public void init(double[][][] Qtable, int[][][] rewards, int[][][] actions) {
		this.Qtable = Qtable;
		this.rewards = rewards;
		this.actions = actions;
	}

	public void setLocation(int[] state) {
		this.state = state;
	}

	//
	public enum Action {
		North, South, West, Eest
	};


	public boolean arriveIn(ArrayList<int[]> ob, int[] state) {
		for (int i = 0; i < ob.size(); i++) {
			if (state[0] == ob.get(i)[0] && state[1] == ob.get(i)[1]) {
				// System.out.println("Agent cound not be in the obstacle");
				return true;
			}
		}
		return false;
	}

	public void draw(Graphics g) {
		// move();
		int x = state[1];
		int y = state[0];
		Color c = g.getColor();
		g.setColor(Color.red);
		g.fillRect(x * Variant.SCALE, y * Variant.SCALE, Variant.SCALE,
				Variant.SCALE);
		g.setColor(c);
	}

	// public void move() {
	// switch (action) {
	// case North:
	// y = y - speed;
	// break;
	// case South:
	// y = y + speed;
	// break;
	// case West:
	// x = x - speed;
	// break;
	// case Eest:
	// x = x + speed;
	// break;
	// }
	//
	// }
	//
	// public boolean hitWall() {
	//
	// return false;
	// }

	// public boolean hitObstacle(Obstacle ob) {
	// if (this.getRect().intersects(ob.getRect())) {
	// return true;
	// }
	// return false;
	// }

	public int greedyAction(int[] state) {

		int y = state[0];
		int x = state[1];
		int action;
		double temp;
		// around 0.9 possibility or greedy choosing
		if ((1 - learner.epislon) * 1000 > rand.nextInt(1000)) {

			temp = Qtable[y][x][0];

			// if the first item of Qtable is not available
			for (int k = 0; k < Qtable[y][x].length; k++) {
				if (actions[y][x][k] > 0 && k != backwardAction(last_action)) {
					temp = Qtable[y][x][k];
					break;
				}
			}

			// find the biggest Q value used to decide the action
			for (int k = 0; k < Qtable[y][x].length; k++) {
				if (Qtable[y][x][k] > temp && actions[y][x][k] > 0
						&& k != backwardAction(last_action)) {
					temp = Qtable[y][x][k];
				}
			}

			// find the duplicated values of maximum Q
			for (int k = 0; k < Qtable[y][x].length; k++) {
				if (temp == Qtable[y][x][k] && actions[y][x][k] > 0
						&& k != backwardAction(last_action)) {
					max_index.add(k);
				}
			}

			// chosing random actions based on the size of the numbers of same
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
			default:
				System.out.println("something worng with action choosen");
				action = randAction(state);
			}

			max_index.clear();

		} else {
			// around 0.1 possibility of random choosing action;
			action = randAction(state);
		}
		// setEpislon(getEpislon() * 0.999);

		last_action = action;
		return action;
	}

	public boolean agentCollision(List<Agent> agents) {
		for (int i = 0; i < agents.size(); i++) {
			Agent agent = agents.get(i);
			if (this != agent) {
				if (this.getRect().intersects(agent.getRect())) {
					return true;
				}
			}
		}
		return false;
	}

	public Rectangle getRect() {
		Rectangle rt = new Rectangle(state[1], state[0], Variant.SCALE,
				Variant.SCALE);
		return rt;
	}

	public void updateQtable(int[] state, int action, double new_Q) {
		Qtable[state[0]][state[1]][action] = new_Q;
		// System.out.println(" Q table is updated in x:" + state[1] + ",y="
		// + state[0] + ",action=" + action + ", new value=" + new_Q);
	}

	public double getMaxQvalue(int[] newstate) {
		double[] num = Qtable[newstate[0]][newstate[1]];
		double temp = num[0];
		for (int i = 0; i < num.length; i++) {
			if (num[i] > temp) {
				temp = num[i];
			}
		}
		double result = temp;
		// System.out.println("biggest  Q value =" + result);
		return result;
	}

	public double getQvalue(int[] state, int action) {
		int y = state[0];
		int x = state[1];
		// System.out.println("Q value =" + Qtable[y][x][action]);
		return Qtable[y][x][action];
	}

	public int getReward(int[] state, int action) {
		int y = state[0];
		int x = state[1];
		// System.out.println("Reward is " + rewards[y][x][action]);
		return rewards[y][x][action];
	}

	public int[] getNextState(int[] state, int action) {
		int y = state[0];
		int x = state[1];
		// System.out.println("current state: x=" + x + ",y=" + y);

		switch (action) {
		case 0:
			y = y - 1;
			break;
		case 1:
			y = y + 1;
			break;
		case 2:
			x = x - 1;
			break;
		case 3:
			x = x + 1;
			break;
		}

		if (y == state[0] && x == state[1]) {
			System.out.println("Error: The state is not changed");
		} else {
			// System.out.println("new state: x=" + x + ",y=" + y);
		}

		int[] result = { y, x };
		return result;
	}

	public int randAction(int[] state) {
		int y = state[0];
		int x = state[1];

		int result = rand.nextInt(actions[y][x].length);
		while (actions[y][x][result] == 0
				|| result == backwardAction(last_action)) {
			result = rand.nextInt(actions[y][x].length);
		}

		// System.out.println("rand choose action: " + result);
		return result;
	}

	public int[] randStart() {
		int[] begin = new int[2];
		begin[0] = rand.nextInt(Variant.Framework_height);
		begin[1] = rand.nextInt(Variant.Framework_width);

		// the start place can not be in obstacle or cliif
		while (arriveIn(learner.cliff.location, begin)
				|| arriveIn(learner.obstacle.location, begin)) {
			begin[0] = rand.nextInt(Variant.Framework_height);
			begin[1] = rand.nextInt(Variant.Framework_width);
		}
		// System.out.println("agent start from x=" + current_s[1] + ", y="
		// + current_s[0]);
		return begin;
	}

	public int backwardAction(int action) {
		int result;
		switch (action) {
		case 0:
			result = 1;
			break;
		case 1:
			result = 0;
			break;
		case 2:
			result = 3;
			break;
		case 3:
			result = 2;
			break;
		default:
			System.out.println("no such action");
			result = action;
		}

		return result;
	}

}
