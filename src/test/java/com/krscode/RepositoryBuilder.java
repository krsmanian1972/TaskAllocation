package com.krscode;

public class RepositoryBuilder {

	static TaskRepository buildTaskRepository() {
		Task task1 = buildTask(1, true);
		Task task2 = buildTask(2, true);
		Task task3 = buildTask(3, false);
		Task task4 = buildTask(4, true);
		Task task5 = buildTask(5, false);
		Task task6 = buildTask(6, true);

		TaskRepository repository = new StubbedTaskRepository();
		repository.offer(task1);
		repository.offer(task2);
		repository.offer(task3);
		repository.offer(task4);
		repository.offer(task5);
		repository.offer(task6);

		return repository;
	}

	private static Task buildTask(int id, boolean isAssignable) {
		Task task = new Task(id);
		task.assignable(isAssignable);
		task.assignedTo(null);
		return task;
	}
}
