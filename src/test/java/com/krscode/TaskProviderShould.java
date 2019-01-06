package com.krscode;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.krscode.NoTaskException;
import com.krscode.TaskRepository;

public class TaskProviderShould {

	@Rule
	public ExpectedException noTaskException = ExpectedException.none();

	@Test
	public void bufferTasks() throws InterruptedException 
	{
		TaskRepository repository = RepositoryBuilder.buildTaskRepository();

		TaskProvider taskProvider = new TaskProvider(repository);

		Thread.sleep(2000);

		Collection<Task> tasks = taskProvider.getBufferedTasks();

		assertTrue("Expecting three tasks in assignable state", tasks.size() == 2);
		for (Task task : tasks) {
			assertTrue("The task should be assignable", task.isAssignable());
		}
	}

	/**
	 * Should allocate a performable task.
	 */
	@Test
	public void allocateTaskFromBuffer() throws InterruptedException {

		TaskRepository repository = RepositoryBuilder.buildTaskRepository();

		TaskProvider allocator = new TaskProvider(repository);

		String requestorId = "RJ12";

		Task task1 = allocator.allocate(requestorId);
		assertEquals(requestorId, task1.getAssignedTo());
		assertEquals(1, task1.getId());

		Task task2 = allocator.allocate(requestorId);
		assertEquals(requestorId, task2.getAssignedTo());
		assertEquals(2, task2.getId());

		Task task4 = allocator.allocate(requestorId);
		assertEquals(requestorId, task4.getAssignedTo());
		assertEquals(4, task4.getId());

		Task task6 = allocator.allocate(requestorId);
		assertEquals(requestorId, task6.getAssignedTo());
		assertEquals(6, task6.getId());

		noTaskException.expect(NoTaskException.class);
		allocator.allocate(requestorId);
	}

	

}
