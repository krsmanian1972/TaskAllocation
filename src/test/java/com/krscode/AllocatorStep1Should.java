package com.krscode;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.krscode.NoTaskException;
import com.krscode.Task;
import com.krscode.TaskRepository;
import com.krscode.step_1.Allocator;

/**
 * Should Buffer tasks that is assignable. Should Allocate tasks from the Buffer
 * 
 * @author raja
 *
 */
public class AllocatorStep1Should {

	@Test
	public void bufferAssignableTasks() {
		TaskRepository repository = RepositoryBuilder.buildTaskRepository();

		Allocator allocator = new Allocator(repository);
		allocator.setCapacity(4);
		allocator.fillBuffer();

		Collection<Task> tasks = allocator.getBufferedTasks();
		assertEquals("Expecting three tasks in assignable state", 4, tasks.size());
		for (Task task : tasks) {
			assertTrue("The task should be assignable", task.isAssignable());
		}
	}

	@Rule
	public ExpectedException noTaskException = ExpectedException.none();
	
	/**
	 * Should allocate a performable task.
	 * @throws InterruptedException 
	 * @throws NoTaskException 
	 */
	@Test
	public void allocateTaskFromBuffer() throws NoTaskException, InterruptedException {

		TaskRepository repository = RepositoryBuilder.buildTaskRepository();

		Allocator allocator = new Allocator(repository);

		String requestorId = "RJ72";

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
