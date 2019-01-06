package com.krscode;

import java.util.StringJoiner;

public class Task {

	private int id;
	private String name;
	private boolean isAssignable;
	private String performerId;
	private String orderNumber;
	private String state;

	public Task(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void assignable(boolean flag) {
		this.isAssignable = flag;
	}

	public void assignedTo(String asssignedTo) {
		this.performerId = asssignedTo;
	}

	public String getAssignedTo() {
		return performerId;
	}

	public boolean isAssignable() {
		return this.isAssignable;
	}

	public void toPrint() {
		StringJoiner joiner = new StringJoiner("\t");
		joiner.add(String.valueOf(id));
		joiner.add(String.valueOf(isAssignable));
		joiner.add(String.valueOf(performerId));

		System.out.println(joiner);
	}

	public void setName(String taskName) {
		this.name = taskName;
	}
	
	public String getName()
	{
		return name;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	public String getOrderNumber() {
		return orderNumber;
	}

	public void setState(String state) {
		this.state = state;
		this.isAssignable ="Assignable".equalsIgnoreCase(state);
	}
	
	public String getState()
	{
		return state;
	}
}
