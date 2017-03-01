# Agent Pathfinding Demo by Q-learning

Here is a java program aiming to give the RL beginners a better understanding of Q-learning.

**If you are new to Reinforcement Learning, here is what you want for Q-learning !!!** 

## Demo

### Scenario

The goal is to train the agent to find path to the destination

* Red square represents the agent
* Black square represents the rival, agent will get -100 punishment after falling into rivers
* Grey square represents the wall, agent cannot go through the wall
* Blue square represents the goal destination that agent should arrive, agent will get 300 reward while arriving 

1. Option: Show Q tables 

  <img src="/snap/snap-1.png">

  you can visualize the four action values (up, down, left and right) in each square box

2. Option:Show routes

  <img src="/snap/snap-2.png">

  Also See the decided path of the agent, trained by cumulative experiments 

## Getting Started

1. Clone repo to local and import it into eclipse
2. Manually add external jar (/jars/designgridlayout-1.11.jar) to project
3. Run `GameRunner.java` as java application


## Usage

In the Control Panel, you can adjust the params of gamma, alpha, epislon in the formula to see the performance of algorithm

<img src="/snap/formula.png">

You can also determine the time interval(millisecond) between two frames and the largest training steps.

And you can also simulate your training results with Demo button

## Main Loop

```
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
    
    graphUpdate(current_s,steps);
    Thread.sleep(time_interval);
  }
  // the last state is the arrival of the terminal
  graphUpdate(current_s, steps);
  steps++;
}
```
