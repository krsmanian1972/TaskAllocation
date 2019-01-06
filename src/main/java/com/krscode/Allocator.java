package com.krscode;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Allocator {

	private TaskRepository taskRepository;

	private final Queue<Task> allocationBuffer = new LinkedList<Task>();
	
	private final Lock lock = new ReentrantLock();

	private int capacity = 2;
	
	private final static String ALLOCATOR_ID = "9999";

	public Allocator(TaskRepository repository) {
		this.taskRepository = repository;
	}

	public void setCapacity(int capacity)
	{
		this.capacity = capacity;
	}
	
	public Collection<Task> getBufferedTasks()
	{
		return Collections.unmodifiableCollection(allocationBuffer);
	}
	
	/**
	 * Fill the Buffer with assignable Task.
	 * Offer the unassignable Task back to the Repository.
	 * 
	 * Protect against cyclic offering back to the repository.
	 * @throws NoAssignableTaskException 
	 * 
	 */
	public void fillBuffer() 
	{
		lock.lock();
		
		int visitedId = -1;
		
		try
		{
			while(allocationBuffer.size() < capacity)
			{
				Task task = taskRepository.allocate(ALLOCATOR_ID);
				
				if(task == null)
				{
					throw new NoTaskException("The Repository has no further tasks to offer");
				}
				
				if(task.isAssignable())
				{
					allocationBuffer.offer(task);
				}
				else
				{
					taskRepository.offer(task);
					if(task.getId() == visitedId) break;
					if(visitedId == -1)  visitedId = task.getId();
				}
			}
		}
		finally 
		{
			lock.unlock();
		}
	}

	
	/**
	 * If the Buffer is empty, let us wait until we get at least one item
	 *  
	 * @param requestorId
	 * @return
	 * @throws NoTaskException
	 * @throws InterruptedException
	 */
	public Task allocate(String requestorId) throws NoTaskException, InterruptedException {
		lock.lock();
		try
		{
			if(allocationBuffer.isEmpty())
			{
				fillBuffer();
			}
			Task capturedTask = allocationBuffer.poll();
			if(capturedTask == null)
			{
				throw new NoTaskException("No Assignable Task is available with the repository.");
			}
			capturedTask.assignedTo(requestorId);
			return capturedTask;
		}
		finally {
			lock.unlock();
		}
	}
	

}
