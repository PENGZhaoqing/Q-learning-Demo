package cn.pengzhaoqing.Q_learning;

import java.util.Random;

public class MapLoader {

	private Qlearner learner;

	// 4 start, 5 end, 3 cliff, 2 obstacle
	// private int[][] map =new
	// int[Variant.Framework_height][Variant.Framework_width];

	public void MapGenerater() {
		for (int i = 0; i < Variant.Framework_height; i++) {
			for (int j = 0; j < Variant.Framework_width; j++) {
				Variant.MAP[i][j] = 0;
			}
		}
		Random rand = new Random();
		Variant.MAP[rand.nextInt(Variant.Framework_height)][rand
				.nextInt(Variant.Framework_width)] = 5;
	}

	public MapLoader(Qlearner learner) {
		this.learner = learner;
		// MapGenerater();
	}

	public void load(Agent agent) {

		double[][][] Qtable = agent.Qtable;
		int[][][] rewards = agent.rewards;
		int[][][] actions = agent.actions;
		
		// init_Q
		for (int i = 0; i < Qtable.length; i++) {
			for (int j = 0; j < Qtable[i].length; j++) {
				for (int k = 0; k < Qtable[i][j].length; k++) {
					Qtable[i][j][k] = 0;
				}
			}
		}

		// init_Reward

		for (int i = 0; i < rewards.length; i++) {
			for (int j = 0; j < rewards[i].length; j++) {
				for (int k = 0; k < rewards[i][j].length; k++) {
					rewards[i][j][k] = -1;
				}
			}
		}

		for (int i = 0; i < Variant.MAP.length; i++) {
			for (int j = 0; j < Variant.MAP[i].length; j++) {
				int item = Variant.MAP[i][j];
				switch (item) {

				// cliff
				case 3:
					// cliff location
					int[] num = { i, j };
					learner.cliff.location.add(num);

					// punish
					rewards[i + 1][j][0] = -100;
					rewards[i - 1][j][1] = -100;
					rewards[i][j + 1][2] = -100;
					rewards[i][j - 1][3] = -100;
					break;
				// end
				case 5:
					rewards[i + 1][j][0] = 300;
					rewards[i - 1][j][1] = 300;
					rewards[i][j + 1][2] = 300;
					rewards[i][j - 1][3] = 300;
					break;

				}
			}
		}

		// init_Action avaliable 1, not 0
		for (int i = 0; i < actions.length; i++) {
			for (int j = 0; j < actions[i].length; j++) {
				for (int k = 0; k < actions[i][j].length; k++) {
					actions[i][j][k] = 1;
				}
			}
		}

		// obstacle
		for (int i = 0; i < Variant.MAP.length; i++) {
			for (int j = 0; j < Variant.MAP[i].length; j++) {
				int item = Variant.MAP[i][j];
				if (item == 2) {

					// find obstacle
					int[] num = { i, j };
					learner.obstacle.location.add(num);

					actions[i + 1][j][0] = 0;
					actions[i - 1][j][1] = 0;
					actions[i][j + 1][2] = 0;
					actions[i][j - 1][3] = 0;
				}
			}
		}

		// border
		for (int i = 0; i < Variant.Framework_width; i++) {
			actions[0][i][0] = 0;
		}

		for (int i = 0; i < Variant.Framework_width; i++) {
			actions[Variant.Framework_height - 1][i][1] = 0;
		}

		for (int i = 0; i < Variant.Framework_height; i++) {
			actions[i][0][2] = 0;
		}

		for (int i = 0; i < Variant.Framework_height; i++) {
			actions[i][Variant.Framework_width - 1][3] = 0;
		}

		// find terminal
		for (int i = 0; i < Variant.MAP.length; i++) {
			for (int j = 0; j < Variant.MAP[i].length; j++) {
				if (Variant.MAP[i][j] == 5) {
					int[] num = { i, j };
					learner.terminal.location.add(num);
				}
			}
		}

		agent.init(Qtable, rewards, actions);
	}

	// for (int i = 0; i < actions.length; i++) {
	// for (int j = 0; j < actions[i].length; j++) {
	// for (int k = 0; k < actions[i][j].length; k++) {
	// System.out.print(actions[i][j][k] + " ");
	// }
	// System.out.print(",");
	// }
	// System.out.print("\n");
	// }

}
