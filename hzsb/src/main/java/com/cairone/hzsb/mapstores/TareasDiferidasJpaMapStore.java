package com.cairone.hzsb.mapstores;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cairone.hzsb.entities.HzTareaDiferidaEntity;
import com.cairone.hzsb.entities.HzTareaDiferidaPkEntity;
import com.cairone.hzsb.repositories.HzTareaDiferidaRepository;
import com.cairone.hzsb.tasks.TaskKey;
import com.cairone.hzsb.tasks.TaskQueue;

@Component
public class TareasDiferidasJpaMapStore implements TareasDiferidasMapStore {

	@Autowired private HzTareaDiferidaRepository hzTareaDiferidaRepository = null;
	
	@Override
	public synchronized void store(TaskKey key, TaskQueue value) {

		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(value);
			oos.close();
			
			byte[] bin = baos.toByteArray();
			baos.close();
			
			HzTareaDiferidaEntity hzTareaDiferidaEntity = new HzTareaDiferidaEntity(key);
			hzTareaDiferidaEntity.setSerializedObject(bin);
			
			hzTareaDiferidaRepository.save(hzTareaDiferidaEntity);
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public synchronized void storeAll(Map<TaskKey, TaskQueue> map) {
		for (Map.Entry<TaskKey, TaskQueue> entry : map.entrySet()) {
            store(entry.getKey(), entry.getValue());
        }
	}

	@Override
	public synchronized void delete(TaskKey key) {
		HzTareaDiferidaEntity hzTareaDiferidaEntity = new HzTareaDiferidaEntity(key);
		hzTareaDiferidaRepository.delete(hzTareaDiferidaEntity);
	}

	@Override
	public synchronized void deleteAll(Collection<TaskKey> keys) {
		for (TaskKey key : keys) delete(key);
	}

	@Override
	public synchronized TaskQueue load(TaskKey key) {
	
		try {
			
			HzTareaDiferidaPkEntity hzTareaDiferidaPkEntity = new HzTareaDiferidaPkEntity(key);
			HzTareaDiferidaEntity hzTareaDiferidaEntity = hzTareaDiferidaRepository.findOne(hzTareaDiferidaPkEntity);
			
			if(hzTareaDiferidaEntity == null) {
				return null;
			}
			
			byte[] buf = hzTareaDiferidaEntity.getSerializedObject();
	
			ObjectInputStream ois = null;
			
			if (buf != null) {
				ois = new ObjectInputStream(new ByteArrayInputStream(buf));
			}
			
			Object deserializedObject = ois.readObject();
			TaskQueue taskQueue = null;
			
			if(deserializedObject instanceof TaskQueue) {
				taskQueue = (TaskQueue) deserializedObject;
			}
			
			return taskQueue;
			
		} catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
	}

	@Override
	public synchronized Map<TaskKey, TaskQueue> loadAll(Collection<TaskKey> keys) {
		Map<TaskKey, TaskQueue> map = new HashMap<TaskKey, TaskQueue>();
		for(TaskKey taskKey : keys) map.put(taskKey, load(taskKey));
		return map;
	}

	@Override
	public Iterable<TaskKey> loadAllKeys() {
		return null;
	}
}
