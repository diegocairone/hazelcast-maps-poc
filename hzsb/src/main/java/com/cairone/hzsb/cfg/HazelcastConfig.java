package com.cairone.hzsb.cfg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cairone.hzsb.mapstores.TareasDiferidasMapStore;
import com.hazelcast.config.Config;
import com.hazelcast.config.MapStoreConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

@Configuration
public class HazelcastConfig {

	public static final String MAP_NAME = "TAREAS-DIFERIDAS";
	
	@Autowired
	TareasDiferidasMapStore tareasDiferidasMapStore = null;

	@Value("${hz.cluster.name}")
	private String hzClusterName = null;
	
	@Value("${hz.cluster.pwd}")
	private String hzClusterPwd = null;
	
	@Value("${hz.mancenter.url}")
	private String hzMancenterUrl = null;
	
	@Bean
	public HazelcastInstance getHazelcastInstance() {
		
		Config cfg = new Config();

    	cfg.getManagementCenterConfig()
			.setEnabled(true)
			.setUrl(hzMancenterUrl);
		
    	MapStoreConfig mapStoreConfig = new MapStoreConfig();
        mapStoreConfig.setImplementation(tareasDiferidasMapStore);
        mapStoreConfig.setWriteDelaySeconds(0);
        
    	cfg.getMapConfig(MAP_NAME).setMapStoreConfig(mapStoreConfig);
    	
    	cfg.getGroupConfig().setName(hzClusterName).setPassword(hzClusterPwd);
    	
    	return Hazelcast.newHazelcastInstance(cfg);
	}
}
