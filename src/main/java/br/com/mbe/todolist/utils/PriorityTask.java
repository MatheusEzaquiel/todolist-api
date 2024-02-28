package br.com.mbe.todolist.utils;

public enum PriorityTask {
	HIGH(1),
	MEDIUM(2),
	LOW(3),
	NO(4);
	
	public int priorityLevel;
	
	PriorityTask(int level) {
		this.priorityLevel = level;
	}
}
