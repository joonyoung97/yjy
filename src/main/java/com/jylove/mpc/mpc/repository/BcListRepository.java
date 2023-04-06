package com.jylove.mpc.mpc.repository;

import com.jylove.mpc.mpc.entity.BcList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BcListRepository extends JpaRepository<BcList, Long>, QuerydslPredicateExecutor<BcList>, BcListRepositoryCustom {
}
