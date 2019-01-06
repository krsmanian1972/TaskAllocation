package com.krscode;


public interface TaskRepository {
	
	public void offer(Task task);

	public Task allocate(String requestorId);

}
