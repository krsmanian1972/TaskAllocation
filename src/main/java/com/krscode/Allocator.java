package com.krscode;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Allocator {

	private TaskRepository taskRepository;

	private final BlockingQueue<Task> allocationBuffer = new LinkedBlockingQueue<Task>(2);

	public Allocator(TaskRepository repository) {
		this.taskRepository = repository;
		startTaskPool();
	}

	private void startTaskPool() {
		TaskPool taskPool = new TaskPool(taskRepository, allocationBuffer);
		new Thread(taskPool).start();
	}

	public Collection<Task> getBufferedTasks() {
		return Collections.unmodifiableCollection(allocationBuffer);
	}

	public Task allocate(String requestorId) throws NoTaskException, InterruptedException {

		Task capturedTask = allocationBuffer.poll(2, TimeUnit.SECONDS);
		if (capturedTask == null) {
			throw new NoTaskException("The Repository has no further tasks to offer");
		}
		capturedTask.assignedTo(requestorId);
		capturedTask.toPrint();
		return capturedTask;
	}

}
