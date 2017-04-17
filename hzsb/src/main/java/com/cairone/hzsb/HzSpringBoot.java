package com.cairone.hzsb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cairone.hzsb.services.TestService;

@SpringBootApplication
public class HzSpringBoot implements CommandLineRunner
{
	public static Logger logger = LoggerFactory.getLogger(HzSpringBoot.class);
	
	//@Autowired
	//private HazelcastInstance hazelcastInstance = null;
	
	@Autowired
	private TestService testService = null;
	
    public static void main( String[] args ) {
        SpringApplication.run(HzSpringBoot.class, args);
    }
    
    @Override
	public void run(String... args) throws Exception {

		logger.info("Iniciando ...");
		
		try {
			testService.agregar();
			
		} catch(RuntimeException ex) {
			logger.info("EXEPCION: ", ex.getMessage());
		}
    }
    /*
	public void run0(String... args) throws Exception {

		logger.info("Iniciando consumidor ...");
		
		TransactionContext context = hazelcastInstance.newTransactionContext();
		context.beginTransaction();
		
		TransactionalMap<TaskKey, TaskQueue> mapTareasDiferidas = context.getMap("TAREAS-DIFERIDAS");
		logger.info("TOTAL DE TRANSACCIONES SIN EJECUTAR: {}", mapTareasDiferidas.size());
		
		TaskKey taskKey = new TaskKey(1L);
		TaskQueue taskQueue = mapTareasDiferidas.get(taskKey);
		
		if(taskQueue != null) {
			
			logger.info("SE ENCONTRO UNA COLA DE TAREAS CON CLAVE: {}", taskKey);
			
			LinkedList<Task<?>> queue = (LinkedList<Task<?>>) taskQueue.getQueue();
			Task<?> task = queue.poll();
			
			while(task != null) {
				
				if(task instanceof TaskImplFactorial) {
					
					TaskImplFactorial taskImplFactorial = (TaskImplFactorial) task;
					Integer resultado = taskImplFactorial.execute();
					
					logger.info("EL RESULTADO DEL FACTORIAL DE {} ES {}", taskImplFactorial.getNumero(), resultado);
				}
				
				task = queue.poll();
			}
			
			if(queue.isEmpty()) {
				
				try {
					mapTareasDiferidas.remove(taskKey);
					context.commitTransaction();
				} catch (TransactionException e) {
					context.rollbackTransaction();
					logger.error(e.getMessage());
				}
			}
			
			return;
		}
		
		Integer numero = 4;
		
		Task<Integer> taskFactorial = new TaskImplFactorial(numero);
		
		taskQueue = new TaskQueue();
		taskQueue.getQueue().add(taskFactorial);
		
		try {
			mapTareasDiferidas.put(taskKey, taskQueue);
			logger.info("SE AGREGA LA TAREA PARA CALCULAR EL FACTORIAL DE: {}", numero);
			
			context.commitTransaction();
			
		} catch (TransactionException e) {
			context.rollbackTransaction();
			logger.error(e.getMessage());
		}
	}*/
}
