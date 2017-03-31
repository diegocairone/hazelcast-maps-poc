package com.cairone.hzsb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.cairone.hzsb.entities.HzTareaDiferidaEntity;
import com.cairone.hzsb.entities.HzTareaDiferidaPkEntity;

public interface HzTareaDiferidaRepository extends JpaRepository<HzTareaDiferidaEntity, HzTareaDiferidaPkEntity>, QueryDslPredicateExecutor<HzTareaDiferidaEntity> {

}
