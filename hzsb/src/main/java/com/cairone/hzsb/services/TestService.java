package com.cairone.hzsb.services;

import java.sql.SQLException;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.xa.XAResource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atomikos.icatch.jta.UserTransactionManager;
import com.cairone.hzsb.entities.PaisEntity;
import com.cairone.hzsb.repositories.PaisRepository;
import com.cairone.hzsb.tasks.Task;
import com.cairone.hzsb.tasks.TaskImplFactorial;
import com.cairone.hzsb.tasks.TaskKey;
import com.cairone.hzsb.tasks.TaskQueue;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.TransactionalMap;
import com.hazelcast.transaction.HazelcastXAResource;
import com.hazelcast.transaction.TransactionContext;

@Service
public class TestService {

	public static Logger logger = LoggerFactory.getLogger(TestService.class);
	
	@Autowired private HazelcastInstance hazelcastInstance = null;
	@Autowired private UserTransactionManager tm = null;
	
	@Autowired private PaisRepository paisRepository = null;
	
	@Transactional
	public void agregar() throws NotSupportedException, SystemException, IllegalStateException, RollbackException, SecurityException, HeuristicMixedException, HeuristicRollbackException, SQLException {
		
		logger.info("AGRENADO AL MAPA ...");
		
		HazelcastXAResource xaResource = hazelcastInstance.getXAResource();
		
		Transaction transaction = tm.getTransaction();
		transaction.enlistResource(xaResource);
		
		TransactionContext context = xaResource.getTransactionContext();
		TransactionalMap<TaskKey, TaskQueue> mapTareasDiferidas = context.getMap("TAREAS-DIFERIDAS");
		
		TaskKey taskKey = new TaskKey(1L);
		TaskQueue taskQueue = mapTareasDiferidas.get(taskKey);
		
		Integer numero = 4;
		
		Task<Integer> taskFactorial = new TaskImplFactorial(numero);
		
		taskQueue = new TaskQueue();
		taskQueue.getQueue().add(taskFactorial);
		
		mapTareasDiferidas.put(taskKey, taskQueue);

		transaction.delistResource(xaResource, XAResource.TMSUCCESS);
        
		logger.info("AGRENADO A LA TABLA ...");
		
		PaisEntity paisEntity = new PaisEntity(100, "COLOMBIA", 10);
		paisRepository.save(paisEntity);
	}
}
