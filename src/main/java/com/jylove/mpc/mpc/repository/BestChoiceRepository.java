package com.jylove.mpc.mpc.repository;

import com.jylove.mpc.mpc.entity.BestChoice;
import com.jylove.mpc.mpc.entity.BestChoiceImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface BestChoiceRepository extends JpaRepository<BestChoice, Long>, QuerydslPredicateExecutor<BestChoice>, BestChoiceRepositoryCustom {
}
