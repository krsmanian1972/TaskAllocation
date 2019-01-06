package com.krscode;

import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;

@RunWith(ConcordionRunner.class)

public class PreferredTasksFeature {

	private final TaskRepository taskRepository = new StubbedTaskRepository();
	private final Allocator allocator = new Allocator(taskRepository);
	
	public void addTask(int id, String name, String orderNumber, String state, String assignedTo)
	{
		Task task = new Task(id);
		task.setName(name);
		task.setOrderNumber(orderNumber);
		task.setState(state);
		task.assignedTo(assignedTo);
		
		taskRepository.offer(task);
	}
	
	public Task allocate(String requestorId) throws InterruptedException {
		try
		{
			return allocator.allocate(requestorId);
		}
		catch(NoTaskException e)
		{
			return null;
		}
	}
}
