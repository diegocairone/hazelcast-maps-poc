package com.cairone.hzsb.mapstores;

import com.cairone.hzsb.tasks.TaskKey;
import com.cairone.hzsb.tasks.TaskQueue;
import com.hazelcast.core.MapStore;

public interface TareasDiferidasMapStore extends MapStore<TaskKey, TaskQueue> {

}
