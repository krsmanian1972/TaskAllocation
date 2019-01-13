package com.krscode;

import java.util.concurrent.BlockingQueue;

public final class TaskPool implements Runnable {

	private TaskRepository taskRepository;
	private BlockingQueue<Task> allocationBuffer;

	private final static String ALLOCATOR_ID = "9999";
	private final static int UNVISITED_TASK_ID = -1;

	private volatile boolean go = true;

	public TaskPool(TaskRepository taskRepository, BlockingQueue<Task> allocationBuffer) {
		this.taskRepository = taskRepository;
		this.allocationBuffer = allocationBuffer;
	}

	/**
	 * Fill the Buffer with assignable Task. Offer the unassignable Task back to
	 * the Repository.
	 * 
	 * Protect against cyclic offering back to the repository.
	 * 
	 * @throws InterruptedException
	 * 
	 */
	private void fillBuffer() throws InterruptedException {
		int visitedTaskId = UNVISITED_TASK_ID;

		while (go) {
			Task task = taskRepository.allocate(ALLOCATOR_ID);

			if (task == null) {
				Thread.sleep(1000);
				continue;
			}

			if (task.isAssignable()) {
				allocationBuffer.put(task);
			} else {
				taskRepository.offer(task);
				visitedTaskId = onRevisit(visitedTaskId, task);
			}
		}
	}

	private int onRevisit(int visitedTaskId, Task task) throws InterruptedException {
		if (visitedTaskId == UNVISITED_TASK_ID) {
			return task.getId();
		}

		if (task.getId() != visitedTaskId) {
			return visitedTaskId;
		}

		Thread.sleep(1000);
		return UNVISITED_TASK_ID;
	}
	

	public void run() {
		try {
			fillBuffer();
		} catch (InterruptedException e) {
			this.go = false;
		}
	}

}
