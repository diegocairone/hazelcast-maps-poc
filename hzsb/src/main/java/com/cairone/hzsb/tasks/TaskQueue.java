package com.cairone.hzsb.tasks;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class TaskQueue implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<Task<?>> queue = null;
	
	public TaskQueue() {
		this.queue = new LinkedList<Task<?>>();
	}

	public List<Task<?>> getQueue() {
		return queue;
	}
}
