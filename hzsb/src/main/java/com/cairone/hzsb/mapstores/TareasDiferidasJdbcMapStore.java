package com.cairone.hzsb.mapstores;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.cairone.hzsb.tasks.TaskKey;
import com.cairone.hzsb.tasks.TaskQueue;
import com.cairone.hzsb.utils.FechaUtil;

public class TareasDiferidasJdbcMapStore implements TareasDiferidasMapStore {

	private static final String SQL_SELECT = "SELECT serialized_object FROM hazelcast_tareasdiferidas WHERE fechaOperativa = ? AND numeroTransaccion = ?";
	private static final String SQL_INSERT = "INSERT INTO hazelcast_tareasdiferidas (fechaOperativa, numeroTransaccion, serialized_object) VALUES (?, ?, ?)";
	private static final String SQL_DELETE = "DELETE FROM hazelcast_tareasdiferidas WHERE fechaOperativa = ? AND numeroTransaccion = ?";
	
	private final Connection con;
	
	public TareasDiferidasJdbcMapStore() {
		try {
            con = DriverManager.getConnection("jdbc:jtds:sqlserver://localhost:1433/EJERCICIO_JPA", "sa", "rv760");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}
	
	@Override
	public synchronized TaskQueue load(TaskKey key) {
		try {
			
			PreparedStatement pstmt = con.prepareStatement(SQL_SELECT);

			pstmt.setDate(1, FechaUtil.asSqlDate(key.getFechaOperativa()));
			pstmt.setLong(2, key.getNumeroTransaccion());
			
			ResultSet rs = pstmt.executeQuery();
			
			if (!rs.next()) {
                return null;
            }
			
			byte[] buf = rs.getBytes(1);
			
			ObjectInputStream ois = null;
			
			if (buf != null) {
				ois = new ObjectInputStream(new ByteArrayInputStream(buf));
			}
			
			Object deserializedObject = ois.readObject();

			rs.close();
			pstmt.close();

			TaskQueue taskQueue = null;
			
			if(deserializedObject instanceof TaskQueue) {
				taskQueue = (TaskQueue) deserializedObject;
			}
			
			return taskQueue;
			
        } catch (SQLException | IOException | ClassNotFoundException e) {
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
		return null;/*
		try 
		{
			List<TaskKey> taskKeys = new ArrayList<TaskKey>();
			
			PreparedStatement pstmt = con.prepareStatement(SQL_ALLKEYS);
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				LocalDate fechaOperativa = FechaUtil.asLocalDate(rs.getDate(1));
				Long numeroTransaccion = rs.getLong(2);
				TaskKey taskKey = new TaskKey(fechaOperativa, numeroTransaccion);
				taskKeys.add(taskKey);
	        }

			System.out.println("TOTAL DE TRANSACCIONES ENCONTRADAS EN BD: " + taskKeys.size());
			
			return taskKeys;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/
	}

	@Override
	public synchronized void store(TaskKey key, TaskQueue value) {
		
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(value);
			oos.close();
			
			byte[] bin = baos.toByteArray();
			baos.close();
			
			PreparedStatement pstmt = con.prepareStatement(SQL_INSERT);
			
			pstmt.setDate(1, FechaUtil.asSqlDate(key.getFechaOperativa()));
			pstmt.setLong(2, key.getNumeroTransaccion());
			pstmt.setBytes(3, bin);
			
			pstmt.executeUpdate();
			pstmt.close();
			
		} catch (SQLException | IOException e) {
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

		try {
			PreparedStatement pstmt = con.prepareStatement(SQL_DELETE);
			
			pstmt.setDate(1, FechaUtil.asSqlDate(key.getFechaOperativa()));
			pstmt.setLong(2, key.getNumeroTransaccion());
			
			pstmt.executeUpdate();
			pstmt.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public synchronized void deleteAll(Collection<TaskKey> keys) {
		for (TaskKey key : keys) delete(key);
	}
}
