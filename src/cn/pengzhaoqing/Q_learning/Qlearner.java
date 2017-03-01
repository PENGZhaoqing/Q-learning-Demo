package cn.pengzhaoqing.Q_learning;

import cn.pengzhaoqing.Q_learning.Agent;

public class Qlearner implements Runnable {
	
	public Agent agent;
	public Obstacle obstacle;
	public Cliff cliff;
	public Visualization visualizer;
	public Terminal terminal;
	public MapLoader mapper;
	public MainFrame frame;

	public double gamma;
	public double alpha;
	public double epislon;
	public int iteration;
	public int time_interval;
	public boolean reset = false;
	public boolean pause = false;

	private final Object LOCK = new Object();

	

	public Qlearner() {
		agent = new Agent(this);
		obstacle = new Obstacle();
		cliff = new Cliff();
		visualizer = new Visualization(agent);
		terminal = new Terminal();
		mapper = new MapLoader(this);
		mapper.load(agent);
		frame = new MainFrame(this);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		int steps = 0;
		double this_Q;
		double max_Q;
		double new_Q;
		int[] newstate;
		int[] current_s;
		int reward;
		int action;
		
		while (steps <= iteration && !reset) {
			
			current_s=agent.randStart();
			
			while (!agent.arriveIn(cliff.location, current_s)
					&& !agent.arriveIn(terminal.location, current_s)) {

				action = agent.greedyAction(current_s);
				reward = agent.getReward(current_s, action);
				newstate = agent.getNextState(current_s, action);
				max_Q = agent.getMaxQvalue(newstate);
				this_Q = agent.getQvalue(current_s, action);
				new_Q = this_Q + alpha
						* (reward + gamma * max_Q - this_Q);
				agent.updateQtable(current_s, action, new_Q);
				current_s = newstate;

				synchronized (LOCK) {
					if (pause) {
						try {
							LOCK.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} else {
						graphUpdate(current_s,steps);
					}
				}

				try {
					Thread.sleep(time_interval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
			// the last state is the arrival of the terminal
			graphUpdate(current_s, steps);
			steps++;
		}
	}

	public void pause() {
		synchronized (LOCK) {
			pause = true;
			LOCK.notifyAll();
		}
	}

	public void resume() {
		synchronized (LOCK) {
			pause = false;
			LOCK.notifyAll();
		}
	}

	public void graphUpdate(int[] state,int steps) {
		frame.paramsUpdate(obstacle.location, cliff.location, state,
				terminal.location, steps);
		frame.repaint();
	}

}

// for (int k = 0; k < Qtable[y][x].length; k++) {
// if (actions[y][x][k] > 0) {
// hash.put(k, Qtable[y][x][k]);
// }
// }

// TreeSet<Double> values = new TreeSet<Double>(hash.values());
// // four action in total, but only values has only one value,
// // which means the four actions have the same Q values
// Object[] array = values.toArray();
//
// switch (hash.size()) {
// case 1:
// action = (int) hash.keySet().toArray()[0];
// case 2:
// if ((Double) array[0] != (Double) array[1]) {
// }
// }
// switch (hash.size() - values.size()) {
//
// case 1:
//
// }
// ;

// values.toArray()[0]

// List<Person> peopleByAge = new ArrayList<Person>(people.values());

