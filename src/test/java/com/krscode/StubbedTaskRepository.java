package com.krscode;

import java.util.LinkedList;
import java.util.Queue;

public class StubbedTaskRepository implements TaskRepository {

	private final Queue<Task> tasks = new LinkedList<Task>();

	public void offer(Task task) {
		if (task == null) {
			return;
		}
		task.assignedTo(null);
		tasks.add(task);
	}

	public Task allocate(String requestorId) {
		Task task = tasks.poll();

		if (task == null)
			return null;

		task.assignedTo(requestorId);
		return task;
	}

}
